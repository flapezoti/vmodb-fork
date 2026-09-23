package dk.ku.di.dms.vms.sdk.embed.services;

import dk.ku.di.dms.vms.modb.api.annotations.Inbound;
import dk.ku.di.dms.vms.modb.api.annotations.Microservice;
import dk.ku.di.dms.vms.modb.api.annotations.Outbound;
import dk.ku.di.dms.vms.modb.api.annotations.Transactional;
import dk.ku.di.dms.vms.sdk.embed.events.OutputEventExample1;
import dk.ku.di.dms.vms.sdk.embed.events.OutputEventExample2;
import dk.ku.di.dms.vms.sdk.embed.events.OutputEventExample3;

import static dk.ku.di.dms.vms.modb.api.enums.TransactionTypeEnum.W;

@Microservice("example2")
public class MicroserviceExample2 {

    private int count;

    public MicroserviceExample2() {
        this.count = 0;
    }

    @Inbound(values = {"out1","out2"})
    @Outbound(value = "out3")
    @Transactional(type=W)
    public OutputEventExample3 methodExample2(OutputEventExample1 out1, OutputEventExample2 out2) {
        this.count = out1.id + out2.id;
        System.out.println("I am microservice 2: out1 "+out1.id+" out2 "+out2.id);
        return new OutputEventExample3(this.count);
    }

}