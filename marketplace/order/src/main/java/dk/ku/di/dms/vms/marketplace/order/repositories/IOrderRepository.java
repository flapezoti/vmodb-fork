package dk.ku.di.dms.vms.marketplace.order.repositories;

import dk.ku.di.dms.vms.marketplace.order.entities.Order;
import dk.ku.di.dms.vms.modb.api.interfaces.IRepository;

public interface IOrderRepository extends IRepository<Order.OrderId, Order> { }
