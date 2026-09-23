package dk.ku.di.dms.vms.marketplace.order.entities;

import dk.ku.di.dms.vms.modb.api.annotations.VmsForeignKey;
import dk.ku.di.dms.vms.modb.api.annotations.VmsTable;
import dk.ku.di.dms.vms.modb.api.interfaces.IEntity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.util.Date;

@VmsTable(name="order_items")
@IdClass(OrderItem.OrderItemId.class)
public final class OrderItem implements IEntity<OrderItem.OrderItemId> {

    public static class OrderItemId implements Serializable {

        public int customer_id;
        public int order_id;
        public int order_item_id;

        public OrderItemId(){}

        public OrderItemId(int customer_id, int order_id, int order_item_id) {
            this.customer_id = customer_id;
            this.order_id = order_id;
            this.order_item_id = order_item_id;
        }
    }

    @Id
    @VmsForeignKey(table = Order.class, column = "customer_id")
    public int customer_id;

    @Id
    @VmsForeignKey(table = Order.class, column = "order_id")
    public int order_id;

    @Id
    public int order_item_id;

    @Column
    public int product_id;

    @Column
    public String product_name;

    @Column
    public int seller_id;

    @Column
    public float unit_price;

    @Column
    public Date shipping_limit_date;

    @Column
    public float freight_value;

    @Column
    public int quantity;

    @Column
    public float total_items;

    @Column
    public float total_amount;

    // can be derived from total_items - total_amount
    // incentive of item is not of concern to the order
    // the seller must compute
    // public float total_incentive;

    public OrderItem() { }

    public OrderItem(int customer_id, int order_id, int order_item_id, int product_id, String product_name, int seller_id,
                     float unit_price, Date shipping_limit_date,
                     float freight_value, int quantity, float total_items, float total_amount) {
        this.customer_id = customer_id;
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
    }

    public dk.ku.di.dms.vms.marketplace.common.entities.OrderItem toCommonOrderItem(float voucher){
        return new dk.ku.di.dms.vms.marketplace.common.entities.OrderItem(order_id, order_item_id,
                product_id, product_name, seller_id, unit_price, shipping_limit_date, freight_value,
                quantity, total_items, total_amount, voucher);
    }

}
