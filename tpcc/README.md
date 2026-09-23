# TPC-C in vMODB

This project contains the modules to execute the warehouse, inventory, and order services of vMODB for the TPC-C benchmark.

## Running an experiment

### Preliminaries

When executing the project, a menu will show up on screen. The menu offers different functionalities to manage the parameters of a TPC-C experiment.

Make sure warehouse-tpcc, inventory-tpcc, and order-tpcc are deployed before initializing an experiment through <i>TPC-C Proxy</i>.

Make sure that warehouse, inventory, and order services are up and running. Refer to their respective README files and the README found in the project's base folder to compile and execute the services.

After, you can start the "proxy" service, which represents the coordinator. Through the proxy, you manage an experiment lifecycle of vMODB. The following menu is presented to the user:

```
=== Main Menu ===
1. Create tables in disk
2. Load services with data from tables in disk
3. Create workload
4. Submit workload
5. Reset service states
0. Exit
```

## Querying VMSes after data load and experiments

The 'curl' commands below are example requests that one can submit to the TPC-C VMSes to query their stored data. Modify the parameters to obtain different records other than the first ones.

### Warehouse VMS

#### Warehouse table
```
curl -X GET http://localhost:8001/warehouse/1
```

#### District table
```
curl -X GET http://localhost:8001/district/1/1
```

#### Customer table
```
curl -X GET http://localhost:8001/customer/1/1/1
```

### Inventory VMS

#### Item table
```
curl -X GET http://localhost:8001/customer/1
```

#### Stock table
```
curl -X GET http://localhost:8001/stock/1/1
```

### Order VMS

#### Order table
```
curl -X GET http://localhost:8001/order/1/1/1
```

### Transaction workers (aka dispatchers)
For number of warehouses == 8, it is recommended to increase the number of transaction workers to at least 2 in order to experience a higher throughput.

For number of warehouses >= 16, it is recommended to increase the number of transaction workers to 4 to get the highest throughput possible.

For number of warehouses >= 32, make sure to increase the heap size to match the size of the input tables. You can use the command below:
```
java --enable-preview --add-exports java.base/jdk.internal.misc=ALL-UNNAMED --add-opens java.base/jdk.internal.util=ALL-UNNAMED -jar tpcc/proxy-tpcc/target/proxy-tpcc-1.0-SNAPSHOT-jar-with-dependencies.jar -Xms100g
```

### Transaction worker(s) input queue(s)

Transaction inputs must be generated beforehand and must be sufficient to allow the system to achieve its maximum performance.
A good guess is to adjust to the expected performance in terms of tx/s, that is, considering a warm-up and data collection time, the number of inputs must meet the tx/s.

The table below provides the parameters used in vMODB experiments to preload transaction requests in the system:

| Warehouse Number | Number of inputs |
|------------------|------------------|
| 1                | 300000           |
| 2                | 200000           |
| 4                | 200000           |
| 8                | 200000           |
| 16               | 100000           |
| 32               | 100000           |

## Troubleshooting

Make sure to generate enough transactions for your experiment. It should account for the period selected. If less transaction inputs are available, that can lead to errors.

Run the following commands in case the proxy failed. That will allow identifying the PID in order to shut it down before proceeding with the experiments.
```
lsof -n -i :8091 | grep LISTEN
```
```
sudo netstat -nlp | grep :8091
```
