package dk.ku.di.dms.vms.marketplace.cart.infra;

import dk.ku.di.dms.vms.marketplace.cart.entities.CartItem;
import dk.ku.di.dms.vms.marketplace.cart.repositories.ICartItemRepository;
import dk.ku.di.dms.vms.modb.common.serdes.IVmsSerdesProxy;
import dk.ku.di.dms.vms.modb.common.serdes.VmsSerdesProxyBuilder;
import dk.ku.di.dms.vms.modb.common.transaction.ITransactionManager;
import dk.ku.di.dms.vms.sdk.embed.client.DefaultHttpHandler;

import java.util.List;

public final class CartHttpHandler extends DefaultHttpHandler {

    private final ICartItemRepository repository;
    private static final IVmsSerdesProxy SERDES = VmsSerdesProxyBuilder.build();

    public CartHttpHandler(ITransactionManager transactionManager,
                           ICartItemRepository repository){
        super(transactionManager);
        this.repository = repository;
    }

    @Override
    public void patch(String uri, String body) {
        String[] split = uri.split("/");
        String op = split[split.length - 1];
        if(op.contentEquals("add")) {
            int customerId = Integer.parseInt(split[split.length - 2]);
            dk.ku.di.dms.vms.marketplace.common.entities.CartItem cartItemAPI =
                    SERDES.deserialize(body, dk.ku.di.dms.vms.marketplace.common.entities.CartItem.class);
            this.transactionManager.beginTransaction(0, 0, 0, false);
            this.repository.insert(CartUtils.convertCartItemAPI(customerId, cartItemAPI));
            return;
        }
        this.transactionManager.reset();
    }

    @Override
    public String getAsJson(String uri) {
        String[] split = uri.split("/");
        int customerId = Integer.parseInt(split[split.length - 1]);
        this.transactionManager.beginTransaction( 0, 0, 0,true );
        List<CartItem> cartItems = this.repository.getCartItemsByCustomerId(customerId);
        return SERDES.serializeList(cartItems);
    }
}