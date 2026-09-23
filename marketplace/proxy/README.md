# OnlineMarketplace Proxy (aka Coordinator Service)

## Running all microservices (including the Proxy)

Run the following command in the marketplace root folder:
```
./scripts/deploy/deploy_macos.sh cart product stock order payment shipment seller proxy
```

## Running only the proxy project

Run the project with the following command:
```
java --enable-preview --add-exports java.base/jdk.internal.misc=ALL-UNNAMED --add-opens java.base/jdk.internal.util=ALL-UNNAMED -jar target/proxy-1.0-SNAPSHOT-jar-with-dependencies.jar
```

## Prerequisites

Make sure all the microservices involved in the defined transactional DAGs are up and running before spawning the <i>Proxy</i>.

In particular, the <i>Product Management</i> guide requires that at least the <i>Product</i> microservice is deployed and the associated DAGs that involve <i>Product</i> do not involve any other microservice that is not running at the moment.

## Playing with the APIs

### <a name="product"></a>Product Management

#### Adding a product 
Let's start adding a <b>product</b> to the <i>Product</i> microservice
```
curl -X POST -H "Content-Type: application/json" -d '{"seller_id": "1", "product_id": "1", "name" : "productTest", "sku" : "skuTest", "category" : "categoryTest", "status" : "approved", "description": "descriptionTest", "price" : 10, "freight_value" : 0, "version": "0"}' http://localhost:8081/product
```

Let's send a GET request to verify whether the function have successfully stored the state
```
curl -X GET http://localhost:8081/product/1/1
```

If everything worked, you should see the following output:

```
{"seller_id":"1", "product_id":"1", "name":"productTest", "sku":"skuTest", "category":"categoryTest", "description":"descriptionTest", "price":"10.0", "freight_value":"0.0", "status":"approved", "version":"0"}
```

#### Modifying a product

There are two ways we can update a <b>product</b>: updating its price or overwriting it.

To submit a <b>price update</b>, a user must send the following request:
```
curl -X PATCH -H "Content-Type: application/json" -d '{ "sellerId" : 1, "productId" : 1, "price" : 100, "instanceId" : "1", "version": "0"}' http://localhost:8091/product/1/1
```

After an epoch completion, by querying the product again, we will be able to see the updated price, like below:

```
{product_id=1, seller_id=1, name='productTest', sku='sku', category='categoryTest', description='descriptionTest', price=100.0, freight_value=0.0, status='approved', version='0', createdAt=2023-10-25T14:45:22.388, updatedAt=2023-10-25T14:45:40.637}
```

To substitute the product and trigger referential integrity enforcement in stock, we use another HTTP method and content:

```
curl -X PUT -H "Content-Type: application/json" -d '{"seller_id": "1", "product_id": "1", "name" : "productTest", "sku" : "skuTest", "category" : "categoryTest", "status" : "approved", "description": "descriptionTest", "price" : 10, "freight_value" : 0, "version": "1"}' http://localhost:8091/product/1/1
```

