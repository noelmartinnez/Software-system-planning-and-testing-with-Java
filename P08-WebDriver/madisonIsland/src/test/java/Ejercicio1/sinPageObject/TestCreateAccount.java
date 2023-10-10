package Ejercicio1.sinPageObject;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCreateAccount {
    private WebDriver driver;

    @BeforeEach
    public void inicializacion() {
        driver = new ChromeDriver();
        // Esto viene indicado en el propio enunciado de la práctica.
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("http://demo-store.seleniumacademy.com");
    }

    @AfterEach
    public void cerrar() {
        // Cerramos el navegador después de cada test y antes del siguiente.
        driver.close();
    }

    @Tag("OnlyOnce")
    @Test
    public void createAccount() {
        //1 - Verificamos que el título de la página de inicio es el correcto.
        assertEquals("Madison Island", driver.getTitle());

        //2 - Seleccionamos Account, y a continuación seleccionamos el hiperenlace Login
        // Para copiar el xpath, inspeccionar el elemento que queramos y "Copy->Copy XPath"
        driver.findElement(By.xpath("//*[@id=\"header\"]/div/div[2]/div/a")).click();
        driver.findElement(By.xpath("//*[@id=\"header-account\"]/div/ul/li[6]/a")).click();

        //3 - Verificamos que el título de la página es el correcto
        assertEquals("Customer Login", driver.getTitle());

        //4 - Seleccionamos el botón "Create Account"
        driver.findElement(By.cssSelector("a[title='Create an Account']")).click();

        //5 - Verificamos que estamos en la página correcta
        assertEquals("Create New Customer Account", driver.getTitle());

        //6 - Rellenamos los campos con los datos de la cuenta y enviamos formulario
        driver.findElement(By.name("firstname")).sendKeys("Noel ");
        driver.findElement(By.cssSelector("input[name='middlename'")).sendKeys("Martinez");
        driver.findElement(By.cssSelector("input[name='lastname'")).sendKeys("Pomares");
        driver.findElement(By.cssSelector("input[name='email'")).sendKeys("noelmartinez@gmail.com");
        driver.findElement(By.id("password")).sendKeys("noelmartinez");
        driver.findElement(By.cssSelector("button[title='Register']")).submit();

        //7 - Verificamos que nos aparece el mensaje "This is required field"
        // .getText() para poder coger el texto del xpath
        assertEquals("This is a required field.",
                driver.findElement(By.xpath("//*[@id=\"advice-required-entry-confirmation\"]")).getText());

        //8 - Rellenamos el campo que nos falta y volvemos a enviar los datos del formulario.
        driver.findElement(By.id("confirmation")).sendKeys("noelmartinez");
        driver.findElement(By.cssSelector("button[title='Register']")).submit();

        //9 - Verificamos que estamos en la página correcta usando su título
        assertEquals("", driver.getTitle());
    }
}