package com.swtestacademy.springbootselenium.pages;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import com.swtestacademy.springbootselenium.annotations.LazyComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@LazyComponent
public class AuthPage extends BasePage {

    //********* Web Elements by using By Class *********
    By btnProximo = By.id("ctl00_cphLogin_btnPesquisaUsuarioPorCpf");
    By cpf = By.id("ctl00_cphLogin_txtCpfParaPesquisa");
    By resumoConta = By.id("ctl00_cphPrincipal_Resumo_ddlConta");
    By btnFiltro = By.xpath("//*[contains(text(), 'Aplicar Filtros')]");
    By btnFiltroDaycoval = By.xpath("//span[contains(@class, 'mdc-evolution-chip__text-label') and contains(text(), 'Daycoval')]");
    By btnFiltroIsento = By.xpath("//span[contains(@class, 'mdc-evolution-chip__text-label') and contains(text(), 'Isento')]");
    By tabLCILCA = By.xpath("//a[contains(@data-bs-target, '#tab-LCILCA')]");

    //*********Page Methods*********
    public AuthPage login(String cpf) {
        driver.get("https://ecode.daycoval.com.br");
        writeText(this.cpf, cpf);
        jsClick(btnProximo);

        System.out.println(">>> faça: (1) digite a senha numerica e (2) token em até 240s");

        var w = new WebDriverWait(driver, Duration.ofSeconds(240));
        w.until(ExpectedConditions.presenceOfElementLocated(resumoConta));
        System.out.println("Autenticação OK");

        driver.get("https://ecode.daycoval.com.br/ng/Investimento/#/RendaFixa/Aplicar");

        w.until(ExpectedConditions.visibilityOfElementLocated(tabLCILCA));
        jsClick(tabLCILCA);
        
        w.until(ExpectedConditions.visibilityOfElementLocated(btnFiltro));

        w.until(ExpectedConditions.visibilityOfElementLocated(btnFiltroDaycoval));

        

        jsClick(btnFiltroDaycoval);
        jsClick(btnFiltroIsento);

        //By btnInvestir = By.xpath("//td[contains(@class, 'cdk-column-nomeProduto') and contains(text(), 'LCA - DAYCOVAL PÓS')]/following::button[1]");
        By btnInvestir = By.xpath("//td[contains(@class, 'cdk-column-nomeProduto') and contains(text(), '90 DIAS')]/following::button[1]");

        boolean encontrou = false;
        do {
            System.out.println("Loop procura invest");
            jsClick(btnFiltro);
            w.until(ExpectedConditions.visibilityOfElementLocated(btnFiltro));
            var elements = driver.findElements(btnInvestir);
            if (elements.size() != 0) {
                encontrou = true;
                jsClick(btnInvestir);
            }
        } while(!encontrou);

        //jsClick(By.xpath("//td[contains(@class, 'cdk-column-nomeProduto') and contains(text(), '90 DIAS')]/following::button[1]"));
        
        return this;
    }

    @Override public boolean isAt() {
        return this.wait.until((d) -> driver.findElement(resumoConta).isDisplayed());
    }
}
