import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.type.CollectionType;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.specification.RequestSpecification;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class RestClient {
    RequestSpecification request;
    String token;
    ObjectMapper mapper = new ObjectMapper();

    public RestClient(LoginDto userData)
    {
        request = RestAssured.given()
                .baseUri("http://localhost:5000")
                .basePath("/api")
                .contentType(ContentType.JSON);
        try {
            getToken(userData);
        } catch (JsonProcessingException e) {
            token = "";
        }
    }

    private void getToken(LoginDto userData)
            throws JsonProcessingException
    {
            token = postWithObjectResult("/account/login", userData, UserDetails.class).token;
    }

    private String get(String path)
    {
        if (token != ""){
            Header header = new Header("Authorization", "Bearer " + token);
            request.with().header(header);
        }

        var resp = request
                .when()
                .get(path);
        var status = resp.getStatusCode();
        return resp.getBody().asString();
    }

    public <TResp> TResp getWithObjectResult(String path,  Class<TResp> respClass)
            throws JsonProcessingException
    {
        String json = get(path);
        return mapper.readValue(json, respClass);
    }

    public <TResp> TResp getWithCollectionResult(String path, CollectionType respClass)
            throws JsonProcessingException
    {
       String json = get(path);
       return mapper.readValue(json, respClass);
    }

    private <TReq> String post(String path, TReq requestBody)
            throws JsonProcessingException
    {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonString = jsonString = mapper.writeValueAsString(requestBody);
        return request
                .when().body(jsonString)
                .post(path )
                .getBody().asString();
    }

    public <TReq, TResp> TResp postWithObjectResult(String path, TReq requestBody, Class<TResp> respClass)
            throws JsonProcessingException
    {
        String json = post(path, requestBody);
        return mapper.readValue(json, respClass);
    }
}