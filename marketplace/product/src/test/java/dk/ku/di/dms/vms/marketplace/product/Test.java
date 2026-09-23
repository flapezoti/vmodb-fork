package dk.ku.di.dms.vms.marketplace.product;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.function.Function;

public class Test {

    private static final Function<String, HttpRequest> supplier = str -> HttpRequest.newBuilder( URI.create( "http://localhost:8001/product" ) )
            .header("Content-Type", "application/json").timeout(Duration.ofMinutes(10))
            .version(HttpClient.Version.HTTP_2)
            .POST(HttpRequest.BodyPublishers.ofString( str ))
            .build();

    @org.junit.Test
    public void testIngestion() throws Exception {
        dk.ku.di.dms.vms.marketplace.product.Main.main(null);

        String str = new Product( 1, 10, "test", "test", "test", "test", 1.0f, 1.0f,  "test", "test" ).toString();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = supplier.apply(str);
        HttpResponse<String> resp = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(resp);
    }

}
