package StepDefinitions.SlackPractice;

import Pages.SlackLoginPage;
import Utils.ConfigReader;
import Utils.Driver;
import Utils.PayloadUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.hc.core5.net.URIBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.Map;

public class SlackUISteps22 {

    WebDriver driver= Driver.getDriver();
    SlackLoginPage slackLoginPage= new SlackLoginPage(driver);
    HttpClient client = HttpClientBuilder.create().build();
    HttpResponse response;
    URIBuilder uriBuilder = new URIBuilder();
    ObjectMapper objectMapper = new ObjectMapper();
    String ts;
    String channel = "C0164SXRETU";
    String expectedMessage = ConfigReader.getProperty("message");


    @When("the user sends a message to slack via POST request")
    public void the_user_sends_a_message_to_slack_via_POST_request() throws IOException, URISyntaxException {

        uriBuilder.setScheme("https");
        uriBuilder.setHost("slack.com");
        uriBuilder.setPath("api/chat.postMessage");
        HttpPost post = new HttpPost(uriBuilder.build());
        post.setHeader("Content-Type", "application/json");
        post.setHeader("Authorization", ConfigReader.getProperty("token"));

        HttpEntity entity = new StringEntity(PayloadUtil.getPostPayload(channel, expectedMessage));
        post.setEntity(entity);

        response = client.execute(post);
        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());

        Map<String, Object> parseResponse = objectMapper.readValue(response.getEntity().getContent()
                , new TypeReference<Map<String, Object>>() {
                });
        ts = (String) parseResponse.get("ts");

    }

    @Then("the user verifies the message with Selenium Webdriver in UI")
    public void the_user_verifies_the_message_with_Selenium_Webdriver_in_UI() throws InterruptedException {
        driver.get(ConfigReader.getProperty("url"));
        slackLoginPage.emailField.sendKeys(ConfigReader.getProperty("userName"));
        slackLoginPage.passwordField.sendKeys(ConfigReader.getProperty("password"));
        slackLoginPage.signInButton.click();
        Thread.sleep(3000);
        slackLoginPage.apiChannel.click();
        String actualMessage=driver.findElement(By.xpath("//*[contains(text(),'"+expectedMessage+"')]")).getText();
        Assert.assertEquals(expectedMessage, actualMessage);

    }
}