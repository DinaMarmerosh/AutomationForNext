package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static utilities.Constants.*;

public class PaymentPage {
    WebDriver driver;

    //constructor
    public PaymentPage(WebDriver driver) {
        this.driver = driver;
    }

    //locators
    By creditCardOptionLoc = By.cssSelector("input[value='5']");
    By cardNumberLoc = By.id("cardNumber");
    By expiryMonthLoc = By.id("expiryMonth");
    By expiryYearLoc = By.id("expiryYear");
    By securityCodeLoc=By.id("securityCode");
    By payNowLoc=By.cssSelector("input[value='Pay Now']");
    By errorInCardNumberHintLoc=By.id("cardNumber-hint");

    //methods

    /**
     * chooseWayToPay: choose the way to pay to be by a credit card
     */
    public void chooseWayToPay(){
        driver.findElement(creditCardOptionLoc).click();
    }

    /**
     * enterCardNumber: enter credit card number
     */
    public void enterCardNumber(){
        driver.findElement(cardNumberLoc).sendKeys(CREDIT_CARD_NUMBER);
    }

    /**
     * enterExpiryDate: enter expiry date
     */
    public void enterExpiryDate(){
        driver.findElement(expiryMonthLoc).sendKeys(EXPIRY_MONTH);
        driver.findElement(expiryYearLoc).sendKeys(EXPIRY_YEAR);
    }

    /**
     * enterSecurityCode: enter security code
     */
    public void enterSecurityCode(){
        driver.findElement(securityCodeLoc).sendKeys(SECURITY_CODE);
    }

    /**
     * clickPayNow: click on payNow button
     */
    public void clickPayNow(){
        driver.findElement(payNowLoc).click();
    }

    /**
     * checkIfErrorInCardNumberIsExist: check if there is an error in credit card
     * @return true if exist and false if not
     */
    public boolean checkIfErrorInCardNumberIsExist(){
        String errorHintMsg=driver.findElement(errorInCardNumberHintLoc).getText();
        return errorHintMsg.equals(CARD_NUMBER_ERROR_HINT);
    }

}
