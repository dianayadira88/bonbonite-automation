import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class NavegarProductosTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Test
    @DisplayName("TC_004 - Navegar Zapatos y Bolsos")
    public void testNavegarProductos() throws InterruptedException {

        // 1️⃣ Login
        driver.get("https://www.bon-bonite.com/mi-cuenta/");
        Thread.sleep(2000);
        driver.findElement(By.id("username")).sendKeys("1049625888");
        driver.findElement(By.id("password")).sendKeys("Dinero*123");
        ((JavascriptExecutor) driver).executeScript(
                "document.querySelector('button[name=login]').click();");
        Thread.sleep(3000);
        System.out.println("✅ Sesión iniciada");

        // 2️⃣ Ir a Zapatos y hacer scroll
        driver.get("https://www.bon-bonite.com/");
        Thread.sleep(2000);
        WebElement linkZapatos = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(translate(.,'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'ZAPATO')]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", linkZapatos);
        Thread.sleep(2000);
        System.out.println("✅ En categoría Zapatos");

        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
        Thread.sleep(2000);
        System.out.println("✅ Scroll en Zapatos completado");

        // 3️⃣ Ir a Bolsos y hacer scroll
        driver.get("https://www.bon-bonite.com/");
        Thread.sleep(2000);
        WebElement linkBolsos = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(translate(.,'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'BOLSO')]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", linkBolsos);
        Thread.sleep(2000);
        System.out.println("✅ En categoría Bolsos");

        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
        Thread.sleep(2000);
        System.out.println("✅ Scroll en Bolsos completado");

        // 4️⃣ Verificar que llegamos a la página correcta
        assertTrue(driver.getCurrentUrl().contains("bolso") || driver.getCurrentUrl().contains("bag"),
                "❌ No se navegó a Bolsos");
        System.out.println("🎉 Navegación completada exitosamente!");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}