package dk.ku.di.dms.vms.sdk.embed.client;

import dk.ku.di.dms.vms.modb.common.transaction.ITransactionManager;
import dk.ku.di.dms.vms.web_common.IHttpHandler;

import java.util.function.Function;

@FunctionalInterface
public interface HttpHandlerBuilder {

    IHttpHandler build(ITransactionManager transactionManager,
                               Function<String, Object> repositoryFunction);

}