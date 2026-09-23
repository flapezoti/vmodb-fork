package dk.ku.di.dms.vms.tpcc.warehouse.repositories;

import dk.ku.di.dms.vms.modb.api.annotations.Query;
import dk.ku.di.dms.vms.modb.api.annotations.Repository;
import dk.ku.di.dms.vms.modb.api.interfaces.IRepository;
import dk.ku.di.dms.vms.tpcc.warehouse.entities.Warehouse;

@Repository
public interface IWarehouseRepository extends IRepository<Integer, Warehouse> {

    @Query("SELECT w_tax FROM warehouse WHERE w_id = :w_id")
    float getWarehouseTax(int w_id);

}