package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SlackLoginPage  {

    public SlackLoginPage(WebDriver driver){
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = "//input[@id='email']")
    public WebElement emailField;

    @FindBy(xpath = "//input[@id='password']")
    public WebElement passwordField;

    @FindBy(id = "signin_btn")
    public WebElement signInButton;

    @FindBy(xpath = "//span[contains(text(),'api_channel')]")
    public WebElement apiChannel;





}
