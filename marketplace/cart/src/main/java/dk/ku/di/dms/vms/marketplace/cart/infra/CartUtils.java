package dk.ku.di.dms.vms.marketplace.cart.infra;

import dk.ku.di.dms.vms.marketplace.common.entities.CartItem;

import java.util.List;

public final class CartUtils {

    public static List<CartItem> convertCartItems(List<dk.ku.di.dms.vms.marketplace.cart.entities.CartItem> cartItems){
        return cartItems.stream().map(CartUtils::convertCartItemEntity).toList();
    }

    public static CartItem convertCartItemEntity(dk.ku.di.dms.vms.marketplace.cart.entities.CartItem cartItem){
        return new dk.ku.di.dms.vms.marketplace.common.entities.CartItem( cartItem.seller_id, cartItem.product_id, cartItem.product_name, cartItem.unit_price, cartItem.freight_value, cartItem.quantity, cartItem.voucher, cartItem.version);
    }

    public static dk.ku.di.dms.vms.marketplace.cart.entities.CartItem convertCartItemAPI(int customerId, CartItem cartItem){
        return new dk.ku.di.dms.vms.marketplace.cart.entities.CartItem( cartItem.SellerId, cartItem.ProductId, customerId, cartItem.ProductName, cartItem.UnitPrice, cartItem.FreightValue, cartItem.Quantity, cartItem.Voucher, cartItem.Version);
    }

}
