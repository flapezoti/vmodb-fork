package dk.ku.di.dms.vms.marketplace.common.events;

import dk.ku.di.dms.vms.marketplace.common.enums.PackageStatus;
import dk.ku.di.dms.vms.modb.api.annotations.Event;

import java.util.Date;

@Event
public final class DeliveryNotification {

    public int customerId;

    public int orderId;

    public int packageId;

    public int sellerId;

    public int productId;

    public String productName;

    public PackageStatus packageStatus;

    public Date deliveryDate;
    
    public DeliveryNotification(){}

    public DeliveryNotification(int customerId, int orderId, int packageId, int sellerId, int productId, String productName, PackageStatus packageStatus, Date deliveryDate) {
        this.customerId = customerId;
        this.orderId = orderId;
        this.packageId = packageId;
        this.sellerId = sellerId;
        this.productId = productId;
        this.productName = productName;
        this.packageStatus = packageStatus;
        this.deliveryDate = deliveryDate;
    }

}
