package ejercicio2.conPO;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CustomerLoginPage {
    private WebDriver driver;
    private WebElement email, password, button, error;

    public CustomerLoginPage(WebDriver dri) {
        this.driver = dri;

        email = driver.findElement(By.xpath("//*[@id=\"email\"]"));
        password = driver.findElement(By.xpath("//*[@id=\"pass\"]"));
        button = driver.findElement(By.cssSelector("button[title='Login']"));
    }

    public MyAccountPage loginCorrect(String email, String password){
        this.email.sendKeys(email);
        this.password.sendKeys(password);
        button.submit();

        return new MyAccountPage(driver);
    }

    public String loginIncorrect(String email, String password){
        this.email.sendKeys(email);
        this.password.sendKeys(password);
        button.submit();

        // Obtenemos el error con mensaje "Invalid login or password."
        error = driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div[2]/div[2]/div/div/div[2]/ul/li/ul/li/span"));
        // Esto hay que hacerlo por separado
        return error.getText();
    }

    public String getPageName(){ return driver.getTitle(); }
}
