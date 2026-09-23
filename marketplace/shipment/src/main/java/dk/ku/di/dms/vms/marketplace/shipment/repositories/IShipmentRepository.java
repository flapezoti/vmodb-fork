package dk.ku.di.dms.vms.marketplace.shipment.repositories;

import dk.ku.di.dms.vms.marketplace.shipment.entities.Shipment;
import dk.ku.di.dms.vms.modb.api.interfaces.IRepository;

public interface IShipmentRepository extends IRepository<Shipment.ShipmentId, Shipment> {
}
