package dk.ku.di.dms.vms.tpcc.proxy;

import dk.ku.di.dms.vms.modb.api.annotations.Microservice;
import dk.ku.di.dms.vms.modb.common.transaction.ITransactionManager;
import dk.ku.di.dms.vms.sdk.embed.client.DefaultHttpHandler;
import dk.ku.di.dms.vms.sdk.embed.client.VmsApplication;
import dk.ku.di.dms.vms.sdk.embed.client.VmsApplicationOptions;

@Microservice("warehouse-stub")
public final class TestService {

    public VmsApplication buildAndStart() {
        VmsApplicationOptions options = VmsApplicationOptions.build(
                "0.0.0.0",
                8001, new String[]{
                        "dk.ku.di.dms.vms.tpcc.proxy",
                });
        VmsApplication vms;
        try {
            vms = VmsApplication.build(options, (x, ignored) -> new TestHandler(x));
            vms.start();
            return vms;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static class TestHandler extends DefaultHttpHandler {

        public TestHandler(ITransactionManager transactionManager) {
            super(transactionManager);
        }

        @Override
        public String getAsJson(String uri) {
            System.out.println("GET TEST");
            return "GET TEST";
        }

        @Override
        public void post(String uri, String body){
            System.out.println("POST TEST");
        }
    }

}
