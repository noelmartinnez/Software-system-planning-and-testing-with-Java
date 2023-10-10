package ejercicio3.conPOyPFact;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestShoes {
    public WebDriver driver;
    public MyAccountPage myAccountPage;

    @BeforeEach
    public void inicializacion() {
        // Simulamos que guardamos las cookies en un fichero
        Cookies.storeCookiesToFile("noelmartinez@gmail.com", "noelmartinez");

        // Con las siguientes lineas (+ lo añadido en el pom) podemos activar el modo headless
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(Boolean.parseBoolean(System.getProperty("chromeHeadless")));
        driver = new ChromeDriver(chromeOptions);

        // Para que se nos ponga en pantalla completa y que no tengamos que controlar los scrolls en el test
        driver.manage().window().maximize();

        // Simulamos que recuperamos las cookies y las cargamos en el driver
        Cookies.loadCookiesFromFile(driver);

        driver.get("http://demo-store.seleniumacademy.com/customer/account/");

        // Creamos el objeto de la clase "MyAccountPage" usando el método "initElements(...)"
        myAccountPage = PageFactory.initElements(driver, MyAccountPage.class);
    }

    @AfterEach
    public void cerrar() { driver.close(); }

    @Test
    public void compareShoes() {
        //1 - Verificamos que el título de la página es el correcto
        assertEquals("My Account", myAccountPage.getTile());

        //2 - Seleccionamos Accessories → Shoes
        ShoesPage shoesPage = myAccountPage.goToShoesPage();

        //3 - Verificamos que el titulo de la página es el correcto
        assertEquals("Shoes - Accessories", shoesPage.getTile());

        //4 - Seleccionamos dos zapatos para compararlos (pulsando sobre "Add to Compare").
        shoesPage.selectShoeToCompare(5);
        shoesPage.selectShoeToCompare(6);

        //5 - Seleccionamos el botón "COMPARE"
        // Nos movemos a la página de comparación
        ProductComparisonPage comparisonPage = shoesPage.compareProducts();

        //6 - Verificamos que estamos en la página correcta usando el título de la misma
        assertEquals("Products Comparison List - Magento Commerce",comparisonPage.getTile());

        //7 - Cerramos la ventana con la comparativa de productos
        shoesPage = comparisonPage.close();

        //8 - Verificamos que estamos de nuevo en la ventana
        assertEquals("Shoes - Accessories", shoesPage.getTile());

        //9 - Borramos la comparativa (hiperenlace "Clear All"), y verificamos la alerta
        assertEquals("Are you sure you would like to remove all products from your comparison?",
                shoesPage.clearComparison());

        //10 - Verificamos que en la página aparece el mensaje indicado.
        assertEquals("The comparison list was cleared.", shoesPage.getMessageSpan());
    }
}
