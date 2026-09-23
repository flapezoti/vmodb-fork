package dk.ku.di.dms.vms.marketplace.common.entities;

import java.util.Date;

public final class OrderItem {

    public int order_id;

    public int order_item_id;

    public int seller_id;

    public int product_id;

    public String product_name;

    public float unit_price;

    public Date shipping_limit_date;

    public float freight_value;

    public int quantity;

    public float total_items;

    public float total_amount;

    public float total_incentive;

    public OrderItem() { }

    public OrderItem(int order_id, int order_item_id, int product_id, String product_name, int seller_id, float unit_price, Date shipping_limit_date,
                     float freight_value, int quantity, float total_items, float total_amount, float total_incentive) {
        this.order_id = order_id;
        this.order_item_id = order_item_id;
        this.product_id = product_id;
        this.product_name = product_name;
        this.seller_id = seller_id;
        this.unit_price = unit_price;
        this.shipping_limit_date = shipping_limit_date;
        this.freight_value = freight_value;
        this.quantity = quantity;
        this.total_items = total_items;
        this.total_amount = total_amount;
        this.total_incentive = total_incentive;
    }

    public float getFreightValue() {
        return this.freight_value;
    }

}
