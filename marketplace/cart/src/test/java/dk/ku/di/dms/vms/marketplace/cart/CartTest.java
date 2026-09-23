package dk.ku.di.dms.vms.marketplace.cart;

import dk.ku.di.dms.vms.marketplace.cart.entities.CartItem;
import dk.ku.di.dms.vms.marketplace.cart.entities.ProductReplica;
import dk.ku.di.dms.vms.marketplace.cart.repositories.ICartItemRepository;
import dk.ku.di.dms.vms.marketplace.cart.repositories.IProductReplicaRepository;
import dk.ku.di.dms.vms.marketplace.common.Constants;
import dk.ku.di.dms.vms.marketplace.common.events.PriceUpdated;
import dk.ku.di.dms.vms.marketplace.common.inputs.CustomerCheckout;
import dk.ku.di.dms.vms.modb.definition.key.IKey;
import dk.ku.di.dms.vms.modb.definition.key.KeyUtils;
import dk.ku.di.dms.vms.modb.transaction.TransactionContext;
import dk.ku.di.dms.vms.modb.transaction.multiversion.index.NonUniqueSecondaryIndex;
import dk.ku.di.dms.vms.sdk.core.operational.InboundEvent;
import dk.ku.di.dms.vms.sdk.embed.client.VmsApplication;
import dk.ku.di.dms.vms.sdk.embed.client.VmsApplicationOptions;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static dk.ku.di.dms.vms.marketplace.common.Constants.CUSTOMER_CHECKOUT;
import static dk.ku.di.dms.vms.marketplace.common.Constants.PRICE_UPDATED;
import static java.lang.Thread.sleep;

public final class CartTest {

    @Test
    public void testRetrieveCartsForPriceUpdate() throws Exception {
        ICartItemRepository cartItemRepository;
        VmsApplication vms = loadCartVms();
        vms.start();
        IProductReplicaRepository productReplicaRepository = (IProductReplicaRepository) vms.getRepositoryProxy("product_replicas");
        cartItemRepository = (ICartItemRepository) vms.getRepositoryProxy("cart_items");

        vms.getTransactionManager().beginTransaction(1, 0, 0, false);
        productReplicaRepository.insert(new ProductReplica(
                1, 1, "test", "test", "test",
                "test", 10, 0, "test", "0"
        ));
        cartItemRepository.insert(new CartItem(1, 1, 1, "test", 10, 10, 1, 0, "0"));
        cartItemRepository.insert(new CartItem(1, 1, 2, "test", 10, 10, 1, 0, "0"));

        vms.getTransactionManager().beginTransaction(2, 0, 1, false);
        cartItemRepository.delete(new CartItem(1, 1, 1, "test", 10, 10, 1, 0, "0"));

        vms.getTransactionManager().beginTransaction(3, 0, 2, true);

        var items = cartItemRepository.getCartItemsBySellerIdAndProductIdAndVersion(1, 1, "0");
        Assert.assertEquals(1, items.size());
    }

    @Test
    public void testPriceUpdate() throws Exception {
        VmsApplication vms = loadCartVms();
        vms.start();
        IProductReplicaRepository productReplicaRepository = (IProductReplicaRepository) vms.getRepositoryProxy("product_replicas");

        var txCtx = (TransactionContext) vms.getTransactionManager().beginTransaction( 1, 0, 0, false );
        productReplicaRepository.insert(new ProductReplica(
                1, 1, "test", "test", "test",
                "test", 10, 0, "test", "0"
        ));
        Object[] cartItemRow1 = new Object[]{1, 1, 1, "test", 10, 10, 1, 0, "0"};
        Object[] cartItemRow2 = new Object[]{1, 1, 2, "test", 10, 10, 1, 0, "0"};
        insertCartItem(vms, txCtx, cartItemRow1);
        insertCartItem(vms, txCtx, cartItemRow2);

        PriceUpdated priceUpdated = new PriceUpdated(1, 1, 20, "0", "1");

        InboundEvent inboundEvent = new InboundEvent( 1, 0, 1,
                PRICE_UPDATED, PriceUpdated.class, priceUpdated );
        vms.internalChannels().transactionInputQueue().add( inboundEvent );

        sleep(3000);

        Assert.assertEquals(1, vms.lastTidFinished());

        ICartItemRepository cartItemRepository = (ICartItemRepository) vms.getRepositoryProxy("cart_items");
        List<CartItem> list = cartItemRepository.getCartItemsByCustomerId(1);

        Assert.assertEquals(1, list.size());

        ProductReplica productReplica = productReplicaRepository.lookupByKey(new ProductReplica.ProductId(1,1));

        Assert.assertEquals(20.0, productReplica.price, 0);
    }

    @Test
    public void testOneCheckout() throws Exception {
        VmsApplication vms = loadCartVms();
        vms.start();
        Object[] cartItemRow = new Object[]{1, 1, 1, "test", 10, 10, 1, 0, "0"};

        var txCtx = (TransactionContext) vms.getTransactionManager().beginTransaction(0, 0,0,false);
        insertCartItem(vms, txCtx, cartItemRow);

        CustomerCheckout customerCheckout = new CustomerCheckout(
                1, "test", "test", "test", "test","test", "test", "test",
                "CREDIT_CARD","test","test","test", "test", "test", 1,"1");

        InboundEvent inboundEvent = new InboundEvent( 1, 0, 1,
                CUSTOMER_CHECKOUT, CustomerCheckout.class, customerCheckout );
        vms.internalChannels().transactionInputQueue().add( inboundEvent );

        sleep(1000);

        Assert.assertEquals(1, vms.lastTidFinished());

        ICartItemRepository cartItemRepository = (ICartItemRepository) vms.getRepositoryProxy("cart_items");

        // register tid 2 and query the state of customer 1 cart items to see if they are deleted
        vms.getTransactionManager().beginTransaction( 2, 0, 1, true );
        List<CartItem> list = cartItemRepository.getCartItemsByCustomerId(1);
        Assert.assertTrue(list.isEmpty());
    }

    private static void insertCartItem(VmsApplication vms, TransactionContext txCtx, Object[] cartItemRow) {
        var table = vms.getTable("cart_items");
        IKey key = KeyUtils.buildRecordKey( table.schema().getPrimaryKeyColumns(), cartItemRow );
        table.primaryKeyIndex().insert(txCtx, key, cartItemRow);
        // add to customer idx
        NonUniqueSecondaryIndex secIdx = table.secondaryIndexMap.get( KeyUtils.buildIndexKey( new int[]{2} ) );
        secIdx.insert(txCtx, key, cartItemRow);
    }

    private static VmsApplication loadCartVms() throws Exception {
        VmsApplicationOptions options = VmsApplicationOptions.build(
                "localhost",
                Constants.CART_VMS_PORT, new String[]{
                "dk.ku.di.dms.vms.marketplace.cart",
                "dk.ku.di.dms.vms.marketplace.common"
        });
        return VmsApplication.build(options);
    }

    @Test
    public void testTwoCustomerUpdates() throws Exception {
        VmsApplication vms = loadCartVms();
        vms.start();
        // cart item for same customer id
        Object[] cartItemRow = new Object[]{1, 1, 1, "test", 10, 10, 1, 0, "0"};

        for(int i = 1; i <= 2; i++) {
            var txCtx = (TransactionContext) vms.getTransactionManager().beginTransaction(i-1, 0,i-1,false);
            System.out.println("Adding item " + i + " to the cart");
            insertCartItem(vms, txCtx, cartItemRow);
            CustomerCheckout customerCheckout = new CustomerCheckout(
                    1, "test", "test", "test", "test", "test", "test", "test",
                    "CREDIT_CARD", "test", "test", "test", "test", "test", 1, String.valueOf(i));
            InboundEvent inboundEvent = new InboundEvent(i, i-1, 1,
                    CUSTOMER_CHECKOUT, CustomerCheckout.class, customerCheckout);
            vms.internalChannels().transactionInputQueue().add(inboundEvent);
            // have to wait until the first transaction finishes in order to add
            // the new cart item and avoid deleting them all before the second transaction starts
            // this causes blocking the test
            // while(vms.lastTidFinished() != i);
            sleep(2000);
        }

        Assert.assertEquals(2, vms.lastTidFinished());

        ICartItemRepository cartItemRepository = (ICartItemRepository) vms.getRepositoryProxy("cart_items");

        // register tid 2 and query the state of customer 1 cart items to see if they are deleted
        vms.getTransactionManager().beginTransaction( 3, 0, 2, true );
        List<CartItem> list = cartItemRepository.getCartItemsByCustomerId(1);
        Assert.assertTrue(list.isEmpty());
    }

}
