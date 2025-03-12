package fr.rocamora.barrali.customer.Controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RefreshScope
public class ConfigTestController {
    @Value("${a}")
    private int a;
    @Value("${b}")
    private int b;
    @Value("${c}")
    private int c;
    @GetMapping("/testConfig")
    public Map<String, Integer> testConfig()
    {
        return Map.of("a",a,"b",b,"c",c);
    }
}