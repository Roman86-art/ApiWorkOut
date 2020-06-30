package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class NewMessagePage {

    public NewMessagePage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[@data-team-id='TTP3PS9QD']")
    public WebElement messageField;

    @FindBy(xpath = "//i[@type='paperplane-filled']")
    public WebElement sendButton;

    @FindBy(xpath = "//div[@aria-expanded='false']//div//span[@class='c-message_kit__text']")
    public List<WebElement> allMessages;
}
