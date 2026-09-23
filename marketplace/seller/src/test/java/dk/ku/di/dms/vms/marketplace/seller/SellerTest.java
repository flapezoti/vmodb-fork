package dk.ku.di.dms.vms.marketplace.seller;

import dk.ku.di.dms.vms.marketplace.common.Constants;
import dk.ku.di.dms.vms.marketplace.common.entities.OrderItem;
import dk.ku.di.dms.vms.marketplace.common.events.InvoiceIssued;
import dk.ku.di.dms.vms.marketplace.common.inputs.CustomerCheckout;
import dk.ku.di.dms.vms.marketplace.seller.dtos.SellerDashboard;
import dk.ku.di.dms.vms.marketplace.seller.entities.OrderEntry;
import dk.ku.di.dms.vms.marketplace.seller.entities.Seller;
import dk.ku.di.dms.vms.modb.definition.key.IKey;
import dk.ku.di.dms.vms.modb.definition.key.KeyUtils;
import dk.ku.di.dms.vms.sdk.core.operational.InboundEvent;
import dk.ku.di.dms.vms.sdk.embed.client.VmsApplication;
import dk.ku.di.dms.vms.sdk.embed.client.VmsApplicationOptions;
import dk.ku.di.dms.vms.sdk.embed.facade.AbstractProxyRepository;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static java.lang.Thread.sleep;

public final class SellerTest {

    private static final int MAX_SELLERS = 10;

    private static final int LAST_TID = 10;

    @SuppressWarnings("unchecked")
    @Test
    public void testParseEnumDynamically() throws Exception {
        VmsApplication vms = getVmsApplication();
        vms.start();
        Object[] entry = new Object[]{
                1, 1, 1, 1, "c_o_id", 1, "test", "test", 1, 1, 1, 1, 1, 1, 1, null, null, "CREATED", "created"
        };
        var orderEntryRepository = (AbstractProxyRepository<OrderEntry.OrderEntryId, OrderEntry>) vms.getRepositoryProxy("order_entries");
        OrderEntry oe = orderEntryRepository.parseObjectIntoEntity(entry);
        Assert.assertNotNull(oe);
    }

    @Test
    public void testComplexSellerDashboard() throws Exception {
        VmsApplication vms = getVmsApplication();
        vms.start();
        CustomerCheckout customerCheckout = new CustomerCheckout(
                1, "test", "test", "test", "test","test", "test", "test",
                "CREDIT_CARD","test","test","test", "test", "test", 1,"1");

        for(int i = 1; i <= 10; i++) {
            InvoiceIssued invoiceIssued = new InvoiceIssued( customerCheckout, i,  "test", new Date(), 100,
                    List.of(new OrderItem(i,1,1, "name", 1, 1.0f, new Date(),
                            1.0f, 1, 1.0f, 1.0f, 1.0f) )
                    , String.valueOf(i));
            InboundEvent inboundEvent = new InboundEvent(i, i-1, 1,
                    "invoice_issued", InvoiceIssued.class, invoiceIssued);
            vms.internalChannels().transactionInputQueue().add(inboundEvent);
        }

        sleep(2000);
        SellerService sellerService = vms.getService("dk.ku.di.dms.vms.marketplace.seller.SellerService");
        vms.getTransactionManager().beginTransaction(LAST_TID, 0, LAST_TID, true);
        SellerDashboard dash = sellerService.queryDashboard(1);
        Assert.assertNotNull(dash);
        Assert.assertEquals(10, dash.view.count_items);
        Assert.assertEquals(10, dash.view.count_orders);
    }

    @Test
    public void testSimpleSellerDashboard() throws Exception {
        VmsApplication vms = getVmsApplication();
        vms.start();
        generateInvoiceIssuedEvents(vms);
        sleep(2000);
        SellerService sellerService = vms.getService("dk.ku.di.dms.vms.marketplace.seller.SellerService");
        vms.getTransactionManager().beginTransaction(LAST_TID, 0, LAST_TID, true);
        SellerDashboard dash = sellerService.queryDashboard(1);
        Assert.assertNotNull(dash);
        Assert.assertEquals(1, dash.view.count_items);
        Assert.assertEquals(1, dash.view.count_orders);
    }

    @Test
    public void testParallelInvoiceIssued() throws Exception {
        VmsApplication vms = getVmsApplication();
        vms.start();
        insertSellers(vms);
        generateInvoiceIssuedEvents(vms);
        sleep(3000);
        Assert.assertEquals(LAST_TID, vms.lastTidFinished());
    }

    private static void generateInvoiceIssuedEvents(VmsApplication vms) {
        CustomerCheckout customerCheckout = new CustomerCheckout(
                1, "test", "test", "test", "test","test", "test", "test",
                "CREDIT_CARD","test","test","test", "test", "test", 1,"1");

        for(int i = 1; i <= MAX_SELLERS; i++) {
            InvoiceIssued invoiceIssued = new InvoiceIssued( customerCheckout, i,  "test", new Date(), 100,
                    List.of(new OrderItem(i,1,1, "name", i, 1.0f, new Date(),
                            1.0f, 1, 1.0f, 1.0f, 0.0f) )
                    , String.valueOf(i));
            InboundEvent inboundEvent = new InboundEvent(i, i-1, 1,
                    "invoice_issued", InvoiceIssued.class, invoiceIssued);
            vms.internalChannels().transactionInputQueue().add(inboundEvent);
        }
    }

    /**
     *  Add sellers first to avoid foreign key constraint violation
     */
    @SuppressWarnings("unchecked")
    private static void insertSellers(VmsApplication vms) {
        var sellerTable = vms.getTable("sellers");
        var sellerRepository = (AbstractProxyRepository<Integer, Seller>) vms.getRepositoryProxy("sellers");
        for(int i = 1; i <= MAX_SELLERS; i++){
            var seller = new Seller(i, "test", "test", "test",
                    "test", "test", "test", "test",
                    "test", "test", "test", "test", "test");
            Object[] obj = sellerRepository.extractFieldValuesFromEntityObject(seller);
            IKey key = KeyUtils.buildRecordKey( sellerTable.schema().getPrimaryKeyColumns(), obj );
            sellerTable.underlyingPrimaryKeyIndex().insert(key, obj);
        }
    }

    private static VmsApplication getVmsApplication() throws Exception {
        VmsApplicationOptions options = VmsApplicationOptions.build("localhost", Constants.SELLER_VMS_PORT, new String[]{
                "dk.ku.di.dms.vms.marketplace.seller",
                "dk.ku.di.dms.vms.marketplace.common"
        });
        return VmsApplication.build(options);
    }

}
