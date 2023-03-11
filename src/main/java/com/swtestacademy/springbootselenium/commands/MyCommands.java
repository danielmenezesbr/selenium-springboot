package com.swtestacademy.springbootselenium.commands;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import com.swtestacademy.springbootselenium.annotations.LazyAutowired;
import com.swtestacademy.springbootselenium.pages.AuthPage;
import com.swtestacademy.springbootselenium.pages.BuscaInvestimentoLoopPage;

@ShellComponent
public class MyCommands {

	@LazyAutowired
    private AuthPage authPage;

	@LazyAutowired
    private BuscaInvestimentoLoopPage buscaInvestimentoLoopPage;

	@ShellMethod(key = "tudo")
	public String tudo(@ShellOption(defaultValue = "") String cpf) {
		autenticar(cpf);
		loopStart();
		return "Ok?";
	}

	@ShellMethod(key = "autenticar")
	public String autenticar(
		@ShellOption(defaultValue = "") String cpf
	) {
		authPage.login(cpf);
		return "Ok";
	}

	@ShellMethod(key = "loop-start")
	public String loopStart() {
		buscaInvestimentoLoopPage.loopStart();
		return "Ok";
	}

	@ShellMethod(key = "loop-abortar")
	public String loopAbortar() {
		buscaInvestimentoLoopPage.setAbortarLoop(true);
		return "Solicita";
	}
}