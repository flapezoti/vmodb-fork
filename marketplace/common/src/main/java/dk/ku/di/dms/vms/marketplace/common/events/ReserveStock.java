package dk.ku.di.dms.vms.marketplace.common.events;

import dk.ku.di.dms.vms.marketplace.common.entities.CartItem;
import dk.ku.di.dms.vms.marketplace.common.inputs.CustomerCheckout;
import dk.ku.di.dms.vms.modb.api.annotations.Event;

import java.util.Date;
import java.util.List;

/**
 * A command to reserve stock
 */
@Event
public final class ReserveStock {

    public Date timestamp;

    public CustomerCheckout customerCheckout;

    public List<CartItem> items;

    public String instanceId;

    public ReserveStock(){}

    public ReserveStock(Date timestamp, CustomerCheckout customerCheckout, List<CartItem> items, String instanceId) {
        this.timestamp = timestamp;
        this.customerCheckout = customerCheckout;
        this.items = items;
        this.instanceId = instanceId;
    }

}
