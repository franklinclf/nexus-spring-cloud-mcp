package br.ufrn.imd.nexus.config;

import jakarta.annotation.PreDestroy;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class VectorStoreConfig {

    @Value("${spring.ai.vectorStore.path}")
    private String vectorStorePath;
    private SimpleVectorStore simpleVectorStoreBeanInstance;

    @Bean
    public SimpleVectorStore vectorStore(EmbeddingModel embeddingModel) {
        File file = new File(vectorStorePath);
        SimpleVectorStore simpleVectorStore = SimpleVectorStore.builder(embeddingModel).build();
        this.simpleVectorStoreBeanInstance = simpleVectorStore;

        if (file.exists() && file.isFile()) {
            try {
                simpleVectorStore.load(file);
                System.out.println("VectorStore carregado de: " + file.getAbsolutePath());
            } catch (Exception e) {
                System.err.println("Erro ao carregar VectorStore:");
                e.printStackTrace();
            }
        }

        return simpleVectorStore;
    }

    @PreDestroy
    public void saveVectorStore() {
        if (this.simpleVectorStoreBeanInstance != null) {
            File saveFile = new File(this.vectorStorePath);
            saveFile.getParentFile().mkdirs();
            try {
                this.simpleVectorStoreBeanInstance.save(saveFile);
                System.out.println("VectorStore salvo em: " + saveFile.getAbsolutePath());
            } catch (Exception e) {
                System.err.println("Erro ao salvar VectorStore:");
                e.printStackTrace();
            }
        }
    }
}
