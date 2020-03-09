package com.personal.fileutils.autologger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class AutoLoggerApplication
{

    @Autowired
    private FileManager fileManager;

    public static void main(String[] args)
    {
        SpringApplication.run(AutoLoggerApplication.class, args);
    }

    @PostConstruct
    public void init() {
        fileManager.processFiles();
    }
}
