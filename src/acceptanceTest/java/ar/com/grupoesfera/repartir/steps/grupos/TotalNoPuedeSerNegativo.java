package ar.com.grupoesfera.repartir.steps.grupos;

import ar.com.grupoesfera.repartir.model.Grupo;
import ar.com.grupoesfera.repartir.steps.CucumberSteps;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TotalNoPuedeSerNegativo extends CucumberSteps {

    @Y("el usuario agrega un gasto de $ 100")
    public void elUsuarioAgregaUnGastoDe$100() {
        var gastoInput = driver.findElement(By.id("gastoInput"));
        gastoInput.sendKeys("100");
        gastoInput.sendKeys(Keys.ENTER);
    }

    @Entonces("debería visualizar el grupo con total {string}")
    public void deberiaVisualizarElGrupoConTotalDe$100() {
        var total = driver.findElement(By.id("total"));
        assertThat(total.getText()).isEqualTo("$ 100");
    }

    @Y("el usuario agrega un gasto de $ -100")
    public void elUsuarioAgregaUnGastoDe$Menos100() {
        var gastoInput = driver.findElement(By.id("gastoInput"));
        gastoInput.sendKeys("-100");
        gastoInput.sendKeys(Keys.ENTER);
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
