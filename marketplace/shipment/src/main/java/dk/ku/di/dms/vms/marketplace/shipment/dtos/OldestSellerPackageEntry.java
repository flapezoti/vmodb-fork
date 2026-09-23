package dk.ku.di.dms.vms.marketplace.shipment.dtos;

import java.util.Date;

public final class OldestSellerPackageEntry {

    public int seller_id;
    public int customer_id;
    public int order_id;
    public Date shipping_date;

    public OldestSellerPackageEntry() { }

    public int getSellerId() {
        return seller_id;
    }

    public int getCustomerId() {
        return customer_id;
    }

    public int getOrderId() {
        return order_id;
    }

    public Date getShippingDate() {
        return shipping_date;
    }

}
