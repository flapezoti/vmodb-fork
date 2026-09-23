#!/bin/bash

echo "Adding product 1/1"

curl -X POST -H "Content-Type: application/json" -d '{"seller_id": "1", "product_id": "1", "name" : "productTest", "sku" : "skuTest", "category" : "categoryTest", "status" : "approved", "description": "descriptionTest", "price" : 10, "freight_value" : 0, "version": "1"}' localhost:8001/product

echo "Adding stock item 1/1"

curl -X POST -H "Content-Type: application/json" -d '{"seller_id": "1", "product_id": "1", "qty_available" : 10, "qty_reserved" : 0, "order_count" : 0, "ytd": 0, "data" : "", "version": "0"}' localhost:8002/stock

echo "Retrieving product 1/1"

curl -X GET localhost:8001/product/1/1

echo ""

echo "Retrieving stock item 1/1"

curl -X GET localhost:8002/stock/1/1

echo ""

echo "Sleeping for 2 seconds to wait for proxy, product, and stock convergence..."

sleep 2

echo "Updating price of product 1/1"

curl -X PATCH -H "Content-Type: application/json" -d '{ "sellerId" : 1, "productId" : 1, "price" : 100, "instanceId" : "1" }' localhost:8090/product/1/1

echo "Replacing product 1/1"

curl -X PUT -H "Content-Type: application/json" -d '{"seller_id": "1", "product_id": "1", "name" : "productTest", "sku" : "skuTest", "category" : "categoryTest", "status" : "approved", "description": "descriptionTest", "price" : 10, "freight_value" : 0, "version": "2"}' localhost:8090/product/1/1

echo "Finished!"
