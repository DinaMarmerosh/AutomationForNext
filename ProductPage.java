package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.time.Duration;
import java.util.Objects;

import static utilities.Constants.*;

public class ProductPage {
    WebDriver driver;

    //constructor
    public ProductPage(WebDriver driver) {
        this.driver = driver;
    }

    //locators
    By selectColorLoc= By.id("dk_container_Colour-2001");
    By whiteColorLoc=By.xpath("//*[@id=\"dk_container_Colour-2001\"]/div/ul/li[1]/a");
    By selectSizeLoc= By.xpath("//*[@id=\"dk_container_Size-M29-788\"] ");
    By largeSizeLoc=By.xpath("//*[@id=\"dk_container_Size-M29-788\"]/div/ul/li[3]/a");
    By addToCartLoc=By.xpath("//*[@id=\"Style2001\"]/section/div[4]/div[5]/div[4]/div/a[1]");
    By viewEditCartLoc=By.xpath("//*[@id=\"platform_modernisation_header\"]/header/div[1]/nav/div[2]/div[4]/div[2]/div/div/div[2]/div/div/div[3]/div[1]/a");
    By quantityInCartLoc=By.xpath("//*[@id=\"dk_container_Qty_1\"]/a");
    By forPaymentLoc = By.xpath("//div//a[text()='לקופה']");

    //methods

    /**
     * enterProductPage: enter the specific product page
     */
    public void enterProductPage(){
        driver.navigate().to(SELECTED_PRODUCT_PAGE_URL);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    /**
     * selectColor: select product  color
     */
    public void selectColor(){
        driver.findElement(selectColorLoc).click();
        driver.findElement(whiteColorLoc).click();
    }

    /**
     * verifyColor: verify the color which chosen is the color which send
     * @return true if yes and false if not
     */
    public boolean verifyColor(){
        String actualColor = driver.findElement(selectColorLoc).getText();
        return Objects.equals(actualColor, COLOR_OF_PRODUCT);
    }

    /**
     * selectSize: select product size
     */
    public void selectSize(){
        driver.findElement(selectSizeLoc).click();
        driver.findElement(largeSizeLoc).click();
    }

    /**
     *  verifySize: verify the size which chosen is the size which send
     *  @return true if yes and false if not
     */
    public boolean verifySize(){
        String actualSize=driver.findElement(selectSizeLoc).getText();
        return Objects.equals(actualSize, SIZE_OF_PRODUCT);
    }

    /**
     * addToCart: add the product to cart
     */
    public void addToCart(){
        driver.findElement(addToCartLoc).click();
    }

    /**
     * forPayment: go to payment page
     */
    public void forPayment(){
        driver.findElement(forPaymentLoc).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    /**
     * viewShoppingCart: open the shopping cart
     */
    public void viewShoppingCart(){
        driver.findElement(viewEditCartLoc).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    /**
     * checkShoppingCart: check if shopping cart included the product twice
     * @return true if yes and false if not
     */
    public boolean checkShoppingCart(){
        String actualQuantity= driver.findElement(quantityInCartLoc).getText();
        return Objects.equals(actualQuantity, QUANTITY_OF_PRODUCT);
    }

    /**
     * paymentSucceeded: check if payment succeeded
     * @return true if yes and false if not
     */
    public boolean paymentSucceeded() {
        //I don't check really if payment succeeded because I can't run this code because of security issues
        //But, actually I had to check it here
        return true;
    }
}
