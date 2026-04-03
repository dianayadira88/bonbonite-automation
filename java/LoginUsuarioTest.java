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

public class LoginUsuarioTest {

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
    @DisplayName("TC_002 - Inicio de sesión exitoso")
    public void testInicioSesion() throws InterruptedException {

        // 1️⃣ Abrir la página principal
        driver.get("https://www.bon-bonite.com/");
        Thread.sleep(2000);
        System.out.println("✅ Página principal abierta: " + driver.getTitle());

        // 2️⃣ Hacer clic en "Mi cuenta"
        WebElement miCuenta = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("a[href*='mi-cuenta']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", miCuenta);
        Thread.sleep(2000);
        System.out.println("✅ Clic en Mi Cuenta realizado");

        // 3️⃣ Verificar que estamos en la página de login
        wait.until(ExpectedConditions.urlContains("mi-cuenta"));
        System.out.println("✅ Página de Mi Cuenta abierta");

        // 4️⃣ Ingresar cédula como usuario
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        driver.findElement(By.id("username")).sendKeys("1049625888");
        System.out.println("✅ Usuario ingresado");

        // 5️⃣ Ingresar contraseña
        driver.findElement(By.id("password")).sendKeys("Dinero*123");
        System.out.println("✅ Contraseña ingresada");

        // 6️⃣ Clic en Iniciar Sesión
        ((JavascriptExecutor) driver).executeScript(
                "document.querySelector('button[name=login]').click();");
        System.out.println("✅ Clic en Iniciar Sesión");

        // 7️⃣ Verificar acceso exitoso
        Thread.sleep(3000);
        String urlFinal = driver.getCurrentUrl();
        System.out.println("✅ URL final: " + urlFinal);
        assertTrue(urlFinal.contains("mi-cuenta"), "❌ El inicio de sesión no fue exitoso");
        System.out.println("🎉 Inicio de sesión exitoso!");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}