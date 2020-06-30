package StepDefinitions.SlackPractice;

import Utils.ConfigReader;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;


public class DeleteAndVerifyStep5 {

    HttpClient client;
    HttpResponse response;
    URIBuilder uriBuilder;
    String ts= SlackPostStep1.ts;


    @When("the deletes message from slack via POST request")
    public void the_deletes_message_from_slack_via_POST_request() throws URISyntaxException, IOException {
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

    @Then("the user verifies the message is gone via GET request")
    public void the_user_verifies_the_message_is_gone_via_GET_request() throws URISyntaxException, IOException {

        uriBuilder.setScheme("https");
        uriBuilder.setHost("slack.com");
        uriBuilder.setPath("api/conversations.history");
        uriBuilder.setCustomQuery("channel=C0164SXRETU");

        HttpGet get = new HttpGet(uriBuilder.build());
        get.setHeader("Authorization", ConfigReader.getProperty("token"));
        get.setHeader("Accept", "application/json");

        response = client.execute(get);
        System.out.println("no idea");
        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
        ObjectMapper objectMapper= new ObjectMapper();

        Map<String, Object> parseResponse = objectMapper.readValue(response.getEntity().getContent()
                , new TypeReference<Map<String, Object>>() {
                });

        List<Map<String, Object>> messages = (List<Map<String, Object>>) parseResponse.get("messages");
        System.out.println("before");
        for (Map<String, Object> message : messages) {
            Assert.assertFalse(message.get("ts").equals(ts));

            }

             System.out.println("the message is gone");
        }
    }

