package ejercicio2.conPO;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage {
    private WebDriver driver;
    private WebElement acount, login;

    public HomePage(WebDriver dri){
        driver = dri;
        driver.get("http://demo-store.seleniumacademy.com");

        // Obtenemos el elemento del hiperenlace "Account" de la página principal
        acount = driver.findElement(By.xpath("//*[@id=\"header\"]/div/div[2]/div/a"));
    }

    public CustomerLoginPage loginPage(){
        acount.click();

        // Obtenemos el elemento del hiperenlace "Log in" de la página principal,
        // una vez que le hemos dado click al hiperenlace "Account"
        login= driver.findElement(By.xpath("//*[@id=\"header-account\"]/div/ul/li[6]/a"));
        login.click();

        return new CustomerLoginPage(driver);
    }

    public String getPageName(){ return driver.getTitle(); }
}
