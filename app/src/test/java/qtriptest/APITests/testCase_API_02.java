package qtriptest.APITests;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBody;
import org.apache.poi.sl.usermodel.Line;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.RestAssured;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
public class testCase_API_02 {

    @Test(groups ="API")
    public void testCase02(){
        String url="https://content-qtripdynamic-qa-backend.azurewebsites.net/api/v1/cities";
        RequestSpecification httpRequest=RestAssured.given();
        Response response=httpRequest.get(url);
        System.out.println(response.getBody().asPrettyString());
    
       JsonPath jspath=response.jsonPath();
       int size = jspath.getInt("data.size()"); 

      
        Assert.assertEquals(response.getStatusCode(), 200,"Invalid response code");
        Assert.assertEquals(size, 8,"Invalid data size");
        Assert.assertTrue(response.getBody().asString().contains("description"),"Description node is missing from  response body");
        Assert.assertTrue(jspath.getString("description").contains("100+ Places"), "100+ Places is not present in description");
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(new File("/home/crio-user/workspace/yashagarwal12081999-ME_API_TESTING_PROJECT/app/src/test/resources/tc2_schema.json")));

        


    }





}
