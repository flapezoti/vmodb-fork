package dk.ku.di.dms.vms.marketplace.shipment.entities;

import dk.ku.di.dms.vms.marketplace.common.enums.PackageStatus;
import dk.ku.di.dms.vms.modb.api.annotations.VmsForeignKey;
import dk.ku.di.dms.vms.modb.api.annotations.VmsPartialIndex;
import dk.ku.di.dms.vms.modb.api.annotations.VmsTable;
import dk.ku.di.dms.vms.modb.api.interfaces.IEntity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.util.Date;

@VmsTable(name="packages")
@IdClass(Package.PackageId.class)
public final class Package implements IEntity<Package.PackageId> {

    public static class PackageId implements Serializable {

        public int customer_id;
        public int order_id;
        public int package_id;

        public PackageId(){}

        public PackageId(int customer_id, int order_id, int package_id) {
            this.customer_id = customer_id;
            this.order_id = order_id;
            this.package_id = package_id;
        }

    }

    @Id
    @VmsForeignKey(table = Shipment.class, column = "customer_id")
    public int customer_id;

    @Id
    @VmsForeignKey(table = Shipment.class, column = "order_id")
    public int order_id;

    @Id
    public int package_id;

    @Column
    public int seller_id;

    @Column
    public int product_id;

    @Column
    public String product_name;

    @Column
    public float freight_value;

    @Column
    public Date shipping_date;

    @Column
    public Date delivery_date;

    @Column
    public int quantity;

    @Column
    @VmsPartialIndex(name = "shipped_idx", value = "shipped")
    public PackageStatus status;

    public Package() { }

    public Package(int customer_id, int order_id, int package_id, int seller_id,
                   int product_id, String product_name, float freight_value, Date shipping_date,
                   Date delivery_date, int quantity, PackageStatus status) {
        this.customer_id = customer_id;
        this.order_id = order_id;
        this.package_id = package_id;
        this.seller_id = seller_id;
        this.product_id = product_id;
        this.product_name = product_name;
        this.freight_value = freight_value;
        this.shipping_date = shipping_date;
        this.delivery_date = delivery_date;
        this.quantity = quantity;
        this.status = status;
    }

    @Override
    public PackageId getId(){
        return new PackageId( this.customer_id, this.order_id, this.package_id );
    }

    public int getSellerId() {
        return this.seller_id;
    }

    public Date getShippingDate(){
        return this.shipping_date;
    }

}
