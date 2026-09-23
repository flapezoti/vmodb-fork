# Online Marketplace Seller Microservice

## Running the project

Run the project with the command as follows:
```
java --enable-preview --add-exports java.base/jdk.internal.misc=ALL-UNNAMED --add-opens java.base/jdk.internal.util=ALL-UNNAMED -jar target/seller-1.0-SNAPSHOT-jar-with-dependencies.jar
```

## Playing with the APIs

Let's start adding a <b>seller</b> to the <i>Seller</i> microservice
```
curl -X POST -H "Content-Type: application/json" -d '{"id": "1", "name" : "test", "company_name" : "test", "email" : "test", "phone" : "test", "mobile_phone": "test", "cpf" : "test", "cnpj" : "test", "address": "test", "complement" : "test", "city" : "test", "state" : "test", "zip_code": "test"}' http://localhost:8087/seller
```

Let's send a GET request to verify whether the function have successfully stored the state
```
curl -X GET http://localhost:8087/seller/1
```

If everything worked, you should see the following output:

```
{"id": "1", "name" : "test", "company_name" : "test", "email" : "test", "phone" : "test", "mobile_phone": "test", "cpf" : "test", "cnpj" : "test", "address": "test", "complement" : "test", "city" : "test", "state" : "test", "zip_code": "test"}
```

If you run a round of checkout transactions, then you probably can query the seller dashboard:

```
curl -X GET http://localhost:8087/seller/dashboard/1
```

The result will be similar to the following:

```
{"view":{"seller_id":"1","total_amount":"1033956.125","total_freight":"18259.0","total_incentive":"758.0560302734375","total_invoice":"1052215.125","total_items":"1164196.75","count_orders":"34","count_items":"34"},"entries":[{"customer_id":"74","order_id":"1","seller_id":"1","product_id":"10","package_id":"-1","product_name":"Awesome Granite Chair","product_category":"","unit_price":"8222.0","quantity":"1","total_items":"8222.0","total_amount":"8222.0","total_invoice":"9101.0","total_incentive":"0.0","freight_value":"879.0","shipment_date":null,"delivery_date":null,"order_status":"INVOICED","delivery_status":"null"}, {"customer_id":"2208","order_id":"1","seller_id":"1","product_id":"9","package_id":"-1","product_name":"Tasty Fresh Mouse","product_category":"","unit_price":"7402.0","quantity":"9","total_items":"66618.0","total_amount":"66618.0","total_invoice":"66648.0","total_incentive":"0.0","freight_value":"30.0","shipment_date":null,"delivery_date":null,"order_status":"INVOICED","delivery_status":"null"}, ... ]
```