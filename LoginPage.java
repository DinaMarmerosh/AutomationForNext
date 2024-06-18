package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Objects;

public class LoginPage
{
    WebDriver driver;

    //constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    //locators
    By emailInputLoc = By.id("EmailOrAccountNumber");
    By passwordInputLoc = By.id("Password");
    By signInBtnLoc = By.id("SignInNow");

    //methods

    /**
     * enterEmailAndPassword: enter email and password
     * @param email: email
     * @param password: password
     */
    public void enterEmailAndPassword(String email, String password){
        WebElement emailInput = driver.findElement(emailInputLoc);
        emailInput.clear();
        emailInput.sendKeys(email);

        WebElement passwordInput = driver.findElement(passwordInputLoc);
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    /**
     * clickOnSignIn: clock on sign in button
     */
    public void clickOnSignIn(){
        driver.findElement(signInBtnLoc).click();
    }

    /**
     * verifyEmailInput: verify that the email sent does go into the input
     * @param email: email
     * @return true if succeeded and false if not
     */
    public boolean verifyEmailInput(String email){
        String actualEmail=driver.findElement(emailInputLoc).getAttribute("value");
        return Objects.equals(actualEmail, email);
    }

    /**
     * verifyPasswordInput: verify that the password sent does go into the input
     * @param password: email
     * @return true if succeeded and false if not
     */
    public boolean verifyPasswordInput(String password){
        String actualPassword=driver.findElement(passwordInputLoc).getAttribute("value");
        return Objects.equals(actualPassword, password);
    }
}
