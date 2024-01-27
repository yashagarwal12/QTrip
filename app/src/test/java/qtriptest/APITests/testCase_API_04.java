package qtriptest.APITests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.Random;
import java.util.UUID;

public class testCase_API_04 {
    @Test(groups ="API")
    public void testCase04(){
         
    System.out.println("---------------------Register--------------------------");
    String registerURL="https://content-qtripdynamic-qa-backend.azurewebsites.net/api/v1/register";
    Random random=new Random();
    int random_int=random.nextInt(1000);
    String username="testUser"+random_int+"@gmail.com";
    String body="{\"email\":\""+username+"\",\"password\":\"test123\",\"confirmpassword\":\"test123\"}";
    RequestSpecification request_1=RestAssured.given().contentType(ContentType.JSON).body(body);
    Response response_1=request_1.post(registerURL);
    System.out.println(response_1.getBody().asPrettyString());
    String status_line= response_1.getStatusLine();
    System.out.println(status_line);
   JsonPath jspath_1=response_1.jsonPath();
    System.out.println(jspath_1.getBoolean("success"));

    Assert.assertEquals(response_1.getStatusCode(),201,"Expected status code not present");
    Assert.assertTrue(jspath_1.getBoolean("success"),"Register Failed");

    System.out.println("---------------------Re-Register--------------------------");

    RequestSpecification request_2=RestAssured.given().contentType(ContentType.JSON).body(body);
    Response response_2=request_2.post(registerURL);
    System.out.println(response_2.getBody().asPrettyString());
    status_line= response_2.getStatusLine();
    System.out.println(status_line);
   JsonPath jspath_2=response_2.jsonPath();
    System.out.println(jspath_2.getBoolean("success"));

    Assert.assertEquals(response_2.getStatusCode(),400,"Expected status code not present");
    Assert.assertFalse(jspath_2.getBoolean("success"),"Register Invalid");

    }
    }

  

