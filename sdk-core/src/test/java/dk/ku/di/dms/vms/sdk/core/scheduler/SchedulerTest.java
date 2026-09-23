package dk.ku.di.dms.vms.sdk.core.scheduler;

import dk.ku.di.dms.vms.modb.common.transaction.ITransactionManager;
import dk.ku.di.dms.vms.sdk.core.example.InputEventExample1;
import dk.ku.di.dms.vms.sdk.core.example.InputEventExample2;
import dk.ku.di.dms.vms.sdk.core.example.MicroserviceExample2;
import dk.ku.di.dms.vms.sdk.core.facade.IVmsRepositoryFacade;
import dk.ku.di.dms.vms.sdk.core.facade.NetworkRepositoryFacade;
import dk.ku.di.dms.vms.sdk.core.metadata.VmsMetadataLoader;
import dk.ku.di.dms.vms.sdk.core.metadata.VmsRuntimeMetadata;
import dk.ku.di.dms.vms.sdk.core.operational.InboundEvent;
import dk.ku.di.dms.vms.sdk.core.scheduler.complex.VmsComplexTransactionScheduler;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

/**
 * Test a varied set of configurations to make sure the scheduler progresses correctly according to the expectation.
 * Scenarios:
 * - failure
 * - simple and complex tasks
 * - submission of concurrent tasks
 */
public class SchedulerTest {

    @Test
    public void testDifferentPartitionClasses() throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, InterruptedException {
        VmsInternalChannels vmsInternalChannels = VmsInternalChannels.getInstance();
        VmsRuntimeMetadata vmsRuntimeMetadata = VmsMetadataLoader.load("dk.ku.di.dms.vms.sdk.core.example");

        VmsTransactionScheduler scheduler = VmsTransactionScheduler.build("example1", vmsInternalChannels.transactionInputQueue(),
                vmsRuntimeMetadata.queueToVmsTransactionMap(), new ITransactionManager() {}, x -> { }, 4);

        for(int i = 1; i <= 4; i++){
            InputEventExample1 eventExample = new InputEventExample1(i);
            InboundEvent event = new InboundEvent(i,i-1,1, "in", InputEventExample1.class, eventExample);
            vmsInternalChannels.transactionInputQueue().add(event);
        }

        InputEventExample2 eventExample = new InputEventExample2(5, 5);
        InboundEvent event = new InboundEvent(5,4,1, "in_", InputEventExample2.class, eventExample);
        vmsInternalChannels.transactionInputQueue().add(event);

        Thread schedulerThread = new Thread(scheduler);
        schedulerThread.start();

        sleep(2000);

        var list = new ArrayList<IVmsTransactionResult>();
        vmsInternalChannels.transactionOutputQueue().drainTo(list);
        Assert.assertEquals(5, list.size());
    }

    @Test
    public void test() throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, InterruptedException {

        // what do I need to set up a vms transaction scheduler?
        VmsInternalChannels vmsInternalChannels = VmsInternalChannels.getInstance();
        @SuppressWarnings("unchecked")
        Constructor<IVmsRepositoryFacade> constructor = (Constructor<IVmsRepositoryFacade>) NetworkRepositoryFacade.class.getConstructors()[0];
        VmsRuntimeMetadata vmsRuntimeMetadata = VmsMetadataLoader.load("dk.ku.di.dms.vms.sdk.core.example");

        VmsComplexTransactionScheduler scheduler = VmsComplexTransactionScheduler.build(
                "test", vmsInternalChannels, vmsRuntimeMetadata.queueToVmsTransactionMap(), new ITransactionManager() { });

        Thread schedulerThread = new Thread(scheduler);
        schedulerThread.start();

        // event producer that creates transactions simple and complex
        InputEventExample1 eventExample = new InputEventExample1(0);

        // scheduler needs to deserialize the
        InboundEvent event = new InboundEvent(1,0,1,"in",InputEventExample1.class, eventExample);

        vmsInternalChannels.transactionInputQueue().add(event);

        // read from output queue and insert them all into scheduler again

        IVmsTransactionResult out = vmsInternalChannels.transactionOutputQueue().take();

        // 1 - check if output queue contains two events
        // 2 - insert these two events in the input queue of the scheduler

        scheduler.stop(); // will stop the thread

        // tricky to simulate we have a scheduler in other microservice.... we need a new scheduler because of the tid
        // could reset the tid to 0, but would need to synchronize to avoid exceptions
        scheduler = VmsComplexTransactionScheduler.build("vmsTest", vmsInternalChannels,
                vmsRuntimeMetadata.queueToVmsTransactionMap(), new ITransactionManager() { });

        schedulerThread = new Thread(scheduler);
        schedulerThread.start();

        Assert.assertNotNull(out.getOutboundEventResults());

        for(var res : out.getOutboundEventResults()){
            Class<?> clazz = vmsRuntimeMetadata.queueToEventMap().get( res.outputQueue() );
            InboundEvent payload_ = new InboundEvent(1,0,1, res.outputQueue(), clazz, res.output());
            vmsInternalChannels.transactionInputQueue().add(payload_);
        }

        // 3 - check state of microservice 2 and see if the method was executed
        // there will be no output event since it is a void method
        sleep(2000); // just an upper bound. everything completes much earlier

        MicroserviceExample2 ms2 = (MicroserviceExample2) vmsRuntimeMetadata.loadedVmsInstances().get("dk.ku.di.dms.vms.sdk.core.example.MicroserviceExample2");

        Assert.assertNotNull(ms2);

        Assert.assertEquals(2, ms2.getCount());

        // test abort from the application. test 2
    }

}
