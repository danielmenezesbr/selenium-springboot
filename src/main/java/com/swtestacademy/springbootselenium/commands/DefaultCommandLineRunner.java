package com.swtestacademy.springbootselenium.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Order(-1) // Run before Spring Shell
@Slf4j
public class DefaultCommandLineRunner implements CommandLineRunner {

    @Autowired
    MyCommands myCommands;

    @Override
    public void run(String... args) throws Exception {
        try {
            myCommands.tudo();
        } catch (Exception e) {
            log.error("Ocorreu um erro no startup quando tentou realizar o comando tudo", e);
        }
    }

}