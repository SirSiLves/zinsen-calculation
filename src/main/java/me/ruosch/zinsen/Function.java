package me.ruosch.zinsen;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import me.ruosch.zinsen.domain.Laufzeit;
import me.ruosch.zinsen.domain.Produkt;
import me.ruosch.zinsen.domain.Zins;

import java.util.Optional;

/**
 * Azure Functions with HTTP Trigger.
 * mvn clean package
 * mvn azure-functions:run
 * http://localhost:7071/api/Calculation?period="ONE"&product="FEST"
 */
public class Function {

    @FunctionName("Calculation")
    public HttpResponseMessage run(
            @HttpTrigger(
                    name = "req",
                    methods = {HttpMethod.GET},
                    authLevel = AuthorizationLevel.ANONYMOUS)
                    HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {
        context.getLogger().info("Java HTTP trigger processed a request.");

        // Parse query parameter
        final String param1 = request.getQueryParameters().get("period");
        final String param2 = request.getQueryParameters().get("product");
        final String periodJson = request.getBody().orElse(param1);
        final String productJson = request.getBody().orElse(param2);

        if (param1 == null || param2 == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Wrong Request-Format").build();
        } else {

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                Produkt product = objectMapper.readValue(productJson, Produkt.class);
                Laufzeit laufzeit = objectMapper.readValue(periodJson, Laufzeit.class);

                Zins zins = new Zins(product, laufzeit);
                zins.calculate();

                return request.createResponseBuilder(HttpStatus.OK).body(zins).build();

            } catch (Exception e) {
                return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Wrong Request-Format").build();
            }
        }
    }
}


//    /**
//     * EXAMPLE from Azure
//     * This function listens at endpoint "/api/HttpExample". Two ways to invoke it using "curl" command in bash:
//     * 1. curl -d "HTTP Body" {your host}/api/HttpExample
//     * 2. curl "{your host}/api/HttpExample?name=HTTP%20Query"
//     */
//    @FunctionName("HttpExample")
//    public HttpResponseMessage run(
//            @HttpTrigger(
//                name = "req",
//                methods = {HttpMethod.GET, HttpMethod.POST},
//                authLevel = AuthorizationLevel.ANONYMOUS)
//                HttpRequestMessage<Optional<String>> request,
//            final ExecutionContext context) {
//        context.getLogger().info("Java HTTP trigger processed a request.");
//
//        // Parse query parameter
//        final String query = request.getQueryParameters().get("name");
//        final String name = request.getBody().orElse(query);
//
//        if (name == null) {
//            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Please pass a name on the query string or in the request body").build();
//        } else {
//            return request.createResponseBuilder(HttpStatus.OK).body("Hello TEST 99999, " + name).build();
//        }
//    }
