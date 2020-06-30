package StepDefinitions.SlackPractice;

import Utils.ConfigReader;
import Utils.PayloadUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.bs.A;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.hc.core5.net.URIBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

public class SlackPostStep1 {

    HttpClient client = HttpClientBuilder.create().build();
    HttpResponse response;
    URIBuilder uriBuilder = new URIBuilder();
    ObjectMapper objectMapper = new ObjectMapper();
    public static String ts;
    String channel = "C0164SXRETU";
    String expectedMessage = ConfigReader.getProperty("message");


    @When("the user creates a message on Slack with Post method")
    public void the_user_creates_a_message_on_Slack_with_Post_method() throws URISyntaxException, IOException {

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

    @Then("the user verifies the message with get method")
    public void the_user_verifies_the_message_with_get_method() throws URISyntaxException, IOException {
        // https://slack.com/api/conversations.history?channel=C0164SXRETU
        uriBuilder.setScheme("https");
        uriBuilder.setHost("slack.com");
        uriBuilder.setPath("api/conversations.history");
        uriBuilder.setCustomQuery("channel=C0164SXRETU");

        HttpGet get = new HttpGet(uriBuilder.build());
        get.setHeader("Authorization", ConfigReader.getProperty("token"));
        get.setHeader("Accept", "application/json");

        response = client.execute(get);

        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());

        Map<String, Object> parseResponse = objectMapper.readValue(response.getEntity().getContent()
                , new TypeReference<Map<String, Object>>() {
                });

        List<Map<String, Object>> messages = (List<Map<String, Object>>) parseResponse.get("messages");
        String actualMessage = "";

        for (Map<String, Object> message : messages) {
            String actualTS = (String) message.get("ts");
            if (actualTS.equals(ts)) {
                actualMessage = (String) message.get("text");
                break;
            }
        }

        Assert.assertEquals(expectedMessage, actualMessage);
    }

}
