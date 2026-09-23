package dk.ku.di.dms.vms.marketplace.payment;

import dk.ku.di.dms.vms.marketplace.common.Constants;
import dk.ku.di.dms.vms.marketplace.common.entities.OrderItem;
import dk.ku.di.dms.vms.marketplace.common.events.InvoiceIssued;
import dk.ku.di.dms.vms.marketplace.common.inputs.CustomerCheckout;
import dk.ku.di.dms.vms.sdk.core.operational.InboundEvent;
import dk.ku.di.dms.vms.sdk.embed.client.VmsApplication;
import dk.ku.di.dms.vms.sdk.embed.client.VmsApplicationOptions;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static java.lang.Thread.sleep;

public class PaymentTest {

    @Test
    public void test() throws Exception {

        VmsApplicationOptions options = VmsApplicationOptions.build("localhost", Constants.PAYMENT_VMS_PORT, new String[]{
                "dk.ku.di.dms.vms.marketplace.payment",
                "dk.ku.di.dms.vms.marketplace.common"
        });

        VmsApplication vms = VmsApplication.build(options);
        vms.start();

        CustomerCheckout customerCheckout = new CustomerCheckout(
                1, "test", "test", "test", "test","test", "test", "test",
                "CREDIT_CARD","test","test","test", "test", "test", 1,"1");

        InvoiceIssued invoiceIssued = new InvoiceIssued( customerCheckout, 1,  "test", new Date(), 100,
                List.of(new OrderItem(1,1,1, "name", 1, 1.0f, new Date(), 1.0f, 1, 1.0f, 1.0f, 0.0f) )
                , "1");

        InboundEvent inboundEvent = new InboundEvent( 1, 0, 1,
                "invoice_issued", InvoiceIssued.class, invoiceIssued );
        vms.internalChannels().transactionInputQueue().add( inboundEvent );

        sleep(100000);

    }

}
