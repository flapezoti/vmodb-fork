#!/bin/bash

# Access individual command-line arguments
echo "Script name: $0"
echo "Number of arguments: $#"
echo "The arguments passed: $*"

help_="--help"
param1="$1"

if [ "$param1" = "$help_" ]; then
    echo "It is expected that the script runs in the marketplace project's root folder."
    echo "You can specify the apps using the following pattern:"
    echo "<app-id1> ... <app-idn>"
    exit 1
fi

var1=1
current_dir=$(pwd)
echo "Current dir is" $current_dir

echo ""

if [ $# -eq 0 ];
then
  echo "ERROR: No arguments passed"
  exit 1
fi

if test -d `echo $(pwd)/proxy`; then
  echo "Initializing deploy of microservices..."
else
  echo "ERROR: Run the script in the marketplace project's root folder!"
  exit 1
fi

if `echo "$*" | grep -q cart`; then
    s=`ps | grep -c cart`
    if [ $s = $var1 ]
    then
        echo "Cart already running"
    else
        echo "Initializing Cart..."
        java --enable-preview --add-exports java.base/jdk.internal.misc=ALL-UNNAMED --add-opens java.base/jdk.internal.util=ALL-UNNAMED -jar $current_dir/cart/target/cart-1.0-SNAPSHOT-jar-with-dependencies.jar
    fi
fi

if `echo "$*" | grep -q product`; then
    p=`ps | grep -c product`
    if [ $p = $var1 ]
    then
        echo "Product already running"
    else
        echo "Initializing Product..."
        java --enable-preview --add-exports java.base/jdk.internal.misc=ALL-UNNAMED --add-opens java.base/jdk.internal.util=ALL-UNNAMED -jar $current_dir/product/target/product-1.0-SNAPSHOT-jar-with-dependencies.jar
    fi
fi

if `echo "$*" | grep -q stock`; then
    s=`ps | grep -c stock`
    if [ $s = $var1 ]
    then
        echo "Stock already running"
    else
        echo "Initializing Stock..."
        java --enable-preview --add-exports java.base/jdk.internal.misc=ALL-UNNAMED --add-opens java.base/jdk.internal.util=ALL-UNNAMED -jar $current_dir/stock/target/stock-1.0-SNAPSHOT-jar-with-dependencies.jar
    fi
fi

if `echo "$*" | grep -q order`; then
    s=`ps | grep -c order`
    if [ $s = $var1 ]
    then
        echo "Order already running"
    else
        echo "Initializing Order..."
        java --enable-preview --add-exports java.base/jdk.internal.misc=ALL-UNNAMED --add-opens java.base/jdk.internal.util=ALL-UNNAMED -jar $current_dir/order/target/order-1.0-SNAPSHOT-jar-with-dependencies.jar
    fi
fi

if `echo "$*" | grep -q payment`; then
    s=`ps | grep -c payment`
    if [ $s = $var1 ]
    then
        echo "Payment already running"
    else
        echo "Initializing Payment..."
        java --enable-preview --add-exports java.base/jdk.internal.misc=ALL-UNNAMED --add-opens java.base/jdk.internal.util=ALL-UNNAMED -jar $current_dir/payment/target/payment-1.0-SNAPSHOT-jar-with-dependencies.jar
    fi
fi

if `echo "$*" | grep -q shipment`; then
    s=`ps | grep -c shipment`
    if [ $s = $var1 ]
    then
        echo "Shipment already running"
    else
        echo "Initializing Shipment..."
        java --enable-preview --add-exports java.base/jdk.internal.misc=ALL-UNNAMED --add-opens java.base/jdk.internal.util=ALL-UNNAMED -jar $current_dir/shipment/target/shipment-1.0-SNAPSHOT-jar-with-dependencies.jar
    fi
fi

if `echo "$*" | grep -q seller`; then
    s=`ps | grep -c seller`
    if [ $s = $var1 ]
    then
        echo "Seller already running"
    else
        echo "Initializing Seller..."
        java --enable-preview --add-exports java.base/jdk.internal.misc=ALL-UNNAMED --add-opens java.base/jdk.internal.util=ALL-UNNAMED -jar $current_dir/seller/target/seller-1.0-SNAPSHOT-jar-with-dependencies.jar
    fi
fi

if `echo "$*" | grep -q customer`; then
    s=`ps | grep -c customer`
    if [ $s = $var1 ]
    then
        echo "Customer already running"
    else
        echo "Initializing Customer..."
        java --enable-preview --add-exports java.base/jdk.internal.misc=ALL-UNNAMED --add-opens java.base/jdk.internal.util=ALL-UNNAMED -jar $current_dir/customer/target/customer-1.0-SNAPSHOT-jar-with-dependencies.jar
    fi
fi

if `echo "$*" | grep -q proxy`; then
    p=`ps | grep -c proxy`
    if [ $p = $var1 ]
    then
        echo "Proxy already running"
    else
        echo "Waiting 2 sec for microservices before setting up the proxy (coordinator)..."
        sleep 2
        echo "Initializing Proxy..."
        java --enable-preview --add-exports java.base/jdk.internal.misc=ALL-UNNAMED --add-opens java.base/jdk.internal.util=ALL-UNNAMED -jar $current_dir/proxy/target/proxy-1.0-SNAPSHOT-jar-with-dependencies.jar
    fi
fi