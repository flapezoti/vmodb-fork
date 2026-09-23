package dk.ku.di.dms.vms.marketplace.order.entities;

import dk.ku.di.dms.vms.marketplace.common.enums.OrderStatus;
import dk.ku.di.dms.vms.modb.api.annotations.VmsTable;
import dk.ku.di.dms.vms.modb.api.interfaces.IEntity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.util.Date;

@VmsTable(name="orders")
@IdClass(Order.OrderId.class)
public final class Order implements IEntity<Order.OrderId> {

    public static class OrderId implements Serializable {

        public int customer_id;
        public int order_id;

        public OrderId(){}

        public OrderId(int customer_id, int order_id) {
            this.customer_id = customer_id;
            this.order_id = order_id;
        }
    }

    @Id
    public int customer_id;

    @Id
    public int order_id;

    @Column
    public String invoice_number;

    @Column
    public OrderStatus status;

    @Column
    public Date purchase_date;

    @Column
    public Date payment_date;

    @Column
    public Date delivered_carrier_date;

    @Column
    public Date delivered_customer_date;

    @Column
    public Date estimated_delivery_date;

    @Column
    public int count_items;

    @Column
    public float total_amount;

    @Column
    public float total_freight;

    @Column
    public float total_incentive;

    @Column
    public float total_invoice;

    @Column
    public float total_items;

    @Column
    public Date created_at;

    @Column
    public Date updated_at;
    
    public Order(){}

    public Order(int customer_id, int order_id, String invoice_number, OrderStatus status, Date purchase_date,
                 Date payment_date, Date delivered_carrier_date, Date delivered_customer_date, Date estimated_delivery_date,
                 int count_items, float total_amount, float total_freight,
                 float total_incentive, float total_invoice, float total_items, Date created_at, Date updated_at) {
        this.customer_id = customer_id;
        this.order_id = order_id;
        this.invoice_number = invoice_number;
        this.status = status;
        this.purchase_date = purchase_date;
        this.payment_date = payment_date;
        this.delivered_carrier_date = delivered_carrier_date;
        this.delivered_customer_date = delivered_customer_date;
        this.estimated_delivery_date = estimated_delivery_date;
        this.count_items = count_items;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.total_amount = total_amount;
        this.total_freight = total_freight;
        this.total_incentive = total_incentive;
        this.total_invoice = total_invoice;
        this.total_items = total_items;
    }

}
