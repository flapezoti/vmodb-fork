package dk.ku.di.dms.vms.marketplace.stock;

import dk.ku.di.dms.vms.modb.api.annotations.VmsTable;
import dk.ku.di.dms.vms.modb.api.interfaces.IEntity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@VmsTable(name="stock_items")
@IdClass(StockItem.StockId.class)
public class StockItem implements IEntity<StockItem.StockId> {

    public static class StockId implements Serializable {
        public int seller_id;
        public int product_id;

        public StockId(){}

        public StockId(int seller_id, int product_id) {
            this.seller_id = seller_id;
            this.product_id = product_id;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof StockId stockId) {
                return this.seller_id == stockId.seller_id && this.product_id == stockId.product_id;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(seller_id, product_id);
        }

    }

    @Id
    public int seller_id;

    @Id
    public int product_id;

    @Column
    public int qty_available;

    @Column
    public int qty_reserved;

    @Column
    public int order_count;

    @Column
    public int ytd;

    @Column
    public String data;

    @Column
    public String version;

    @Column
    public Date created_at;

    @Column
    public Date updated_at;

    public StockItem() {}

    public StockItem(int seller_id, int product_id, int qty_available, int qty_reserved, int order_count, int ytd, String data, String version) {
        this.seller_id = seller_id;
        this.product_id = product_id;
        this.qty_available = qty_available;
        this.qty_reserved = qty_reserved;
        this.order_count = order_count;
        this.ytd = ytd;
        this.data = data;
        this.version = version;
        this.created_at = new Date();
        this.updated_at = created_at;
    }

    @Override
    public String toString() {
        return "{\"seller_id\":" + seller_id
                + ", \"product_id\":" + product_id
                + ", \"qty_available\":" + qty_available
                + ", \"qty_reserved\":" + qty_reserved
                + ", \"order_count\":" + order_count
                + ", \"ytd\":" + ytd
                + ", \"data\":\"" + data + "\""
                + ", \"version\":\"" + version + "\"}";
    }

}
