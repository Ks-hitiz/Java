package com.example.MicroserviceA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class Controller {

    private AppConfig appConfig;

    public Controller(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @GetMapping("/config")
    public Map<String, String> showConfig(){
        return Map.of(
                "environment", appConfig.getEnvironment(),
                "dbUrl" , appConfig.getDbUrl(),
                "serviceUrl" , appConfig.getServiceUrl()
        );
    }
}
