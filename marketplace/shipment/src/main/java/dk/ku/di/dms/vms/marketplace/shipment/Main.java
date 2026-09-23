package dk.ku.di.dms.vms.marketplace.shipment;

import dk.ku.di.dms.vms.marketplace.common.Constants;
import dk.ku.di.dms.vms.sdk.embed.client.DefaultHttpHandler;
import dk.ku.di.dms.vms.sdk.embed.client.VmsApplication;
import dk.ku.di.dms.vms.sdk.embed.client.VmsApplicationOptions;

public final class Main {

    public static void main(String[] args) throws Exception {
        String[] packages = new String[]{
                "dk.ku.di.dms.vms.marketplace.shipment",
                "dk.ku.di.dms.vms.marketplace.common"
        };
        VmsApplicationOptions options = VmsApplicationOptions.build(
                "0.0.0.0",
                Constants.SHIPMENT_VMS_PORT,
                packages);
        VmsApplication vms = VmsApplication.build(options, (x, ignored) -> new DefaultHttpHandler(x));
        vms.start();
    }

}
