package ejercicio2.conPO;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestLogin2 {
    private WebDriver driver;
    private HomePage homePage;
    private CustomerLoginPage customerLoginPage;
    private MyAccountPage myAccountPage;

    @BeforeEach
    public void inicializacion(){
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        homePage = new HomePage(driver);
    }

    @AfterEach
    public void cerrar(){ driver.close(); }

    @Test
    public void test_Login_Correct(){
        assertEquals("Madison Island", homePage.getPageName());
        customerLoginPage = homePage.loginPage();
        assertEquals("Customer Login", customerLoginPage.getPageName());
        myAccountPage = customerLoginPage.loginCorrect("noelmartinez@gmail.com","noelmartinez");
        assertEquals("My Account", myAccountPage.getPageName());
    }

    @Test
    public void test_Login_Incorrect(){
        assertEquals("Madison Island", homePage.getPageName());
        customerLoginPage = homePage.loginPage();
        assertEquals("Customer Login", customerLoginPage.getPageName());
        assertEquals("Invalid login or password.",
                customerLoginPage.loginIncorrect("noelmartinez@gmail.com","incorrecto"));
    }
}
