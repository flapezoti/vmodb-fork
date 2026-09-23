package dk.ku.di.dms.vms.marketplace.cart;

import dk.ku.di.dms.vms.marketplace.cart.infra.CartHttpHandler;
import dk.ku.di.dms.vms.marketplace.cart.repositories.ICartItemRepository;
import dk.ku.di.dms.vms.marketplace.common.Constants;
import dk.ku.di.dms.vms.modb.common.utils.ConfigUtils;
import dk.ku.di.dms.vms.sdk.embed.client.VmsApplication;
import dk.ku.di.dms.vms.sdk.embed.client.VmsApplicationOptions;

import java.util.Properties;

public final class Main {

    private static final System.Logger LOGGER = System.getLogger(Main.class.getName());

    public static void main(String[] ignoredArgs) throws Exception {
        Properties properties = ConfigUtils.loadProperties();
        VmsApplication vms = buildVms(properties);
        vms.start();
    }

    private static VmsApplication buildVms(Properties properties) throws Exception {
        VmsApplicationOptions options = VmsApplicationOptions.build(
                properties,
                "0.0.0.0",
                Constants.CART_VMS_PORT, new String[]{
                "dk.ku.di.dms.vms.marketplace.cart",
                "dk.ku.di.dms.vms.marketplace.common"
        });
        return VmsApplication.build(options, (x,z) -> new CartHttpHandler(x, (ICartItemRepository) z.apply("cart_items")));
    }

}
