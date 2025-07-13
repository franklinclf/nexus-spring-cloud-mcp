package br.ufrn.imd.nexus.repository;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class VectorRepository {
    private final SimpleVectorStore simpleVectorStore;

    public VectorRepository(SimpleVectorStore simpleVectorStore) {
        this.simpleVectorStore = simpleVectorStore;
    }

    public void add(List<Document> documents) {
        simpleVectorStore.add(documents);
    }

    public void add(Document document) {
        List<Document> documents = new ArrayList<>();
        documents.add(document);
        simpleVectorStore.add(documents);
    }

    public void delete(String ticketId) {
        List<String> ids = new ArrayList<>();
        ids.add(ticketId);
        simpleVectorStore.delete(ids);
    }

    public List<Document> searchTicket(String query){
        return simpleVectorStore.similaritySearch(query);
    }

    public SimpleVectorStore getStore() {
        return this.simpleVectorStore;
    }
}
