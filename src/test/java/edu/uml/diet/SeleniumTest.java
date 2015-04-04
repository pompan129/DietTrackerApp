package edu.uml.diet;

import java.util.concurrent.TimeUnit;

import edu.uml.diet.logic.ServiceFactory;
import edu.uml.diet.logic.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class SeleniumTest {
    private WebDriver driver;
    private String baseUrl;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        driver = new HtmlUnitDriver();
        baseUrl = "http://localhost:8080/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        UserService userService = ServiceFactory.getUserServiceInstance();
        if(!userService.verifyUser("testuser1", "testuser1")){
            userService.createUser("testuser1", "testuser1");
        }
    }

    @Ignore
    @Test
    public void testLogin() throws Exception {
        driver.get(baseUrl + "/DietTracker/");
        driver.findElement(By.linkText("Login")).click();
        driver.findElement(By.name("email")).clear();
        driver.findElement(By.name("email")).sendKeys("testuser1");
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("testuser1");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    }

    @Ignore
    @Test
    public void testSearchForFood() throws Exception {
        driver.get(baseUrl + "/DietTracker/login");
        driver.findElement(By.name("email")).clear();
        driver.findElement(By.name("email")).sendKeys("testuser1");
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("testuser1");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
        driver.findElement(By.linkText("here")).click();
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
        assertTrue(driver.getPageSource().contains("testfood"));
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }
}
