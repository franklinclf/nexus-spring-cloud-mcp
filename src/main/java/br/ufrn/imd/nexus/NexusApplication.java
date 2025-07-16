package br.ufrn.imd.nexus;

import br.ufrn.imd.nexus.model.Ticket;
import br.ufrn.imd.nexus.service.TicketService;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.function.FunctionToolCallback;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
public class NexusApplication {

    public static void main(String[] args) {
        SpringApplication.run(NexusApplication.class, args);
    }

    @Bean
    public ToolCallbackProvider toolCallbackProvider(TicketService ticketService) {
        return MethodToolCallbackProvider.builder().toolObjects(ticketService).build();
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("http://gateway:8080/ai/")
                .build();
    }
}
