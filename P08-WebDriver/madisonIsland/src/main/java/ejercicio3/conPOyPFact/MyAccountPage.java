package ejercicio3.conPOyPFact;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MyAccountPage {
    WebDriver driver;

    @FindBy(xpath = "//*[@id=\"nav\"]/ol/li[3]/a") WebElement accesories;
    @FindBy(xpath = "//*[@id=\"nav\"]/ol/li[3]/ul/li[4]/a") WebElement shoes;

    public MyAccountPage(WebDriver driver) { this.driver = driver; }

    public ShoesPage goToShoesPage() {
        // Actions para situarnos encima de "ACCESORIES" pero no clickar en él
        Actions builder = new Actions(driver);
        builder.moveToElement(accesories);
        builder.perform(); // ejecuta la acción creada en la línea anterior
        shoes.click();

        return PageFactory.initElements(driver, ShoesPage.class);
    }

    public String getTile(){ return driver.getTitle(); }
}
