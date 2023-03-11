package com.swtestacademy.springbootselenium.pages;

import java.time.Duration;

import com.swtestacademy.springbootselenium.annotations.LazyComponent;

import lombok.Getter;
import lombok.Setter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class BuscaInvestimentoLoopPage extends BasePage {

    @Getter @Setter boolean abortarLoop = false;

    //********* Web Elements by using By Class *********
    By btnProximo = By.id("ctl00_cphLogin_btnPesquisaUsuarioPorCpf");
    By cpf = By.id("ctl00_cphLogin_txtCpfParaPesquisa");
    By resumoConta = By.id("ctl00_cphPrincipal_Resumo_ddlConta");
    By btnFiltro = By.xpath("//*[contains(text(), 'Aplicar Filtros')]");
    By btnFiltroDaycoval = By.xpath("//span[contains(@class, 'mdc-evolution-chip__text-label') and contains(text(), 'Daycoval')]");
    By btnFiltroIsento = By.xpath("//span[contains(@class, 'mdc-evolution-chip__text-label') and contains(text(), 'Isento')]");
    By tabLCILCA = By.xpath("//a[contains(@data-bs-target, '#tab-LCILCA')]");

    //*********Page Methods*********
    @Async
    public void loopStart() {
        this.abortarLoop = false;

        var w = new WebDriverWait(driver, Duration.ofSeconds(30));
        driver.get("https://ecode.daycoval.com.br/ng/Investimento/#/RendaFixa/Aplicar");

        w.until(ExpectedConditions.visibilityOfElementLocated(tabLCILCA));
        jsClick(tabLCILCA);
        
        w.until(ExpectedConditions.visibilityOfElementLocated(btnFiltro));

        w.until(ExpectedConditions.visibilityOfElementLocated(btnFiltroDaycoval));

        

        jsClick(btnFiltroDaycoval);
        jsClick(btnFiltroIsento);

        //By btnInvestir = By.xpath("//td[contains(@class, 'cdk-column-nomeProduto') and contains(text(), 'LCA - DAYCOVAL PÓS')]/following::button[1]");
        By btnInvestir = By.xpath("//td[contains(@class, 'cdk-column-nomeProduto') and contains(text(), '90 DIAS')]/following::button[1]");

        boolean encontrouOuSolitacaoParaAbortar = false;
        do {
            jsClick(btnFiltro);
            w.until(ExpectedConditions.visibilityOfElementLocated(btnFiltro));
            var elements = driver.findElements(btnInvestir);
            if (elements.size() != 0) {
                encontrouOuSolitacaoParaAbortar = true;
                jsClick(btnInvestir);
            }
            if (this.abortarLoop) {
                encontrouOuSolitacaoParaAbortar = true;
            }
        } while(!encontrouOuSolitacaoParaAbortar);
    }
}
