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

public class ModificarDatosTest {

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
    @DisplayName("TC_003 - Modificar nombre del usuario")
    public void testModificarDatos() throws InterruptedException {

        // 1️⃣ Abrir la página principal
        driver.get("https://www.bon-bonite.com/");
        Thread.sleep(2000);
        System.out.println("✅ Página principal abierta");

        // 2️⃣ Hacer clic en Mi Cuenta
        WebElement miCuenta = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("a[href*='mi-cuenta']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", miCuenta);
        Thread.sleep(2000);
        System.out.println("✅ Clic en Mi Cuenta realizado");

        // 3️⃣ Ingresar credenciales
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        driver.findElement(By.id("username")).sendKeys("1049625888");
        driver.findElement(By.id("password")).sendKeys("Dinero*123");
        System.out.println("✅ Credenciales ingresadas");

        // 4️⃣ Clic en Iniciar Sesión
        ((JavascriptExecutor) driver).executeScript(
                "document.querySelector('button[name=login]').click();");
        Thread.sleep(3000);
        System.out.println("✅ Sesión iniciada");

        // 5️⃣ Ir directo a editar cuenta
        driver.get("https://www.bon-bonite.com/mi-cuenta/edit-account/");
        Thread.sleep(2000);
        System.out.println("✅ Página de edición abierta");

        // 6️⃣ DIAGNÓSTICO - ver qué inputs hay en la página de edición
        Thread.sleep(1000);
        System.out.println("📄 URL actual: " + driver.getCurrentUrl());
        System.out.println("\n🔍 === INPUTS EN PÁGINA DE EDICIÓN ===");
        for (WebElement input : driver.findElements(By.tagName("input"))) {
            System.out.println("  INPUT → id='" + input.getAttribute("id")
                    + "' name='" + input.getAttribute("name")
                    + "' type='" + input.getAttribute("type")
                    + "' visible=" + input.isDisplayed()
                    + " value='" + input.getAttribute("value") + "'");
        } // ✅ cierra el for

        System.out.println("🎉 Diagnóstico completado");
    } // ✅ cierra el test

    @AfterEach
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}