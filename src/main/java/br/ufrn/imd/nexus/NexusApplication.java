package br.ufrn.imd.nexus;

import br.ufrn.imd.nexus.controller.TicketsController;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class NexusApplication {

    public static void main(String[] args) {
        SpringApplication.run(NexusApplication.class, args);
    }

    @Bean
    public ToolCallbackProvider tools(TicketsController ticketsController) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(ticketsController)
                .build();
    }
}
