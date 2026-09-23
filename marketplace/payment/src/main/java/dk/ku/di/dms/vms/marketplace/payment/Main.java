package dk.ku.di.dms.vms.marketplace.payment;

import dk.ku.di.dms.vms.marketplace.common.Constants;
import dk.ku.di.dms.vms.sdk.embed.client.DefaultHttpHandler;
import dk.ku.di.dms.vms.sdk.embed.client.VmsApplication;
import dk.ku.di.dms.vms.sdk.embed.client.VmsApplicationOptions;

public final class Main {

    public static void main(String[] ignored1) throws Exception {
        VmsApplicationOptions options = VmsApplicationOptions.build(
                "0.0.0.0",
                Constants.PAYMENT_VMS_PORT, new String[]{
                "dk.ku.di.dms.vms.marketplace.payment",
                "dk.ku.di.dms.vms.marketplace.common"
        });
        VmsApplication vms = VmsApplication.build(options, (x, ignored2) -> new DefaultHttpHandler(x));
        vms.start();
    }

}
