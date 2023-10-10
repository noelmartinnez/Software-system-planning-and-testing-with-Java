package ejercicio2.conPO;

import org.openqa.selenium.WebDriver;

public class MyAccountPage {
    private WebDriver driver;

    public MyAccountPage(WebDriver dri) { this.driver = dri; }

    public String getPageName(){ return driver.getTitle(); }
}
