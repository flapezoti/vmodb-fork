package dk.ku.di.dms.vms.marketplace.common.inputs;

import dk.ku.di.dms.vms.modb.api.annotations.Event;

@Event
public final class PriceUpdate {

    public int sellerId;

    public int productId;

    public float price;

    public String version;

    public String instanceId;

    public PriceUpdate(){}

    public PriceUpdate(int sellerId,
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

    public ProductId getId(){
        return new ProductId(this.sellerId, this.productId);
    }

    public record ProductId(int sellerId, int productId){}

}
