package dk.ku.di.dms.vms.marketplace.seller.entities;

import dk.ku.di.dms.vms.marketplace.common.enums.OrderStatus;
import dk.ku.di.dms.vms.marketplace.common.enums.PackageStatus;
import dk.ku.di.dms.vms.modb.api.annotations.VmsIndex;
import dk.ku.di.dms.vms.modb.api.annotations.VmsTable;
import dk.ku.di.dms.vms.modb.api.interfaces.IEntity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.util.Date;

@VmsTable(name="order_entries")
@IdClass(OrderEntry.OrderEntryId.class)
public final class OrderEntry implements IEntity<OrderEntry.OrderEntryId> {

    public static class OrderEntryId implements Serializable {
        public int customer_id;
        public int order_id;
        public int seller_id;
        public int product_id;

        public OrderEntryId() { }

        public OrderEntryId(int customer_id, int order_id, int seller_id, int product_id) {
            this.customer_id = customer_id;
            this.order_id = order_id;
            this.seller_id = seller_id;
            this.product_id = product_id;
        }
    }

    @Id
    public int customer_id;

    @Id
    public int order_id;

    @Id
    public int product_id;

    @Id
    @VmsIndex(name = "seller_idx")
    public int seller_id;

    @Column
    public String c_o_id;

    @Column
    public int package_id;

    @Column
    public String product_name;

    @Column
    public String product_category = "";

    @Column
    public float unit_price;

    @Column
    public int quantity;

    @Column
    public float total_items;

    @Column
    public float total_amount;

    @Column
    public float total_invoice;

    @Column
    public float total_incentive;

    @Column
    public float freight_value;

    @Column
    public Date shipment_date;

    @Column
    public Date delivery_date;

    @Column
    public OrderStatus order_status;

    @Column
    public PackageStatus delivery_status;

    public OrderEntry() { }

    public OrderEntry(int customer_id, int order_id, int product_id, int seller_id, int package_id, String product_name, String product_category, float unit_price, int quantity, float total_items, float total_amount, float total_invoice, float total_incentive, float freight_value, Date shipment_date, Date delivery_date, OrderStatus order_status, PackageStatus delivery_status) {
        this.customer_id = customer_id;
        this.order_id = order_id;
        this.product_id = product_id;
        this.seller_id = seller_id;
        this.package_id = package_id;
        this.product_name = product_name;
        this.product_category = product_category;
        this.unit_price = unit_price;
        this.quantity = quantity;
        this.total_items = total_items;
        this.total_amount = total_amount;
        this.total_invoice = total_invoice;
        this.total_incentive = total_incentive;
        this.freight_value = freight_value;
        this.shipment_date = shipment_date;
        this.delivery_date = delivery_date;
        this.order_status = order_status;
        this.delivery_status = delivery_status;
        this.c_o_id = this.customer_id + "|" + this.order_id;
    }

    @Override
    public OrderEntryId getId(){
        return new OrderEntryId( this.customer_id, this.order_id, this.seller_id, this.product_id );
    }

    public float getTotalItems() {
        return total_items;
    }

    public float getTotalAmount() {
        return total_amount;
    }

    public float getTotalInvoice() {
        return total_invoice;
    }

    public float getTotalIncentive() {
        return total_incentive;
    }

    public float getFreightValue() {
        return freight_value;
    }

    @Override
    public String toString() {
        return "{"
                + "\"customer_id\":\"" + customer_id + "\""
                + ",\"order_id\":\"" + order_id + "\""
                + ",\"seller_id\":\"" + seller_id + "\""
                + ",\"product_id\":\"" + product_id + "\""
                + ",\"package_id\":\"" + package_id + "\""
                + ",\"product_name\":\"" + product_name + "\""
                + ",\"product_category\":\"" + product_category + "\""
                + ",\"unit_price\":\"" + unit_price + "\""
                + ",\"quantity\":\"" + quantity + "\""
                + ",\"total_items\":\"" + total_items + "\""
                + ",\"total_amount\":\"" + total_amount + "\""
                + ",\"total_invoice\":\"" + total_invoice + "\""
                + ",\"total_incentive\":\"" + total_incentive + "\""
                + ",\"freight_value\":\"" + freight_value + "\""
                + ",\"shipment_date\":" + shipment_date
                + ",\"delivery_date\":" + delivery_date
                + ",\"order_status\":\"" + order_status + "\""
                + ",\"delivery_status\":\"" + delivery_status + "\""
                + "}";
    }
}
