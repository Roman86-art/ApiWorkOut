package StepDefinitions.SlackPractice;

import Pages.NewMessagePage;
import Pages.SlackLoginPage;
import Utils.ConfigReader;
import Utils.Driver;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

public class UIPostAndVerifyStep3 {
    WebDriver driver = Driver.getDriver();
    SlackLoginPage slackLoginPage = new SlackLoginPage(driver);
    NewMessagePage newMessagePage = new NewMessagePage(driver);
    String expectedMessage = ConfigReader.getProperty("message");

    @When("the user sends message to slack with Selenium Webdriver in UI")
    public void the_user_message_to_slack_with_Selenium_Webdriver_in_UI() throws InterruptedException {
        driver.get(ConfigReader.getProperty("url"));
        slackLoginPage.emailField.sendKeys(ConfigReader.getProperty("userName"));
        slackLoginPage.passwordField.sendKeys(ConfigReader.getProperty("password"));
        slackLoginPage.signInButton.click();
        slackLoginPage.apiChannel.click();
        newMessagePage.messageField.sendKeys(expectedMessage);
        newMessagePage.sendButton.click();

    }
}
