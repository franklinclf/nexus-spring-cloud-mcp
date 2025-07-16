package br.ufrn.imd.nexus.config;

import br.ufrn.imd.nexus.httpInterface.AnexaAI;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class AnexaAiConfig {

    @Bean
    @Qualifier("AnexaAIWebClient")
    public WebClient anexaAIWebClient() {
        return WebClient.builder()
                .baseUrl("http://gateway:8080/ai")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean
    public AnexaAI anexaAI(@Qualifier("anexaAIWebClient") WebClient webClient) {
        WebClientAdapter adapter = WebClientAdapter.create(webClient);
        return HttpServiceProxyFactory.builderFor(adapter)
                .build()
                .createClient(AnexaAI.class);
    }
}