package dk.ku.di.dms.vms.marketplace.order.repositories;

import dk.ku.di.dms.vms.marketplace.order.entities.CustomerOrder;
import dk.ku.di.dms.vms.modb.api.interfaces.IRepository;

public interface ICustomerOrderRepository extends IRepository<Integer, CustomerOrder> { }
