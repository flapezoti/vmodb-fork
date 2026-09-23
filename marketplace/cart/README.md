# Online Marketplace Cart Microservice

## Running the project

Run the project with the command below:
```
java --enable-preview --add-exports java.base/jdk.internal.misc=ALL-UNNAMED --add-opens java.base/jdk.internal.util=ALL-UNNAMED -jar target/cart-1.0-SNAPSHOT-jar-with-dependencies.jar
```

## Playing with the APIs

Let's start adding a <b>cart item</b> to the <i>Cart</i> microservice:
```
curl -X PATCH -H "Content-Type: application/json" -d '{"SellerId": "1", "ProductId": "1", "ProductName" : "test", "UnitPrice" : "10", "FreightValue" : "0", "Quantity": "3", "Voucher" : "0", "Version": "0"}' http://localhost:8000/cart/1/add
```

Let's send a GET request to verify whether the function have successfully processed the above operation through the following command:
```
curl -X GET http://localhost:8080/cart/1
```

If everything worked, you should see the following output:
```
[{"customer_id":"1", "seller_id":"1", "product_id":"1", "product_name":"test", "unit_price":"10.0", "freight_value":"0.0", "quantity":"3", "voucher":"0.0", "version":"0"}]
```

## Resetting State
```
curl -X PATCH http://localhost:8080/cart/reset
```

