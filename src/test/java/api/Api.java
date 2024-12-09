package api;

import Helpers.TestHelpers;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import todos_autotests.Props;
import Data.Stash;
import org.aeonbits.owner.ConfigFactory;

import static io.restassured.RestAssured.given;

public class Api {
    private static Props props = ConfigFactory.create(Props.class);


    public static Response sendRequest(
            String method,
            String endpoint,
            String body) {

        // get environment according to profiles in pom.xml
        String env = System.getProperty("environment");
        if (env == null || env.isEmpty()) {
            throw new IllegalArgumentException("environment is not specified. Please provide it via Maven profiles.");
        }
        Stash.currentEnv = env;

        // get baseUri for env according to profiles in pom.xml
        String baseUri = System.getProperty("base.url");
        if (baseUri == null || baseUri.isEmpty()) {
            throw new IllegalArgumentException("Base URL is not specified. Please provide it via Maven profiles.");
        }
        String auth = TestHelpers.getCredentials();


        // set baseUri
        RestAssured.baseURI = baseUri;
        RequestSpecification request = RestAssured.given().log().all();

        // base configuration
        request.relaxedHTTPSValidation()
                .contentType(ContentType.JSON)
                .header("Authorization", auth);

        // if have response body
        if (body != null && !body.isEmpty()) {
            request.body(body);
        }

        // choose method
        Response response;
        switch (method.toUpperCase()) {
            case "GET":
                response = request.get(endpoint);
                break;
            case "POST":
                response = request.post(endpoint);
                break;
            case "PUT":
                response = request.put(endpoint);
                break;
            case "DELETE":
                response = request.delete(endpoint);
                break;
            default:
                throw new IllegalArgumentException("Unsupported HTTP method: " + method);
        }

        return response;
    }

}
