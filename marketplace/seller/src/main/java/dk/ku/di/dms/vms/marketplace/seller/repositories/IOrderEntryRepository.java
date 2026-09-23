package dk.ku.di.dms.vms.marketplace.seller.repositories;

import dk.ku.di.dms.vms.marketplace.seller.entities.OrderEntry;
import dk.ku.di.dms.vms.modb.api.annotations.Query;
import dk.ku.di.dms.vms.modb.api.interfaces.IRepository;

import java.util.List;

public interface IOrderEntryRepository extends IRepository<OrderEntry.OrderEntryId, OrderEntry> {

    @Query("select * from order_entries where customer_id = :customerId and order_id = :orderId")
    List<OrderEntry> getOrderEntriesByCustomerIdAndOrderId(int customerId, int orderId);

    @Query("select * from order_entries where seller_id = :sellerId")
    List<OrderEntry> getOrderEntriesBySellerId(int sellerId);

}
