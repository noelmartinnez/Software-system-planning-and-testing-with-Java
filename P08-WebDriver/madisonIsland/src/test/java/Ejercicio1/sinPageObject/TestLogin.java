package Ejercicio1.sinPageObject;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestLogin {
    private WebDriver driver;

    @BeforeEach
    public void inicializacion(){
        driver=new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("http://demo-store.seleniumacademy.com");
    }

    @AfterEach
    public void cerrar(){
        driver.close();
    }

    @Test
    public void loginOK() {
        //1 - Verificamos que el título de la página de inicio es el correcto
        assertEquals("Madison Island",driver.getTitle());

        //2 - Seleccionamos Account, y a continuacion seleccionamos el hiperenlace Login
        driver.findElement(By.xpath("//*[@id=\"header\"]/div/div[2]/div/a")).click();
        driver.findElement(By.xpath("//*[@id=\"header-account\"]/div/ul/li[6]/a")).click();

        //3 - Verificamos que el título de la página es el correcto
        assertEquals("Customer Login",driver.getTitle());

        //4 - Rellenamos el campo email con un email válido y enviamos el formulario
        driver.findElement(By.cssSelector("input[title='Email Address']")).sendKeys("noelmartinez@gmail.com");
        driver.findElement(By.cssSelector("button[title='Login']")).submit();

        //5 - Verificamos que nos aparece el mensaje "This is a required field."
        assertEquals("This is a required field.",
                driver.findElement(By.cssSelector("div#advice-required-entry-pass")).getText());

        //6 - Rellenamos el campo con la contraseña y volvemos a enviar los datos del formulario.
        driver.findElement(By.xpath("//*[@id=\"pass\"]")).sendKeys("noelmartinez");
        driver.findElement(By.cssSelector("button[title='Login']")).submit();

        //7 - Verificamos que estamos en la página correcta usando su título ("My Account").
        assertEquals("My Account", driver.getTitle());

    }

    @Test
    public void loginFailed(){
        //1 - Verificamos que el título de la página de inicio es el correcto
        assertEquals("Madison Island",driver.getTitle());

        //2 - Seleccionamos Account, y a continuacion seleccionamos el hiperenlace Login
        driver.findElement(By.xpath("//*[@id=\"header\"]/div/div[2]/div/a")).click();
        driver.findElement(By.xpath("//*[@id=\"header-account\"]/div/ul/li[6]/a")).click();

        //3 - Verificamos que el título de la página es el correcto
        assertEquals("Customer Login",driver.getTitle());

        //4 - Rellenamos el campo email con un email válido, y un password incorrecto y enviamos formulario
        driver.findElement(By.cssSelector("input[title='Email Address']")).sendKeys("noelmartinez@gmail.com");
        driver.findElement(By.cssSelector("input[title='Password']")).sendKeys("incorrecto");
        driver.findElement(By.cssSelector("button[title='Login']")).submit();

        //5 - Verificamos que nos aparece el mensaje "Invalid login or password"
        //    Utilizo xpath -> selecciono el texto y pillo el xpath del <span>, no del <li>
        assertEquals("Invalid login or password.",
                driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div[2]/div[2]/div/div/div[2]/ul/li/ul/li/span")).getText());
    }
}
