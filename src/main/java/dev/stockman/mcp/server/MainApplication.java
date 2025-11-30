package dev.stockman.mcp.server;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MainApplication {

    static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Bean
    public ToolCallbackProvider tools(ServiceCatalogTools serviceCatalogTools) {
        return MethodToolCallbackProvider.builder().toolObjects(serviceCatalogTools).build();
    }

}
