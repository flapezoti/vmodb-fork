package dk.ku.di.dms.vms.marketplace.shipment.repositories;

import dk.ku.di.dms.vms.marketplace.shipment.entities.Package;
import dk.ku.di.dms.vms.modb.api.annotations.Query;
import dk.ku.di.dms.vms.modb.api.interfaces.IRepository;

import java.util.List;

public interface IPackageRepository extends IRepository<Package.PackageId, Package> {

    @Query("select * from packages where customer_id = :customerId and order_id = :orderId")
    List<Package> getPackagesByCustomerIdAndOrderId(int customerId, int orderId);

}
