package dk.ku.di.dms.vms.marketplace.order.entities;

import dk.ku.di.dms.vms.modb.api.annotations.VmsTable;
import dk.ku.di.dms.vms.modb.api.interfaces.IEntity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.Positive;

@VmsTable(name="customer_orders")
public final class CustomerOrder implements IEntity<Integer> {

    @Id
    public int customer_id;

    @Column
    @Positive
    public int next_order_id;

    public CustomerOrder() { }

    public CustomerOrder(int customer_id, int next_order_id) {
        this.customer_id = customer_id;
        this.next_order_id = next_order_id;
    }

}
