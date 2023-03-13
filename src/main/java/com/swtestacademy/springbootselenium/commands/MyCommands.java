package com.swtestacademy.springbootselenium.commands;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.stereotype.Component;

import com.swtestacademy.springbootselenium.annotations.LazyAutowired;
import com.swtestacademy.springbootselenium.pages.AuthPage;
import com.swtestacademy.springbootselenium.pages.BuscaInvestimentoLoopPage;

@ShellComponent
@Component
public class MyCommands {

	@LazyAutowired
    private AuthPage authPage;

	@LazyAutowired
    private BuscaInvestimentoLoopPage buscaInvestimentoLoopPage;

	@Value("${investimento.cpf}")
	private String cpf;

	@Value("${investimento.valor}")
	private String valorInvestimento;

	@Value("${investimento.texto_nome_investimento}")
	private String textoNomeInvestimento;

	@ShellMethod(key = "tudo")
	public String tudo() {
		autenticar();
		loopStart();
		return "Ok?";
	}

	@ShellMethod(key = "autenticar")
	public String autenticar() {
		authPage.login(cpf);
		return "Ok";
	}

	@ShellMethod(key = "loop-start")
	public String loopStart() {
		buscaInvestimentoLoopPage.loopStart(textoNomeInvestimento, valorInvestimento);
		return "Ok";
	}

	@ShellMethod(key = "loop-abortar")
	public String loopAbortar() {
		buscaInvestimentoLoopPage.setAbortarLoop(true);
		return "Solicita";
	}
}