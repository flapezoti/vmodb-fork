# TPC-C Inventory

This project is responsible to execute the inventory service of vMODB for the TPC-C benchmark.

## Running the project

If you are in this project's root folder, run the project with the following command:
```
java --enable-preview --add-exports java.base/jdk.internal.misc=ALL-UNNAMED --add-opens java.base/jdk.internal.util=ALL-UNNAMED -jar target/inventory-tpcc-1.0-SNAPSHOT-jar-with-dependencies.jar
```

If you are outside the vms-runtime-java folder, run:
```
java --enable-preview --add-exports java.base/jdk.internal.misc=ALL-UNNAMED --add-opens java.base/jdk.internal.util=ALL-UNNAMED -jar vms-runtime-java/tpcc/inventory-tpcc/target/inventory-tpcc-1.0-SNAPSHOT-jar-with-dependencies.jar
```

If you are inside the vms-runtime-java folder, run:
```
java --enable-preview --add-exports java.base/jdk.internal.misc=ALL-UNNAMED --add-opens java.base/jdk.internal.util=ALL-UNNAMED -jar tpcc/inventory-tpcc/target/inventory-tpcc-1.0-SNAPSHOT-jar-with-dependencies.jar
```
