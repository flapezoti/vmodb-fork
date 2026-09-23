package dk.ku.di.dms.vms.marketplace.payment.repositories;

import dk.ku.di.dms.vms.marketplace.payment.entities.OrderPayment;
import dk.ku.di.dms.vms.modb.api.interfaces.IRepository;

public interface IOrderPaymentRepository extends IRepository<OrderPayment.OrderPaymentId, OrderPayment> { }
