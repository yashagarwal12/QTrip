package qtriptest.APITests;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBody;
import org.json.JSONObject;
import org.openqa.selenium.json.Json;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.netty.handler.codec.http.HttpRequest;
import io.restassured.RestAssured;
import java.util.Random;
import java.util.*;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.collect.HashMultimap;



public class testCase_API_01 {

    @Test(groups ="API")
    public void testCase01(){

    System.out.println("---------------------Register--------------------------");
    String registerURL="https://content-qtripdynamic-qa-backend.azurewebsites.net/api/v1/register";
    Random random=new Random();
    int random_int=random.nextInt(1000);
    String username="testUser"+random_int+"@gmail.com";
    String body="{\"email\":\""+username+"\",\"password\":\"test123\",\"confirmpassword\":\"test123\"}";
    RequestSpecification requestSpecification=RestAssured.given();
    requestSpecification.contentType(ContentType.JSON);
    requestSpecification.body(body);
    Response response_1=requestSpecification.post(registerURL);
    System.out.println(response_1.getBody().asPrettyString());
   String status_line= response_1.getStatusLine();
   System.out.println(status_line);
   JsonPath jspath=response_1.jsonPath();
  System.out.println(jspath.getBoolean("success"));

    Assert.assertEquals(response_1.getStatusCode(),201,"Expected status code not present");
    Assert.assertTrue(jspath.getBoolean("success"),"Register Failed");

    System.out.println("---------------------Login--------------------------");
    String loginURL="https://content-qtripdynamic-qa-backend.azurewebsites.net/api/v1/login";
    String loginBody="{\"email\":\""+username+"\",\"password\":\"test123\"}";
    RequestSpecification loginRequest=RestAssured.given();
    loginRequest.contentType(ContentType.JSON);
    loginRequest.body(loginBody);
    Response response_2=loginRequest.post(loginURL);

    System.out.println(response_2.getBody().asPrettyString());

    JsonPath jspath2=response_2.jsonPath();
    String token=jspath2.getString("data.token");
    System.out.println(token);
    String user_id=jspath2.getString("data.id");
    System.out.println(user_id);


    Assert.assertEquals(response_2.getStatusCode(),201,"Expected status code not present");
    Assert.assertTrue(jspath2.getBoolean("success"),"Login Failed");
   Assert.assertFalse(jspath2.getString("data.token").isEmpty(),"No token present");
   Assert.assertFalse(jspath2.getString("data.id").isEmpty(),"No id present");


    





    


}
}
