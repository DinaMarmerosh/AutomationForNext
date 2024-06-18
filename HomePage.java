package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;

public class HomePage {
    WebDriver driver;
    Actions doubleClickAction;

    //constructor
    public HomePage(WebDriver driver) {
        this.driver = driver;
        doubleClickAction=new Actions(driver);
    }

    //locators
    By livingRoomLinkInLeftSideLoc=By.xpath("//div//a[text()='Living Room']");
    By gardenLinkInCenterLoc=By.xpath("//*[@id=\"multi-9-teaser-5946210-1_item_5\"]/div/a/div[2]/h3");
    By shoesLinkInBannerLoc=By.xpath("//*[@id=\"meganav-link-5\"]/div");
    By searchInputLoc = By.name("header-big-screen-search-box");
    By specificProductLoc = By.xpath("//*[@id=\"platform_modernisation_product_summary_M29788\"]/div/div[1]/div[1]/div/div/div[1]/a/img");

    //methods
    /**
     * enterLivingRoomLink: enter to livingRoom link from the left side of the home page
     */
    public void enterLivingRoomLink(){
        driver.findElement(livingRoomLinkInLeftSideLoc).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    /**
     * enterGardenLink: enter to garden link from the center of the home page
     */
    public void enterGardenLink(){
        driver.findElement(gardenLinkInCenterLoc).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    /**
     * enterShoesLink: enter to shoes link from the banner of the home page
     */
    public void enterShoesLink(){
        WebElement shoesCategoryElement = driver.findElement(shoesLinkInBannerLoc);
        doubleClickAction.doubleClick(shoesCategoryElement).build().perform();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    /**
     * searchProduct: search a specific product
     *
     * @param productToSearch: specific product to search
     */
    public void searchProduct(String productToSearch){
        driver.findElement(searchInputLoc).sendKeys(productToSearch);
        driver.findElement(searchInputLoc).sendKeys(Keys.ENTER);
    }

    /**
     * selectProduct: select the specific product which found
     */
    public void selectProduct(){
        driver.findElement(specificProductLoc).click();
    }
}
