package ejercicio3.conPOyPFact;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Set;

public class ShoesPage {
    public WebDriver driver;
    public String myHandleId;

    @FindBy(xpath = "//*[@id=\"top\"]/body/div/div[2]/div[2]/div/div[2]/div[2]/div[3]/ul/li[5]/div/div[2]/ul/li[2]/a") WebElement oxford;
    @FindBy(xpath = "//*[@id=\"top\"]/body/div/div[2]/div[2]/div/div[2]/div[2]/div[3]/ul/li[6]/div/div[2]/ul/li[2]/a") WebElement navy;
    @FindBy(css = "button[title='Compare']") WebElement compareButton;
    @FindBy(linkText = "Clear All") WebElement clearButton;
    @FindBy(xpath = "//*[@id=\"top\"]/body/div/div[2]/div[2]/div/div[2]/div[2]/ul/li/ul/li/span") WebElement messageSpan;

    public ShoesPage(WebDriver driver) {
        this.driver = driver;

        // Cogemos el manejador de la ventana ShoesPage
        // Usamos los manejadores para "movernos" entre ventanas
        myHandleId = driver.getWindowHandle();
    }

    // Método para seleccionar uno de los zapatos
    // script: hace scroll hasta encontrar el elemento
    public void selectShoeToCompare(int number) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;

        if(number == 5){
            jse.executeScript("arguments[0].scrollIntoView();", oxford);
            oxford.click();

        }else if (number == 6){
            jse.executeScript("arguments[0].scrollIntoView();", navy);
            navy.click();
        }
    }

    // Método que hace click en el botón de comparar los productos
    public ProductComparisonPage compareProducts() {
        compareButton.click();

        ProductComparisonPage comparisonPage =
                PageFactory.initElements(driver, ProductComparisonPage.class);

        Set<String> setIds = driver.getWindowHandles();
        String[] handleIds = setIds.toArray(new String[setIds.size()]);

        // [0] -> ShoesPage   [1] -> ProductComparisonPage
        // Asignamos los manejadores a los atributos del objeto comparisonPage
        // Nos permite volver luego otra vez a la página ShoesPage
        comparisonPage.myHandleId = handleIds[1];
        comparisonPage.myHandleIdFrom = handleIds[0];

        driver.switchTo().window(comparisonPage.myHandleId);

        return comparisonPage;
    }

    public String clearComparison() {
        clearButton.click();
        // Cambiamos el foco a la ventana de alerta
        Alert alerta = driver.switchTo().alert();
        // Obtenemos el mensaje de la ventana
        String mensaje = alerta.getText();
        alerta.accept();

        return mensaje;
    }

    // Necesitamos el método para hacer el getText()
    public String getMessageSpan() { return messageSpan.getText(); }

    public String getTile(){ return driver.getTitle(); }
}
