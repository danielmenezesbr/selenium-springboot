package com.swtestacademy.springbootselenium.pages;

import java.time.Duration;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BuscaInvestimentoLoopPage extends BasePage {

    @Getter @Setter boolean abortarLoop = false;

    //********* Web Elements by using By Class *********
    By btnProximo = By.id("ctl00_cphLogin_btnPesquisaUsuarioPorCpf");
    By inputCpf = By.id("ctl00_cphLogin_txtCpfParaPesquisa");
    By resumoConta = By.id("ctl00_cphPrincipal_Resumo_ddlConta");
    By btnFiltro = By.xpath("//*[contains(text(), 'Aplicar Filtros')]");
    By btnFiltroDaycoval = By.xpath("//span[contains(@class, 'mdc-evolution-chip__text-label') and contains(text(), 'Daycoval')]");
    By btnFiltroIsento = By.xpath("//span[contains(@class, 'mdc-evolution-chip__text-label') and contains(text(), 'Isento')]");
    By tabLCILCA = By.xpath("//a[contains(@data-bs-target, '#tab-LCILCA')]");
    By inputValorInvestimento = By.id("ctl00_cphPrincipal_txtValor");
    By btnSolicitarInvestimento = By.id("ctl00_cphPrincipal_btnSolicitar");
    By ckbSaldoDia = By.id("ctl00_cphPrincipal_chkSaldoDia");
    
    
    
    @Async
    @Retryable(value = RuntimeException.class, maxAttempts = 100)
    public void loopStart(String textoNomeInvestimento, String valorInvestimento) {
        log.info("loopStart - inicio");
        var w = new WebDriverWait(driver, Duration.ofSeconds(30));
        driver.get("https://ecode.daycoval.com.br/ng/Investimento/#/RendaFixa/Aplicar");

        w.until(ExpectedConditions.visibilityOfElementLocated(tabLCILCA));
        jsClick(tabLCILCA);
        
        w.until(ExpectedConditions.visibilityOfElementLocated(btnFiltro));

        w.until(ExpectedConditions.visibilityOfElementLocated(btnFiltroDaycoval));

        

        jsClick(btnFiltroDaycoval);
        jsClick(btnFiltroIsento);

        //String xpathInvestir = String.format("//td[contains(@class, 'cdk-column-nomeProduto') and contains(text(), '%s')]/following::button[1]", textoNomeInvestimento);
        //log.info(String.format("$x(%s)", xpathInvestir));
        //By btnInvestir = By.xpath(xpathInvestir);
        //By btnInvestir = By.xpath("//td[contains(@class, 'cdk-column-nomeProduto') and contains(text(), 'DAYCOVAL')]/following::button[1]");
        By btnInvestir = By.xpath("//td[contains(@class, 'cdk-column-nomeProduto') and contains(text(), '90 DIAS')]/following::button[1]");

        boolean encontrouOuSolitacaoParaAbortar = false;
        do {
            //w.until(ExpectedConditions.visibilityOfElementLocated(btnFiltro));
            w.until(ExpectedConditions.elementToBeClickable(btnFiltro));
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
            jsClick(btnFiltro);

            w.until(ExpectedConditions.jsReturnsValue("return window.pageYOffset === 0 ? 'true' : undefined;"));
            w.until(ExpectedConditions.elementToBeClickable(btnFiltro));

            var elements = driver.findElements(btnInvestir);
            if (elements.size() != 0) {
                encontrouOuSolitacaoParaAbortar = true;
                jsClick(btnInvestir);

                w.until(ExpectedConditions.visibilityOfElementLocated(btnSolicitarInvestimento));

                if ("0".equals(valorInvestimento)) {
                    // marca o saldo
                    JavascriptExecutor js = (JavascriptExecutor) driver;                    
                    js.executeScript("document.evaluate('//*[@id=\"ctl00_cphPrincipal_chkUsarSaldo\"]', document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.click()");
                } else {
                    //digita o valor do investimento
                    writeText(inputValorInvestimento, valorInvestimento);
                }

                //confirma solicitação do investimento
                jsClick(btnSolicitarInvestimento);

                log.info("Fornceça as informações de segurança para finalizar o investimento");
                
                // ***** em teste - fim
            }
            if (this.abortarLoop) {
                encontrouOuSolitacaoParaAbortar = true;
                this.abortarLoop = false;
            }
        } while(!encontrouOuSolitacaoParaAbortar);
        log.info("loopStart - fim");
    }
}
