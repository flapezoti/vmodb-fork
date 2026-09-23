package dk.ku.di.dms.vms.marketplace.common;

public final class Constants {

    /**
     * PORTS
     */
    public static final int CART_VMS_PORT = 8080;

    public static final int PRODUCT_VMS_PORT = 8081;

    public static final int STOCK_VMS_PORT = 8082;

    public static final int ORDER_VMS_PORT = 8083;

    public static final int PAYMENT_VMS_PORT = 8084;

    public static final int SHIPMENT_VMS_PORT = 8085;

    public static final int CUSTOMER_VMS_PORT = 8086;

    public static final int SELLER_VMS_PORT = 8087;

    /**
     * INPUTS
     */
    public static final String UPDATE_PRODUCT = "update_product";

    // in modb, we cannot schedule an input event to hit more than a single microservice.
    // that would prevent the product to create an additional event just to synchronize stock and cart
    // only terminal and terminal nodes can receive the same event
    public static final String UPDATE_PRICE = "update_price";

    public static final String CUSTOMER_CHECKOUT = "customer_checkout";

    public static final String UPDATE_DELIVERY = "update_delivery";

    /**
     * EVENTS
     */
    public static final String PRODUCT_UPDATED = "product_updated";

    public static final String  PRICE_UPDATED = "price_updated";

    public static final String RESERVE_STOCK = "reserve_stock";

    public static final String STOCK_CONFIRMED = "stock_confirmed";

    public static final String INVOICE_ISSUED = "invoice_issued";

    public static final String PAYMENT_CONFIRMED = "payment_confirmed";

    public static final String SHIPMENT_UPDATED = "shipment_updated";

}
