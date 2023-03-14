package com.swtestacademy.springbootselenium.pages;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

import lombok.extern.slf4j.Slf4j;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class AuthPage extends BasePage {

    //********* Web Elements by using By Class *********
    By btnProximo = By.id("ctl00_cphLogin_btnPesquisaUsuarioPorCpf");
    By inputCpf = By.id("ctl00_cphLogin_txtCpfParaPesquisa");
    By resumoConta = By.id("ctl00_cphPrincipal_Resumo_ddlConta");
    By btnFiltro = By.xpath("//*[contains(text(), 'Aplicar Filtros')]");
    By btnFiltroDaycoval = By.xpath("//span[contains(@class, 'mdc-evolution-chip__text-label') and contains(text(), 'Daycoval')]");
    By btnFiltroIsento = By.xpath("//span[contains(@class, 'mdc-evolution-chip__text-label') and contains(text(), 'Isento')]");
    By tabLCILCA = By.xpath("//a[contains(@data-bs-target, '#tab-LCILCA')]");

    //*********Page Methods*********
    public void login(String cpf) {
        driver.get("https://ecode.daycoval.com.br");

        var w = new WebDriverWait(driver, Duration.ofSeconds(240));
        w.until(ExpectedConditions.visibilityOfElementLocated(this.inputCpf));
        //jsClick(this.inputCpf);

        if (StringUtils.hasLength(cpf)) {
            writeText(this.inputCpf, cpf);
            jsClick(btnProximo);
        } else {
            log.info("Informe CPF na tela");
        }
        
        System.out.println(">>> faça: (1) digite a senha numerica e (2) token em até 240s");

        
        w.until(ExpectedConditions.presenceOfElementLocated(resumoConta));
        System.out.println("Autenticação OK");
    }
}
