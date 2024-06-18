package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;

public class MainPage {
    WebDriver driver;
    Actions doubleClickAction;

    //constructor
    public MainPage(WebDriver driver){
        this.driver=driver;
        doubleClickAction=new Actions(driver);
    }

    //locators
    By changeCountryLoc=By.cssSelector("img.header-q6ti7y");
    By toEnglishLoc=By.cssSelector("button.header-kpc5eh");
    By toHebrewLoc=By.cssSelector("button.header-1k7b8zz");
    By toShoppingBtnLoc=By.xpath("//*[@id=\"header-country-selector-wrapper\"]/div/div[3]/div/div[5]/button");
    By myAccountLoc= By.cssSelector("span.header-riea5x");
    By homeCategoryLoc = By.xpath("//*[@id=\"meganav-link-6\"]/div");

    //methods

    /**
     * changeLanguageToHebrew: change language from English to Hebrew
     */
    public void changeLanguageToHebrew(){
        driver.findElement(changeCountryLoc).click();
        driver.findElement(toHebrewLoc).click();
        driver.findElement(toShoppingBtnLoc).click();

    }

    /**
     * enterLoginPage: enter to login page
     */
    public void enterLoginPage(){
        driver.findElement(myAccountLoc).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    /**
     * enterHomePage: enter to home page
     */
    public void enterHomePage(){
        WebElement homeCategoryElement = driver.findElement(homeCategoryLoc);
        doubleClickAction.doubleClick(homeCategoryElement).build().perform();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    /**
     * returnToHomePage: return to home page
     */
    public void returnToHomePage(){
        enterHomePage();
    }
}
