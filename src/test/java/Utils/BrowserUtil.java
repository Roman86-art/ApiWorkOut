package Utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BrowserUtil {

    public static void switchWindowByTitle(WebDriver driver, String targetTitle) {

        Set<String> ids = driver.getWindowHandles();
        for (String id : ids) {
            if (!driver.getTitle().equals(targetTitle)) {
                driver.switchTo().window(id);
            }
        }
    }

    public static void switchWindowByUrl(WebDriver driver, String url) {
        Set<String> ids = driver.getWindowHandles();
        for (String id : ids) {
            if (!driver.getCurrentUrl().contains(url)) {
                driver.switchTo().window(id);
            }
        }
    }

    public static void closeWindows(WebDriver driver, String parentId) {
        Set<String> ids = driver.getWindowHandles();

        for (String id : ids) {
            if (!id.equals(parentId)) {
                driver.switchTo().window(id);
                driver.close();
            }
        }
    }

    // create a method for fluent wait
    public static WebElement fluentWait(WebDriver driver, long totalSec, long pollingSecond, By locator) {

        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(totalSec))
                .pollingEvery(Duration.ofSeconds(pollingSecond))
                .ignoring(RuntimeException.class);
        return wait.until(driver1 -> driver1.findElement(locator));
    }

    public static WebElement visibilityOf(WebDriver driver, int timeInSec, WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, timeInSec);
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement visibilityOfElementLocated(WebDriver driver, int timeInSec, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, timeInSec);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

    }

    // ScreenShot method
    public static String takeScreenShot() {
        // first we cast driver to TakesScreen Shot
        // We get the screen as FILE
        File src = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.FILE);
        //we  need the  destination to store all my screenshot
        //System.getProperty("user.dir") will give the project location
        //System.currentTimeMillis() will give the screen shot a unique name
        // for mac single forward / +"/Screenshot/"+
        String destination = System.getProperty("user.dir") + "\\Screenshot\\" + System.currentTimeMillis() + ".png";
        File des = new File(destination);
        try {
            //copyFile will get the file from the store to the destination location
            FileUtils.copyFile(src, des);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return destination;
    }

    //LocalDate
    public static String todaysDate(String formatType) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter newFormat = DateTimeFormatter.ofPattern(formatType);
        return newFormat.format(today);
    }

    //Iframe switch
    public static void switchFrameByIndex(WebDriver driver, int index) {
        driver.switchTo().frame(index);

    }

    //Iframe switch
    public static void switchFrameByElement(WebDriver driver, By locator) {
        driver.switchTo().frame(driver.findElement(locator));

    }

    //Iframe switch
    public static void switchFrameByWebElement(WebDriver driver, WebElement element) {
        driver.switchTo().frame(element);
    }

    //
    public static List<String> getTextOfElement(List<WebElement> lists) {
        List<String> texts = new ArrayList<>();
        for (WebElement list : lists) {
            texts.add(list.getText().trim());
        }
        return texts;
    }

    // select methods
    public static void selectByVisibleText(WebElement element, String visibleText) {
        Select select = new Select(element);
        select.selectByVisibleText(visibleText);
    }
    public static void selectByIndex(WebElement element, int index) {
        Select select = new Select(element);
        select.selectByIndex(index);
    }
    public static Select getSelect(WebElement element){
        return new Select(element);
    }


}
