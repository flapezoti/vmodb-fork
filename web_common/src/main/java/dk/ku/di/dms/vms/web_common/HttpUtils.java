package dk.ku.di.dms.vms.web_common;

import java.util.HashMap;
import java.util.Map;

public final class HttpUtils {

    public record HttpRequestInternal(String httpMethod,
                                      String accept,
                                      String uri,
                                      String body) {}

    public static HttpRequestInternal parseRequest(String request){
        String[] requestLines = request.split("\r\n");
        String requestLine = requestLines[0];  // First line is the request line
        String[] requestLineParts = requestLine.split(" ");
        String method = requestLineParts[0];
        String url = requestLineParts[1];
        // String httpVersion = requestLineParts[2];
        // process header
        Map<String, String> headers = new HashMap<>();
        int i = 1;
        while (requestLines.length > i && !requestLines[i].isEmpty()) {
            String[] headerParts = requestLines[i].split(": ");
            headers.put(headerParts[0], headerParts[1]);
            i++;
        }
        int length = headers.containsKey("Content-Length") ? Integer.parseInt(headers.get("Content-Length")) : 0;
        if(method.contentEquals("GET")){
            return new HttpRequestInternal(method, headers.get("Accept"), url, "");
        }
        StringBuilder body = new StringBuilder();
        for (i += 1; i < requestLines.length; i++) {
            body.append(requestLines[i]);
            // if(body.length() == length) break;
        }
        String payload = body.toString();
        return new HttpRequestInternal(method, headers.get("Accept"), url, payload);
    }

    public static boolean isHttpClient(String request) {
        String subStr = request.substring(0, Math.max(request.indexOf(' '), 0));
        switch (subStr){
            case "GET", "PATCH", "POST", "PUT" -> {
                return true;
            }
        }
        return false;
    }

}
