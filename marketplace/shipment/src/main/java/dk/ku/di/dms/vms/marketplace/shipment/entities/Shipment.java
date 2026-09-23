package dk.ku.di.dms.vms.marketplace.shipment.entities;

import dk.ku.di.dms.vms.marketplace.common.enums.ShipmentStatus;
import dk.ku.di.dms.vms.modb.api.annotations.VmsTable;
import dk.ku.di.dms.vms.modb.api.interfaces.IEntity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.util.Date;

@VmsTable(name="shipments")
@IdClass(Shipment.ShipmentId.class)
public final class Shipment implements IEntity<Shipment.ShipmentId> {

    public static class ShipmentId implements Serializable {

        public int customer_id;
        public int order_id;

        public ShipmentId(){}

        public ShipmentId(int customer_id, int order_id) {
            this.customer_id = customer_id;
            this.order_id = order_id;
        }

    }

    @Id
    public int customer_id;

    @Id
    public int order_id;

    @Column
    public int package_count;

    @Column
    public float total_freight_value;

    @Column
    public Date request_date;

    @Column
    public ShipmentStatus status;

    @Column
    public String first_name;

    @Column
    public String last_name;

    @Column
    public String street;

    @Column
    public String complement;

    @Column
    public String zip_code;

    @Column
    public String city;

    @Column
    public String state;

    public Shipment(){}

    public Shipment(
            int customerId,
            int orderId,
            int packageCount,
            float totalFreight,
            Date requestDate,
            ShipmentStatus status,
            String firstName,
            String lastName,
            String street,
            String complement,
            String zipCode,
            String city,
            String state) {
        this.customer_id = customerId;
        this.order_id = orderId;
        this.package_count = packageCount;
        this.total_freight_value = totalFreight;
        this.request_date = requestDate;
        this.status = status;
        this.first_name = firstName;
        this.last_name = lastName;
        this.street = street;
        this.complement = complement;
        this.zip_code = zipCode;
        this.city = city;
        this.state = state;
    }

}
