package StepDefinitions.SlackPractice;

import Pages.NewMessagePage;
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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

public class DeleteApiVerifyOnUIStep6 {

    WebDriver driver= Driver.getDriver();
    SlackLoginPage slackLoginPage= new SlackLoginPage(driver);
    NewMessagePage newMessagePage= new NewMessagePage(driver);
    HttpClient client = HttpClientBuilder.create().build();
    HttpResponse response;
    URIBuilder uriBuilder = new URIBuilder();
    ObjectMapper objectMapper = new ObjectMapper();
     String ts;
    String channel = "C0164SXRETU";
    String expectedMessage = ConfigReader.getProperty("message6");


    @When("the user creates a message with Post method")
    public void the_user_creates_a_message_with_Post_method() throws IOException, URISyntaxException {

        // https://slack.com/api/chat.postMessage
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
    @Then("the deletes message via POST request")
    public void the_deletes_message_via_POST_request() throws URISyntaxException, IOException {
        client = HttpClientBuilder.create().build();
        uriBuilder = new URIBuilder();
        //https://slack.com/api/chat.delete?channel=C0164SXRETU&ts=1593487707.046400
        uriBuilder.setScheme("https");
        uriBuilder.setHost("slack.com");
        uriBuilder.setPath("api/chat.delete");
        uriBuilder.setCustomQuery("channel=C0164SXRETU&ts="+ts+"");
        HttpPost post = new HttpPost(uriBuilder.build());

        post.setHeader("Authorization", ConfigReader.getProperty("token"));

        HttpResponse  response = client.execute(post);
        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());


    }

    @Then("Verify the message is gone via Selenium Webdriver in UI")
    public void verify_the_message_is_gone_via_Selenium_Webdriver_in_UI() throws InterruptedException {

        driver.get(ConfigReader.getProperty("url"));
        slackLoginPage.emailField.sendKeys(ConfigReader.getProperty("userName"));
        slackLoginPage.passwordField.sendKeys(ConfigReader.getProperty("password"));
        slackLoginPage.signInButton.click();
        slackLoginPage.apiChannel.click();

        List<WebElement> messages=newMessagePage.allMessages;
        for(WebElement message: messages){
            Assert.assertFalse(message.getText().contains(expectedMessage));
        }

    }
}
