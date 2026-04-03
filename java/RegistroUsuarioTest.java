import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

public class RegistroUsuarioTest {

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
    public void testRegistroUsuario() throws InterruptedException {

        // 1️⃣ Abrir la página de Mi Cuenta
        driver.get("https://www.bon-bonite.com/mi-cuenta/");
        Thread.sleep(2000);
        System.out.println("✅ Página abierta: " + driver.getTitle());

        // 2️⃣ Hacer clic en Regístrate para mostrar el formulario
        WebElement registroLink = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.id("show_register")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", registroLink);
        Thread.sleep(2000);
        System.out.println("✅ Formulario de registro visible");

        // 3️⃣ Llenar el formulario de registro
        driver.findElement(By.id("reg_username")).sendKeys("1049625888");
        driver.findElement(By.id("first_name")).sendKeys("Diana");
        driver.findElement(By.id("last_name")).sendKeys("Rojas");
        driver.findElement(By.id("reg_email")).sendKeys("dianayadira25@yopmail.com");
        driver.findElement(By.id("reg_password")).sendKeys("Dinero*123");
        driver.findElement(By.id("reg_password2")).sendKeys("Dinero*123");
        System.out.println("✅ Datos ingresados correctamente");

        // 4️⃣ Activar los checkboxes
        ((JavascriptExecutor) driver).executeScript(
                "document.getElementById('newsletter_authorization').click();");
        ((JavascriptExecutor) driver).executeScript(
                "document.getElementById('privacy_policy_reg').click();");
        System.out.println("✅ Checkboxes activados");

        // 5️⃣ Hacer clic en Registrarme (name="register")
        WebElement botonRegistrar = wait.until(
                ExpectedConditions.elementToBeClickable(By.cssSelector("button[name='register']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", botonRegistrar);
        Thread.sleep(500);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", botonRegistrar);
        System.out.println("✅ Clic en Registrarme realizado");

        // 6️⃣ Verificar que el registro fue exitoso
        Thread.sleep(3000);
        String urlFinal = driver.getCurrentUrl();
        System.out.println("✅ URL final: " + urlFinal);

        // Si redirigió a mi-cuenta significa que el registro fue exitoso
        assertTrue(urlFinal.contains("mi-cuenta"), "❌ El registro no fue exitoso");
        System.out.println("🎉 ¡Registro completado con éxito!");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}