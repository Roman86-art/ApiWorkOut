package StepDefinitions.Amazon;

import Pages.AmazonPage;
import Utils.Driver;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.io.IOException;
import java.net.HttpURLConnection;

import java.net.URL;
import java.util.List;

public class AmazonSteps {
    WebDriver driver = Driver.getDriver();
    AmazonPage page = new AmazonPage(driver);


    @When("the user on amazon.com")
    public void the_user_on_amazon_com() {
        driver.get("https://www.amazon.com/");

    }

    @Then("the user prints all links")
    public void the_user_prints_all_links() throws IOException {

        List<WebElement> links = page.allLinks;
        for (int i = 0; i < links.size(); i++) {
            String linkURL = links.get(i).getAttribute("href");
            try {
                if (linkURL != null) {
                    URL obj = new URL(linkURL);
                    HttpURLConnection conn = ((HttpURLConnection)
                            obj.openConnection());
                    int rCode = conn.getResponseCode();
                    if (rCode == 200) {
                        System.out.println(i + " Link is working------" +
                                linkURL);
                    } else {
                        System.out.println(i + " Link is not-Working------" + linkURL);
                    }
                } else {
                    System.out.println(links.get(i).getText());
                    System.out.println(i + " Link is not working ********" + linkURL);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    @Then("the user prints only working links")
    public void the_user_prints_only_working_links() {

        System.out.println("===============working links==================");

        List<WebElement> links = page.allLinks;

        for (int i = 0; i < links.size(); i++) {
            String linkURL = links.get(i).getAttribute("href");
            try {
                if (linkURL != null) {
                    URL obj = new URL(linkURL);
                    HttpURLConnection conn = ((HttpURLConnection) obj.openConnection());
                    int rCode = conn.getResponseCode();
                    if (rCode == 200) {
                        System.out.println(i + " Link is working------" + linkURL);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}