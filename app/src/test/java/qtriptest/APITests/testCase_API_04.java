package qtriptest.APITests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.UUID;

public class testCase_API_04 {

    @Test(description = "Verify that a duplicate user account cannot be created on Qtrip", groups = {"API Tests"})
    public static void TestCase04() throws InterruptedException {
    }

    }

