package ar.com.grupoesfera.repartir.steps.grupos;

import ar.com.grupoesfera.repartir.model.Grupo;
import ar.com.grupoesfera.repartir.steps.CucumberSteps;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TotalNoPuedeSerNegativo extends CucumberSteps {

    @Y("el usuario agrega un gasto de $ 100")
    public void elUsuarioAgregaUnGastoDe$100() {
        elUsuarioAgregaunGastode(100);
    }

    private void elUsuarioAgregaunGastode(int monto) {
        WebDriverWait wait = new WebDriverWait(driver, 5);

        //Hacer clic en el botón para agregar gastos
        WebElement agregarGastosButton = driver.findElement(By.xpath("//*[@id=\"agregarGastoGruposButton-1\"]/button"));
        agregarGastosButton.click();

        // Esperar a que aparezca la ventana pequeña y el campo de entrada
        WebElement montoGastoInput = driver.findElement(By.xpath("//*[@id=\"montoGastoNuevoInput\"]"));
        montoGastoInput.click();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementById('montoGastoNuevoInput').value='"+monto+"';");

        WebElement guardarGastoButton = driver.findElement(By.xpath("//*[@id=\"guardarGastoNuevoButton\"]/button/span[1]"));
        guardarGastoButton.click();
        montoGastoInput.sendKeys(Keys.ENTER);
    }

    @Entonces("debería visualizar el grupo con total {string}")
    public void deberiaVisualizarElGrupoConTotalDe$100(String totalEsperado) {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        WebElement total = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("td.text-right")));
        String totalTexto = total.getText().replaceAll("\\s+", " ");
        assertThat(totalTexto).isEqualTo(totalEsperado.replaceAll("\\s+", " "));
    }

    @Y("el usuario agrega un gasto de $ -100")
    public void elUsuarioAgregaUnGastoDe$Menos100() {
        elUsuarioAgregaunGastode(-100);
    }

    @Entonces("deberia visualizar un mensaje de error indicando que el total no puede ser negativo")
    public void deberiaVisualizarUnMensajeDeErrorIndicandoQueElTotalNoPuedeSerNegativo() {
        assertThatThrownBy(() -> {
            Grupo grupo = new Grupo();
            grupo.setTotal(new BigDecimal(-100));
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El total no puede ser negativo");
    }


}
