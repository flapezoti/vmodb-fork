package dk.ku.di.dms.vms.marketplace.order;

import dk.ku.di.dms.vms.marketplace.common.Constants;
import dk.ku.di.dms.vms.marketplace.common.entities.CartItem;
import dk.ku.di.dms.vms.marketplace.common.events.StockConfirmed;
import dk.ku.di.dms.vms.marketplace.common.inputs.CustomerCheckout;
import dk.ku.di.dms.vms.sdk.core.operational.InboundEvent;
import dk.ku.di.dms.vms.sdk.embed.client.VmsApplication;
import dk.ku.di.dms.vms.sdk.embed.client.VmsApplicationOptions;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static java.lang.Thread.sleep;

public final class OrderTest {

    @Test
    public void testConcurrentCustomerCheckouts() throws Exception {
        VmsApplication vms = buildOrderVms();
        vms.start();
        for (int i = 1; i <= 2; i++) {
            InboundEvent inboundEvent = getInboundEvent(i);
            vms.internalChannels().transactionInputQueue().add(inboundEvent);
        }
        sleep(100000);
        assert vms.lastTidFinished() == 2;
    }

    @Test
    public void simpleTest() throws Exception {
        VmsApplication vms = buildOrderVms();
        vms.start();
        InboundEvent inboundEvent = getInboundEvent(1);
        vms.internalChannels().transactionInputQueue().add( inboundEvent );
        sleep(1000);
        assert vms.lastTidFinished() == 1;
    }

    private static VmsApplication buildOrderVms() throws Exception {
        VmsApplicationOptions options = VmsApplicationOptions.build(
                "localhost", Constants.ORDER_VMS_PORT, new String[]{
                "dk.ku.di.dms.vms.marketplace.order",
                "dk.ku.di.dms.vms.marketplace.common"
        });
        return VmsApplication.build(options);
    }

    private static InboundEvent getInboundEvent(long tid) {
        CustomerCheckout customerCheckout = new CustomerCheckout(
                1, "test", "test", "test", "test","test", "test", "test",
                "CREDIT_CARD","test","test","test", "test", "test", 1,"1");

        StockConfirmed stockConfirmed = new StockConfirmed(
                new Date(),
                customerCheckout,
                List.of( new CartItem(1,1,"test",1.0f, 1.0f, 1, 1.0f, "1") ),
                "1" );

        return new InboundEvent( tid, tid-1, 0,
                "stock_confirmed", StockConfirmed.class, stockConfirmed );
    }

}
