#!/bin/bash

param1=1

if [ $# -eq 0 ];
then
  echo "No arguments passed. Assuming one customer checkout only"
else
  param1="$1"
fi

echo "Adding product 1/1"

curl -X POST -H "Content-Type: application/json" -d '{"seller_id": "1", "product_id": "1", "name" : "productTest", "sku" : "skuTest", "category" : "categoryTest", "status" : "approved", "description": "descriptionTest", "price" : 10, "freight_value" : 0, "version": "1"}' localhost:8001/product

echo "Retrieving product 1/1"

curl -X GET localhost:8001/product/1/1

echo ""

echo "Adding stock item 1/1"

curl -X POST -H "Content-Type: application/json" -d '{"seller_id": "1", "product_id": "1", "qty_available" : 10, "qty_reserved" : 0, "order_count" : 0, "ytd": 0, "data" : "", "version": "0"}' localhost:8002/stock

echo "Retrieving stock item 1/1"

curl -X GET localhost:8002/stock/1/1

echo ""

for i in `seq 1 $param1`
do

  echo "Starting iteration $i..."
  echo ""

  echo "Updating price of product 1/1"

  curl -X PATCH -H "Content-Type: application/json" -d '{ "sellerId" : 1, "productId" : 1, "price" : 100, "instanceId" : "'$i'" }' localhost:8090/product/1/1

  echo ""

  echo "Replacing product 1/1"

  curl -X PUT -H "Content-Type: application/json" -d '{"seller_id" : "1", "product_id" : "1", "name" : "productTest", "sku" : "skuTest", "category" : "categoryTest", "status" : "approved", "description" : "descriptionTest", "price" : 10, "freight_value" : 0, "version" : "'$i'"}' localhost:8090/product/1/1

  echo ""

done

echo "Update product and price script done"
