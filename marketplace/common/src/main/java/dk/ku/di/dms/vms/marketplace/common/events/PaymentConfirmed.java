package dk.ku.di.dms.vms.marketplace.common.events;

import dk.ku.di.dms.vms.marketplace.common.entities.OrderItem;
import dk.ku.di.dms.vms.marketplace.common.inputs.CustomerCheckout;
import dk.ku.di.dms.vms.modb.api.annotations.Event;

import java.util.Date;
import java.util.List;

@Event
public final class PaymentConfirmed {

    public CustomerCheckout customerCheckout;

    public int orderId;
    
    public float totalAmount;
    
    public List<OrderItem> items;
    
    public Date date;
    
    public String instanceId;
    
    public PaymentConfirmed(){ }

    public PaymentConfirmed(CustomerCheckout customerCheckout, int orderId, float totalAmount,
                            List<OrderItem> items, Date date, String instanceId) {
        this.customerCheckout = customerCheckout;
        this.orderId = orderId;
        this.totalAmount = totalAmount;
        this.items = items;
        this.date = date;
        this.instanceId = instanceId;
    }

}
