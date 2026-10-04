# vMODB — Virtual Microservice‑Oriented Database System

![Java](https://img.shields.io/badge/Java-21-blue.svg) ![Maven](https://img.shields.io/badge/Maven-3.9%2B-blue.svg) ![Branch](https://img.shields.io/badge/branch-multi__vms-purple.svg) [![arXiv](https://img.shields.io/badge/arXiv-2504.19757-b31b1b.svg)](https://arxiv.org/abs/2504.19757)

vMODB is a distributed, event-driven, microservice-oriented database management system. The first principled approach for designing and implementing microservices that require advanced data management requirements. vMODB unifies event and data management, offering event-driven functionalities and ACID guarantees by design, making it easier for developers to build scalable microservices to run in the cloud.

Differently from traditional server-based database and message systems, where users interact via a well-defined network protocol, in vMODB, users solely write application code and all the data and event management complexity is abstracted away. For that, vMODB offers familiar programming abstractions to developers, including object-relational mapping and metaprogramming (i.e., annotations). 

In the end, developers experience the same flexibility and dynamicity offered by microservice architectures, while enjoying native system-level data management support that effectively prevents several challenges usually found in the practice.

## Table of Contents
- [Why vMODB](#why-vmodb)
- [Quickstart](#quickstart)
    * [Prerequisites](#prerequisites)
    * [Build](#build)
    * [Configuration](#config)
- [System](#system)
    * [Abstractions](#abstractions)
    * [Architecture](#architecture)
    * [APIs](#apis)
    * [Play Around](#play)
    * [Testing](#test)
- [Troubleshooting](#troubleshooting)

### <a name="why-vmodb"></a>Why vMODB?

Event‑driven microservice architectures (EDMAs) allow teams to build systems formed by self-contained components that can be deployed, scaled, and upgraded independently. To achieve such non-functional requirements, EDMAs typically rely on asynchronous messages to enable interaction across components. While decoupling components in time facilitate software teams to move fast and adapt the system to varied workloads, ensuring transactional guarantees across components (e.g., workflow atomicity) is often perceived as a major challenge. In most cases, EDMAs end up relying on weaker guarantees, such as eventual consistency, in order to achieve performance requirements like scalability.

vMODB departs from traditional EDMAs by providing a programming model (VMS) and a runtime that unifies event logs and state to deliver ACID across microservices. In evaluations, vMODB outperforms widely adopted eventual‑consistency frameworks by up to 3x.

### <a name="quickstart"></a>Quickstart

#### <a name="prerequisites"></a>Prerequisites

- Java Development Kit (JDK) 21 by any distributor, such as [OpenJDK](https://jdk.java.net/archive/)
- Maven to assemble the dependencies and compile the project: [Maven](https://www.hostinger.com/tutorials/how-to-install-maven-on-ubuntu)
- Curl to play with the HTTP APIs (Optional)

#### <a name="build"></a>Build

Before building the project, clone the source code repository:

```
git clone --depth 1 https://github.com/rnlaigner/vMODB
cd vMODB
```

It is necessary to generate the dependencies required to compile the microservice.
This can be accomplished via running the following command in the root folder:

```
mvn clean install -DskipTests=true
```

Then you can just run the following command:
```
mvn clean package -DskipTests=true
```

To run the Online Marketplace benchmark, for each submodule under `marketplace` , use the following VM parameters:
```
--enable-preview
--add-exports
java.base/jdk.internal.misc=ALL-UNNAMED
--add-opens
java.base/jdk.internal.misc=ALL-UNNAMED
```

To run TPC-C, for each submodule under `tpcc` , use the following VM parameters:
```
-XX:+UseParallelGC
--enable-preview
--add-exports
java.base/jdk.internal.misc=ALL-UNNAMED
--add-opens
java.base/jdk.internal.util=ALL-UNNAMED
--add-opens
java.base/java.nio=ALL-UNNAMED
--add-opens
java.base/sun.nio.ch=ALL-UNNAMED
```

To profile the system, use the following VM parameters:
```
-XX:StartFlightRecording=filename=app.jfr,settings=profile,dumponexit=true
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=/tmp/heapdump.hprof
-XX:+UseParallelGC
--enable-preview
--add-exports
java.base/jdk.internal.misc=ALL-UNNAMED
--add-opens
java.base/jdk.internal.util=ALL-UNNAMED
--add-opens
java.base/java.nio=ALL-UNNAMED
--add-opens
java.base/sun.nio.ch=ALL-UNNAMED
```

### <a name="system"></a>System

#### <a name="architecture"></a>Architecture

At the core of vMODB lies the virtual micro service (VMS) programming model. Through a VMS, users define a component’s data model, constraints, and concurrency semantics. vMODB coordinates the execution of a collection of VMS instances. Developers specify transactions that may traverse multiple VMSes; the coordinator orders, validates, and commits them, preserving ACID across components while retaining the decoupling benefits of EDA.

## <a name="troubleshooting"></a>Troubleshooting

- [Packet Size ,Window Size and Socket Buffer In TCP](https://stackoverflow.com/a/37267929/7735153)
- [Throughput and TCP windows](http://packetbomb.com/understanding-throughput-and-tcp-windows/)
- [Tuning the window size](https://docs.oracle.com/cd/E23507_01/Platform.20073/ATGInstallGuide/html/s0507tuningthetcpwindowsize01.html)

## Fork-specific changes

Everything above this section is the unmodified upstream README. The change below is the modification, made to support "deep"dependency injection (a host framework like Spring constructing `@Microservice` instances itself, instead of VMODB's own reflection), while keeping the existing `VmsApplication.build(...)` entry
point untouched.

### Two-phase construction: `VmsApplication.prepare(...)` / `VmsPreparedApplication#complete(...)`

`VmsApplication.build(options, handlerBuilder)` does five things in sequence: scans for
`@Microservice`/`@VmsTable` classes in the caller's package, loads the catalog and storage,
builds each table's repository proxy, **constructs every `@Microservice` instance via
reflection**, then wires the event handler and transaction scheduler around all of it. A host
framework like Spring cannot `@Autowired` anything into a `@Microservice` class built this way —
by the time Spring could get a reference to it, VMODB has already constructed it itself with no
Spring involvement at all.

The fix splits that sequence in two, instead of threading an externally-built-instance parameter
through the single `build(...)` method:

- **`VmsApplication.prepare(options)`** runs everything up to, but not including, constructing
  the `@Microservice` instance(s). It returns a new `VmsPreparedApplication`, which exposes the
  repository proxies already built (`getRepositoryProxy(table)`) — enough for a caller to
  construct its own, externally-managed `@Microservice` instance(s) with real constructor
  injection.
- **`VmsPreparedApplication#complete(vmsInstances, handlerBuilder)`** takes those
  externally-built instances (keyed by `Class#getName()`, the same convention
  `VmsApplication#getService(String)` already reads back out by) and finishes exactly what
  `build(...)` would have done from that point on — event schema mapping, event handler,
  transaction scheduler — returning an ordinary `VmsApplication`.

`build(...)` itself is **completely unchanged**: it still constructs `@Microservice` instances
via reflection, exactly as before, and does not call `prepare(...)`/`complete(...)` internally.
The split is implemented by extracting `build(...)`'s second half into a new, differently-named
method (`VmsMetadataLoader.loadWithPreBuiltInstances(...)`, not an overload — Java can't
distinguish `Map<String,List<Object>>` from `Map<String,Object>` by generic type alone at the
bytecode level) that both `build(...)` and `complete(...)` now call.

**Backward compatibility, verified empirically, not just by inspection:** `vmodb-marketplace`'s
full existing test suite (`CartProductPriceOrderingTest`, `SpringQueryApiTest`) passes unchanged
against this modified fork, both before this change (via `build(...)`, shallow DI) and after
(via `prepare(...)`/`complete(...)`, deep DI, with `vmodb-marketplace`'s `Main.java` files
rewritten to use it). No other VMODB module needed to change.

**A real constraint found while wiring this up:** `ConfigUtils.getCallerPackage()` — used by both
`build(...)` and `prepare(...)` to find which `@Microservice`/`@VmsTable` classes belong to "this"
VMS — walks the stack trace looking for a frame whose method name is literally `"build"` to find
the direct caller above it. Hardcoded to that one string, it never recognized `prepare(...)` as
an entry point, so `prepare(...)` always threw `IllegalStateException: Cannot identify package.`
regardless of caller. Fixed by widening the check
(`modb-common/.../ConfigUtils.java#isVmsApplicationBuild`) to accept either `"build"` or
`"prepare"`. This does not change behavior for any existing `build(...)` caller.

See `vmodb-spring-starter/README.md` for the Spring-facing API this enables
(`VmodbBootstrap.repository(VmsPreparedApplication, String)`), and
`vmodb-marketplace/README.md` for a concrete application built on it.