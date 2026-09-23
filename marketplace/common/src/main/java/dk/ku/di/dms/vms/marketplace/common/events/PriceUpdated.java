package dk.ku.di.dms.vms.marketplace.common.events;

import dk.ku.di.dms.vms.marketplace.common.inputs.PriceUpdate;
import dk.ku.di.dms.vms.modb.api.annotations.Event;

@Event
public final class PriceUpdated {

    public int sellerId;

    public int productId;

    public float price;

    public String version;

    public String instanceId;

    public PriceUpdated(){}

    public PriceUpdated(int sellerId,
                       int productId,
                       float price,
                       String version,
                       String instanceId) {
        this.sellerId = sellerId;
        this.productId = productId;
        this.price = price;
        this.version = version;
        this.instanceId = instanceId;
    }

    public PriceUpdate.ProductId getId(){
        return new PriceUpdate.ProductId(this.sellerId, this.productId);
    }

    public record ProductId( int sellerId, int productId){}

    @Override
    public String toString() {
        return "{"
                + "\"seller\":\"" + sellerId + "\""
                + ",\"productId\":\"" + productId + "\""
                + ",\"price\":\"" + price + "\""
                + ",\"version\":\"" + version + "\""
                + ",\"instanceId\":\"" + instanceId + "\""
                + "}";
    }
}
