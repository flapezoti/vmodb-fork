package dk.ku.di.dms.vms.web_common;

public interface IHttpHandler {

    IHttpHandler DEFAULT = new IHttpHandler() { };

    default void post(String uri, String body) throws RuntimeException { }

    default Object getAsJson(String uri) throws RuntimeException { return null; }

    default byte[] getAsBytes(String uri) { return null; }

    default void patch(String uri, String body) throws Exception { }

    default void put(String uri, String body) throws Exception { }

}