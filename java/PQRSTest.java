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

public class PQRSTest {

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
    @DisplayName("TC_005 - Acceder a PQRS desde sección Ayuda")
    public void testAccederPQRS() throws InterruptedException {

        // 1️⃣ Login
        driver.get("https://www.bon-bonite.com/mi-cuenta/");
        Thread.sleep(2000);
        driver.findElement(By.id("username")).sendKeys("1049625888");
        driver.findElement(By.id("password")).sendKeys("Dinero*123");
        ((JavascriptExecutor) driver).executeScript(
                "document.querySelector('button[name=login]').click();");
        Thread.sleep(3000);
        System.out.println("✅ Sesión iniciada");

        // 2️⃣ Ir a página principal y hacer scroll hasta sección Ayuda
        driver.get("https://www.bon-bonite.com/");
        Thread.sleep(2000);
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
        Thread.sleep(2000);
        System.out.println("✅ Scroll hasta sección Ayuda");

        // 3️⃣ Clic en PQRS
        WebElement linkPQRS = wait.until(ExpectedConditions.elementToBeClickable(
                By.linkText("PQRS")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", linkPQRS);
        Thread.sleep(2000);
        System.out.println("✅ Clic en PQRS");

        // 4️⃣ Verificar que la página PQRS cargó correctamente
        assertTrue(driver.getCurrentUrl().contains("pqrs") || driver.getCurrentUrl().contains("contacto"),
                "❌ No se navegó a la página PQRS");
        System.out.println("✅ URL PQRS: " + driver.getCurrentUrl());
        System.out.println("🎉 Página PQRS cargada exitosamente!");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}