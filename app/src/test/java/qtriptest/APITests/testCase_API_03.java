package qtriptest.APITests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.json.Json;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.Random;
import java.util.UUID;
import com.google.gson.JsonObject;

public class testCase_API_03 {
    @Test(groups ="API")
    public void testCase03(){
         
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

    System.out.println("---------------------Login--------------------------");
    String loginURL="https://content-qtripdynamic-qa-backend.azurewebsites.net/api/v1/login";
    String loginBody="{\"email\":\""+username+"\",\"password\":\"test123\"}";
    RequestSpecification loginRequest=RestAssured.given().contentType(ContentType.JSON).body(loginBody);
    Response response_2=loginRequest.post(loginURL);
    ResponseBody rb_2=response_2.getBody();
    System.out.println(rb_2.asPrettyString());
    JsonPath jspath_2=new JsonPath(rb_2.asString());
    System.out.println(jspath_2.getInt("data.size()"));
    String token=jspath_2.getString("data.token");
    String user_id=jspath_2.getString("data.id");
  
    Assert.assertEquals(response_2.getStatusCode(),201,"Expected status code not present");
    Assert.assertTrue(jspath_2.getBoolean("success"),"Login Failed");
   Assert.assertFalse(jspath_2.getString("data.token").isEmpty(),"No token present");
   Assert.assertFalse(jspath_2.getString("data.id").isEmpty(),"No id present");

   System.out.println("---------------------New Reservation--------------------------");
   String newReservation="https://content-qtripdynamic-qa-backend.azurewebsites.net/api/v1/reservations/new";
   String adventure_1="2447910730";
   String adventure_2="8304353098";
   String reservation_details="{\"userId\":\""+user_id+"\",\"name\":\"testuser\",\"date\":\"2024-09-09\",\"person\":\"1\",\"adventure\":\""+adventure_1+"\"}";
   String reservation_details_2="{\"userId\":\""+user_id+"\",\"name\":\"testuser\",\"date\":\"2024-09-09\",\"person\":\"1\",\"adventure\":\""+adventure_2+"\"}";
   RequestSpecification request_3=RestAssured.given().header("Authorization","Bearer "+token).contentType(ContentType.JSON).body(reservation_details);

   Response response_3=request_3.post(newReservation);
   System.out.println(response_3.getBody().asPrettyString());

   JsonPath jspath_3=response_3.jsonPath();
  
   Assert.assertEquals(response_3.getStatusCode(),200,"Expected status code not present");
   Assert.assertTrue(jspath_3.getBoolean("success"),"Reservation unsuccessful");

   RequestSpecification request_5=RestAssured.given().header("Authorization","Bearer "+token).contentType(ContentType.JSON).body(reservation_details_2);

   Response response_5=request_5.post(newReservation);
   System.out.println(response_5.getBody().asPrettyString());


System.out.println("---------------------Get Reservation--------------------------");
   String get_reservation_url="https://content-qtripdynamic-qa-backend.azurewebsites.net/api/v1/reservations/";
   RequestSpecification request_4=RestAssured.given().header("Authorization","Bearer "+token).queryParam("id", user_id);
   Response response_4=request_4.get(get_reservation_url);
   ResponseBody rb=response_4.getBody();
      System.out.println(rb.asPrettyString());

      JsonPath jspath_4=rb.jsonPath();
      int array_len=jspath_4.getInt("rb.size()");
      System.out.println(array_len);
      String id[]=new String[array_len];
      String adventure[]=new String[array_len];
      for(int i = 0; i < array_len; i++) {
          id[i] = jspath_4.getString("id["+i+"]");
          adventure[i] = jspath_4.getString("adventure["+i+"]");
         System.out.println(id[i]);
         System.out.println(adventure[i]);
         
      }
   Assert.assertEquals(response_4.getStatusCode(),200,"Expected status code not present");
   //Assert.assertEquals(adventure[0],adventure_1,"Expected adventure_id not present");
   //Assert.assertEquals(adventure[1],adventure_2,"Expected adventure_id not present");
   Assert.assertFalse(jspath_4.getString("id").isEmpty(),"Adventure id is present");
   response_4.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(new File("/home/crio-user/workspace/yashagarwal12081999-ME_API_TESTING_PROJECT/app/src/test/resources/tc3_schema.json")));

    }

}
