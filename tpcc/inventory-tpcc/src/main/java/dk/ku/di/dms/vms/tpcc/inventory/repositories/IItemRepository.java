package dk.ku.di.dms.vms.tpcc.inventory.repositories;

import dk.ku.di.dms.vms.modb.api.annotations.Query;
import dk.ku.di.dms.vms.modb.api.annotations.Repository;
import dk.ku.di.dms.vms.modb.api.interfaces.IRepository;
import dk.ku.di.dms.vms.tpcc.inventory.entities.Item;

@Repository
public interface IItemRepository extends IRepository<Integer, Item> {

    @Query("SELECT i_price FROM item WHERE i_id IN (:itemIds)")
    float[] getPricePerItemId(int[] itemIds);

}