package StepDefinitions.PetStore;

import Utils.PayloadUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;
import io.cucumber.datatable.DataTable;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.Map;


public class PostSteps {
    HttpClient client;
    HttpResponse response;

    @When("the user creates pets with id, name and status")
    public void the_user_creates_pets_with_id_name_and_status(Map<String, String> data) throws IOException, URISyntaxException {
        client = HttpClientBuilder.create().build();
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme("https");
        uriBuilder.setHost("petstore.swagger.io");
        uriBuilder.setPath("v2/pet");

        HttpPost post = new HttpPost(uriBuilder.build());

        post.setHeader("Content-Type", "application/json");
        post.setHeader("Accept", "application/json");

        int id = Integer.parseInt(data.get("id"));
        String name = data.get("name");
        String status = data.get("status");

        System.out.println("Building request body");
        HttpEntity entity = new StringEntity(PayloadUtil.getPetPayload(id, name, status));
        post.setEntity(entity);

        System.out.println("Started POST method execution");
        response = client.execute(post);
        System.out.println("Finished POST method execution");

    }

    @Then("the user validates status code is OK")
    public void the_user_validates_status_code_is_OK() {
        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());


    }

    @Then("the user validates pets with created  id, name, status is created")
    public void the_user_validates_pets_with_created_id_name_status_is_created(Map<String, String> data) throws IOException {

        System.out.println("Mapping response body with Java object");
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> parseResponse = objectMapper.readValue(response.getEntity().getContent(),
                new TypeReference<Map<String, Object>>() {
                });

        int id = Integer.parseInt(data.get("id"));
        String name = data.get("name").toString();
        String status = data.get("status").toString();

        int actualId = (int) parseResponse.get("id");
        String actualName = parseResponse.get("name").toString();
        String actualStatus = parseResponse.get("status").toString();

        Assert.assertEquals(name, actualName);
        Assert.assertEquals(id, actualId);
        Assert.assertEquals(status, actualStatus);


    }


}
