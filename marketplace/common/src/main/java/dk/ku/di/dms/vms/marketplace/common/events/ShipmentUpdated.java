package dk.ku.di.dms.vms.marketplace.common.events;

import dk.ku.di.dms.vms.modb.api.annotations.Event;

import java.util.List;

@Event
public final class ShipmentUpdated {

    public List<DeliveryNotification> deliveryNotifications;

    public List<ShipmentNotification> shipmentNotifications;

    public String instanceId;

    public ShipmentUpdated(){ }

    public ShipmentUpdated(List<DeliveryNotification> deliveryNotifications, List<ShipmentNotification> shipmentNotifications, String instanceId) {
        this.deliveryNotifications = deliveryNotifications;
        this.shipmentNotifications = shipmentNotifications;
        this.instanceId = instanceId;
    }

}
