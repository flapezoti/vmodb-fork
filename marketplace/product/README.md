# Online Marketplace Product Microservice

## Running the project

Run the project with the command as follows:
```
java --enable-preview --add-exports java.base/jdk.internal.misc=ALL-UNNAMED --add-opens java.base/jdk.internal.util=ALL-UNNAMED -jar target/product-1.0-SNAPSHOT-jar-with-dependencies.jar
```

## Playing with the APIs

Let's start adding a <b>product</b> to the <i>Product</i> microservice
```
curl -X POST -H "Content-Type: application/json" -d '{"seller_id": "1", "product_id": "1", "name" : "productTest", "sku" : "skuTest", "category" : "categoryTest", "status" : "AVAILABLE", "description": "descriptionTest", "price" : 10, "freight_value" : 0, "version": "1"}' http://localhost:8081/product
```

Let's send a GET request to verify whether the function have successfully stored the state
```
curl -X GET http://localhost:8081/product/1/1
```

If everything worked, you should see the following output:

```
{"seller_id":"1", "product_id":"1", "name":"productTest", "sku":"skuTest", "category":"categoryTest", "description":"descriptionTest", "price":"10.0", "freight_value":"0.0", "status":"AVAILABLE", "version":"1"}
```

