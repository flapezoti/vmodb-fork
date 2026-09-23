package dk.ku.di.dms.vms.marketplace.common.events;

import dk.ku.di.dms.vms.marketplace.common.enums.ShipmentStatus;

import java.util.Date;

public final class ShipmentNotification {

    public int customerId;

    public int orderId;
    
    public ShipmentStatus status;
    
    public Date eventDate;

    public ShipmentNotification() {
    }

    public ShipmentNotification(int customerId, int orderId, ShipmentStatus status, Date eventDate) {
        this.customerId = customerId;
        this.orderId = orderId;
        this.status = status;
        this.eventDate = eventDate;
    }

}
