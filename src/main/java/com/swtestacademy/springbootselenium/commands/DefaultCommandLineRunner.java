package com.swtestacademy.springbootselenium.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(-1) // Run before Spring Shell
public class DefaultCommandLineRunner implements CommandLineRunner {

    @Autowired
    MyCommands myCommands;

    @Override
    public void run(String... args) throws Exception {
        myCommands.tudo();
    }

}