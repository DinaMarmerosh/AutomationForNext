package testCases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.io.FileUtils;
import org.junit.*;
import org.junit.FixMethodOrder;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import pages.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

import static utilities.Constants.*;

public class SanityTest {
    private static WebDriver driver;
    private static ExtentSparkReporter spark = new ExtentSparkReporter("index.html");
    private static ExtentReports report = new ExtentReports();

    MainPage mainPage = new MainPage(driver);
    LoginPage loginPage = new LoginPage(driver);
    HomePage homePage = new HomePage(driver);
    ProductPage productPage = new ProductPage(driver);
    PaymentPage paymentPage = new PaymentPage(driver);

    String currentTime = String.valueOf(System.currentTimeMillis());

    @BeforeClass
    public static void beforeClass() throws ParserConfigurationException, IOException, SAXException {
        System.out.println("****************initiate****************");

        report.attachReporter(spark);
        spark.config().setTheme(Theme.DARK);
        spark.config().setReportName("Next Project Report");

        ChromeOptions options = new ChromeOptions();
        //to work in unanimous mode
        options.addArguments("-incognito");

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();

        driver.get(getData("SITE_URL"));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }


    /**
     * Login test: The test logs in to the system using a code and password taken from the config file
     *
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    @Test
    public void loginTest() throws ParserConfigurationException, IOException, SAXException {

        ExtentTest loginTestReport = report.createTest("loginTest");
        loginTestReport.info("Running loginTest");

        //Step 1 - enter to login page
        mainPage.enterLoginPage();
        try {
            String actualUrl = driver.getCurrentUrl();
            Assert.assertTrue(actualUrl.equals(LOGIN_PAGE_HE_URL)||actualUrl.equals(LOGIN_PAGE_EN_URL));
            loginTestReport.pass("[PASS]Step1: Enter to login page succeeded");
        } catch (AssertionError e) {
            loginTestReport.fail("[ERROR]Step1: Enter to login page fail",
                    MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot("target\\pictures\\" + currentTime)).build());
        }

        //Step 2 - enter email and password which taken from the config file
        String email = getData("EMAIL");
        String password=getData("PASSWORD");
        loginPage.enterEmailAndPassword(email, password);
        try {
            Assert.assertTrue(loginPage.verifyEmailInput(email));
            Assert.assertTrue(loginPage.verifyPasswordInput(password));
            loginTestReport.pass("[PASS]Step2: Insert email: "+email+"  and password: "+password+" succeeded",
                    MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot("target\\pictures\\" + currentTime)).build());
        } catch (AssertionError e) {
            loginTestReport.fail("[FAIL]Step2 : Insert email and password finished with error",
                    MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot("target\\pictures\\" + currentTime)).build());
        }

        //Step 3 - click on sign in
        loginPage.clickOnSignIn();
        loginTestReport.pass("[PASS]Step3: Click on sign in succeeded");

        //navigate back to maim page because the site does not allow signing in for automation
        driver.navigate().to(getData("SITE_URL"));

        loginTestReport.info("Finished loginTest");
    }

    /**
     * CheckLinksAndSearchProduct Test: The test enter the home page
     * and check a few links, after that search a specific product
     */
    @Test
    public void checkLinksAndSearchProductTest(){
        ExtentTest thisTest = report.createTest("checkLinksAndSearchProductTest");
        thisTest.info("Running checkLinksAndSearchProductTest");

        //Step 1: enter the home page
        mainPage.enterHomePage();
        try {
            Assert.assertEquals(HOME_PAGE_URL, driver.getCurrentUrl());
            thisTest.pass("[PASS]Step1: Enter to home page succeeded");
        }
        catch (AssertionError e) {
            thisTest.fail("[ERROR]Step1: Enter to home page fail",
                    MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot("target\\pictures\\" + currentTime)).build());
        }

        //Step 2: enter a link from the left side and return to home page
        homePage.enterLivingRoomLink();
        try {
            Assert.assertEquals(LIVINGROOM_PAGE_URL, driver.getCurrentUrl());
            thisTest.pass("[PASS]Step2: Enter to a link from the left side succeeded");
        }
        catch (AssertionError e) {
            thisTest.fail("[ERROR]Step2: Enter to a link from the left side fail",
                    MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot("target\\pictures\\" + currentTime)).build());
        }
        mainPage.returnToHomePage();

        //Step 3: enter a link from the center and return to home page
        homePage.enterGardenLink();
        try {
            Assert.assertEquals(GARDEN_PAGE_URL, driver.getCurrentUrl());
            thisTest.pass("[PASS]Step3: Enter to a link from the center side succeeded");
        }
        catch (AssertionError e) {
            thisTest.fail("[ERROR]Step3: Enter to a link from the center side fail",
                    MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot("target\\pictures\\" + currentTime)).build());
        }
        mainPage.returnToHomePage();

        //Step 4: enter a link from the banner and return to home page
        homePage.enterShoesLink();
        try {
            Assert.assertEquals(SHOES_PAGE_URL, driver.getCurrentUrl());
            thisTest.pass("[PASS]Step4: Enter to a link from the banner succeeded");
        }
        catch (AssertionError e) {
            thisTest.fail("[ERROR]Step4: Enter to a link from the banner fail",
                    MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot("target\\pictures\\" + currentTime)).build());
        }
        mainPage.returnToHomePage();

        //Step 5: change the language of the site from English to Hebrew
        mainPage.changeLanguageToHebrew();
        try {
            Assert.assertEquals(NEXT_HEBREW_URL, driver.getCurrentUrl());
            thisTest.pass("[PASS]Step5: Change language to Hebrew succeeded");
        }
        catch (AssertionError e) {
            thisTest.fail("[ERROR]Step5: Change language to Hebrew fail",
                    MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot("target\\pictures\\" + currentTime)).build());
        }

        //Step 6: search a specific product in the search line and select it
        homePage.searchProduct(PRODUCT_TO_SEARCH);
        homePage.selectProduct();
        try {
            Assert.assertEquals(SELECTED_PRODUCT_PAGE_URL, driver.getCurrentUrl());
            thisTest.pass("[PASS]Step6: Selection of a specific product succeeded",
                    MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot("target\\pictures\\" + currentTime)).build());
        }
        catch (AssertionError e) {
            thisTest.fail("[ERROR]Step6: selection of a specific product fail",
                    MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot("target\\pictures\\" + currentTime)).build());
        }

        thisTest.info("Finished checkLinksAndSearchProductTest");
    }

    /**
     * ChooseProductDetails test: The test begins from the product page,
     * select size and color, add the product twice to the cart and go to payment
     *
     * @throws InterruptedException
     */
    @Test
    public void chooseProductDetailsTest() throws InterruptedException {
        ExtentTest thisTest = report.createTest("chooseProductDetailsTest");
        thisTest.info("Running chooseProductDetailsTest");
        productPage.enterProductPage();

        //Step 1: Select size
        productPage.selectSize();
        try {
            Assert.assertTrue(productPage.verifySize());
            thisTest.pass("[PASS]Step1: Selection size succeeded");
        }
        catch (AssertionError e){
            thisTest.fail("[FAIL]Step1: Selection size fail",
                    MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot("target\\pictures\\" + currentTime)).build());
        }

        //Step 2: Select color
        productPage.selectColor();
        try {
            Assert.assertTrue(productPage.verifyColor());
            thisTest.pass("[PASS]Step2: Selection color succeeded",
                    MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot("target\\pictures\\" + currentTime)).build());
        }catch (AssertionError e){
            thisTest.fail("[FAIL]Step2: Selection color fail",
                    MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot("target\\pictures\\" + currentTime)).build());
        }

        //Step 3: Add product to the cart
        productPage.addToCart();
        thisTest.info("Step3: Add the product to cart");

        //Step 4: Add again product to the cart
        productPage.addToCart();
        thisTest.info("Step4: Add again the product to cart");

        //Step 5: Check if the cart includes the product twice
        productPage.viewShoppingCart();
        try {
            Assert.assertTrue(productPage.checkShoppingCart());
            thisTest.pass("[PASS]Step5: Adding the product twice to cart succeeded");
        }
        catch (AssertionError e){
            thisTest.fail("[FAIL]Step5: Adding the product twice to cart fail",
                    MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot("target\\pictures\\" + currentTime)).build());
        }

        //Step 6: Click on forPayment button
        productPage.forPayment();
        Thread.sleep(2000);
        try{
            Assert.assertEquals(LOGIN_PAGE_CHECKOUT_URL,driver.getCurrentUrl());
            thisTest.pass("[PASS]Step6: Go to payment succeeded");
        }
        catch (AssertionError e){
            thisTest.fail("[FAIL]Step6: Go to payment fail",
                    MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot("target\\pictures\\" + currentTime)).build());
        }

        thisTest.info("Finished chooseProductDetailsTest");
    }

    /**
     * Payment Test: The test do the payment
     */
    @Ignore
    @Test
    public void paymentTest() {
        ExtentTest thisTest = report.createTest("paymentTest");
        thisTest.info("Running paymentTest");

        //Step 1: choose paying by a credit card
        paymentPage.chooseWayToPay();
        thisTest.info("Step1: Choose paying by a credit card");

        //Step 2: enter credit card number
        paymentPage.enterCardNumber();
        thisTest.info("Step2: Enter credit card number");

        //Step 3: enter expiry date
        paymentPage.enterExpiryDate();
        thisTest.info("Step3: Enter expiry date");

        //Step 4: enter security code
        paymentPage.enterSecurityCode();
        thisTest.info("Step4: Enter security code");

        //Step 5: click on payNow button
        paymentPage.clickPayNow();
        thisTest.info("Step5: Click on payNow button");

        //Step 6: check if error in card number is appeared
        paymentPage.checkIfErrorInCardNumberIsExist();
        thisTest.info("Step7: Check if error in card number is appeared");

        //Step 7: check if payment succeeded or fail
        try {
            Assert.assertTrue(productPage.paymentSucceeded());
            thisTest.pass("[PASS]Step7: Payment succeeded");
        }
        catch (AssertionError e){
            thisTest.fail("[FAIL]Step7: Payment fail",
                    MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot("target\\pictures\\" + currentTime)).build());
        }

        thisTest.info("Finished paymentTest");
    }

    @AfterClass
    public static void afterClass() {

        System.out.println("*************end testing***************");
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        driver.quit();
        report.flush();
    }

    private static String getData(String keyName) throws ParserConfigurationException, IOException, SAXException {
        File configXmlFile = new File("config.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;

        dBuilder = dbFactory.newDocumentBuilder();

        Document doc = null;

        assert dBuilder != null;
        doc = dBuilder.parse(configXmlFile);

        if (doc != null) {
            doc.getDocumentElement().normalize();
        }
        assert doc != null;
        return doc.getElementsByTagName(keyName).item(0).getTextContent();
    }

    public static String takeScreenShot(String ImagesPath) {
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File screenShotFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        File destinationFile = new File(ImagesPath + ".png");
        try {
            FileUtils.copyFile(screenShotFile, destinationFile);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return ImagesPath + ".png";
    }
}
