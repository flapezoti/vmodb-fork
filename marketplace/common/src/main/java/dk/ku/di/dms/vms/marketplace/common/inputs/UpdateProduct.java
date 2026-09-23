package dk.ku.di.dms.vms.marketplace.common.inputs;

import dk.ku.di.dms.vms.modb.api.annotations.Event;

@Event
public final class UpdateProduct {

    public int seller_id;

    public int product_id;

    public String name;

    public String sku;

    public String category;

    public String description;

    public float price;

    public float freight_value;

    public String status;

    public String version;

    public UpdateProduct(){}

    public UpdateProduct(int seller_id, int product_id, String name, String sku, String category, String description, float price, float freight_value, String status, String version) {
        this.seller_id = seller_id;
        this.product_id = product_id;
        this.name = name;
        this.sku = sku;
        this.category = category;
        this.description = description;
        this.price = price;
        this.freight_value = freight_value;
        this.status = status;
        this.version = version;
    }

    public ProductId getId(){
        return new ProductId(this.seller_id, this.product_id);
    }

    public record ProductId(int sellerId, int productId){}

}
