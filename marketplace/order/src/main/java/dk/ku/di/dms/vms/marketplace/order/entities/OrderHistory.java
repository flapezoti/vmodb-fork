package dk.ku.di.dms.vms.marketplace.order.entities;

import dk.ku.di.dms.vms.marketplace.common.enums.OrderStatus;
import dk.ku.di.dms.vms.modb.api.annotations.VmsForeignKey;
import dk.ku.di.dms.vms.modb.api.annotations.VmsTable;
import dk.ku.di.dms.vms.modb.api.interfaces.IEntity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@VmsTable(name="order_history")
public final class OrderHistory implements IEntity<Integer> {

    @Id
    @GeneratedValue
    public int id;

    @Column
    @VmsForeignKey( table = Order.class, column = "customer_id")
    public int customer_id;

    @Column
    @VmsForeignKey( table = Order.class, column = "order_id")
    public int order_id;

    @Column
    public Date created_at;

    @Column
    public OrderStatus status;

    public OrderHistory() { }

    public OrderHistory(int customer_id, int order_id, Date created_at, OrderStatus status) {
        this.customer_id = customer_id;
        this.order_id = order_id;
        this.created_at = created_at;
        this.status = status;
    }
}
