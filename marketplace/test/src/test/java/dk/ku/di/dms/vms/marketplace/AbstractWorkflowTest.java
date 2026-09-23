package dk.ku.di.dms.vms.marketplace;

import dk.ku.di.dms.vms.marketplace.common.entities.CartItem;
import dk.ku.di.dms.vms.marketplace.common.inputs.CustomerCheckout;
import dk.ku.di.dms.vms.marketplace.customer.Customer;
import dk.ku.di.dms.vms.marketplace.product.Product;
import dk.ku.di.dms.vms.marketplace.stock.StockItem;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.function.Function;

import static dk.ku.di.dms.vms.marketplace.common.Constants.*;

public class AbstractWorkflowTest {

    protected static final System.Logger LOGGER = System.getLogger(AbstractWorkflowTest.class.getName());

    protected static final int BATCH_WINDOW_INTERVAL = 3000;

    protected static final int MAX_ITEMS = 10;

    protected static final int MAX_CUSTOMERS = 10;

    protected static final int MAX_SELLERS = 10;

    protected static HttpClient.Version HTTP_VERSION = HttpClient.Version.HTTP_1_1;

    protected static final Function<Integer, CustomerCheckout> CUSTOMER_CHECKOUT_FUNCTION = customerId -> new CustomerCheckout(
            customerId, "test", "test", "test", "test","test",
            "test", "test","test","test","test",
            "test", "test", "test", 1, String.valueOf(customerId)
    );

    protected static final BiFunction<Integer, String, HttpRequest> HTTP_REQUEST_CART_SUPPLIER = (customerId, payload) -> HttpRequest.newBuilder( URI.create( "http://localhost:"+CART_VMS_PORT+"/cart/"+customerId+"/add" ) )
            .header("Content-Type", "application/json").timeout(Duration.ofMinutes(10))
            .version(HTTP_VERSION)
            .method("PATCH", HttpRequest.BodyPublishers.ofString( payload ))
            .build();

    protected static final Function<String, HttpRequest> HTTP_REQUEST_PRODUCT_SUPPLIER = str -> HttpRequest.newBuilder( URI.create( "http://localhost:"+PRODUCT_VMS_PORT+"/product" ) )
            .header("Content-Type", "application/json").timeout(Duration.ofMinutes(10))
            .version(HTTP_VERSION)
            .POST(HttpRequest.BodyPublishers.ofString( str ))
            .build();

    protected static final Function<String, HttpRequest> HTTP_REQUEST_STOCK_SUPPLIER = str -> HttpRequest.newBuilder( URI.create( "http://localhost:"+STOCK_VMS_PORT+"/stock" ) )
            .header("Content-Type", "application/json").timeout(Duration.ofMinutes(10))
            .version(HTTP_VERSION)
            .POST(HttpRequest.BodyPublishers.ofString( str ))
            .build();

    protected static final Function<String, HttpRequest> HTTP_REQUEST_CUSTOMER_SUPPLIER = str -> HttpRequest.newBuilder( URI.create( "http://localhost:"+CUSTOMER_VMS_PORT+"/customer" ) )
            .header("Content-Type", "application/json").timeout(Duration.ofMinutes(10))
            .version(HTTP_VERSION)
            .POST(HttpRequest.BodyPublishers.ofString( str ))
            .build();

    protected static void ingestDataIntoProductVms() {
        try (HttpClient client = HttpClient.newBuilder()
                                            .version(HTTP_VERSION)
                                            .followRedirects(HttpClient.Redirect.NORMAL)
                                            .connectTimeout(Duration.ofSeconds(20))
                                            .build()) {
            List<CompletableFuture<HttpResponse<String>>> futures = new ArrayList<>();
            for (int i = 1; i <= MAX_ITEMS; i++) {
                String str = new Product(i, 1, "test", "test", "test", "test", 1.0f, 1.0f, "test", "1").toString();
                HttpRequest prodReq = HTTP_REQUEST_PRODUCT_SUPPLIER.apply(str);
                futures.add( client.sendAsync(prodReq, HttpResponse.BodyHandlers.ofString()) );
            }
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).join();
        }
    }

    protected static void insertItemsInStockVms() throws IOException, InterruptedException {
        try (HttpClient client = HttpClient.newBuilder().version(HTTP_VERSION).build()) {
            String str;
            for (int i = 1; i <= MAX_ITEMS; i++) {
                str = new StockItem(i, 1, 100, 0, 0, 0, "test", "0").toString();
                HttpRequest stockReq = HTTP_REQUEST_STOCK_SUPPLIER.apply(str);
                client.send(stockReq, HttpResponse.BodyHandlers.ofString());
            }
        }
    }

    protected static void insertCustomersInCustomerVms() throws IOException, InterruptedException {
        try (HttpClient client = HttpClient.newBuilder()
                .version(HTTP_VERSION)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(20)).build()) {
            String str;
            for (int i = 1; i <= MAX_CUSTOMERS; i++) {
                str = new Customer(i, "test", "test", "test", "test",
                        "test", "test", "test", "test", "test",
                        "test", "test", "test", "CREDIT_CARD",
                        0, 0, 0, "test").toString();
                HttpRequest stockReq = HTTP_REQUEST_CUSTOMER_SUPPLIER.apply(str);
                client.send(stockReq, HttpResponse.BodyHandlers.ofString());
            }
        }
    }

    protected static void insertCartItemInCartVms(int customerId) throws IOException, InterruptedException {
        try (HttpClient client = HttpClient.newBuilder()
                .version(HTTP_VERSION)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(20)).build()) {
            CartItem cartItem = new CartItem(1, 1, "test", 10, 10, 1, 0, "0");
            String str = cartItem.toString();
            HttpRequest addItemRe = HTTP_REQUEST_CART_SUPPLIER.apply(customerId, str);
            client.send(addItemRe, HttpResponse.BodyHandlers.ofString());
        }
    }

}
