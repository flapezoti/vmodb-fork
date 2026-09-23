package dk.ku.di.dms.vms.marketplace.payment.repositories;

import dk.ku.di.dms.vms.marketplace.payment.entities.OrderPaymentCard;
import dk.ku.di.dms.vms.modb.api.interfaces.IRepository;

public interface IOrderPaymentCardRepository extends IRepository<OrderPaymentCard.OrderPaymentCardId, OrderPaymentCard> { }
