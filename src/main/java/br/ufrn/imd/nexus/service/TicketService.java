package br.ufrn.imd.nexus.service;

import br.ufrn.imd.nexus.model.Ticket;
import br.ufrn.imd.nexus.repository.TicketRepository;
import br.ufrn.imd.nexus.repository.VectorRepository;
import jakarta.transaction.Transactional;
import org.springframework.ai.document.Document;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final VectorRepository vectorRepository;

    public TicketService(TicketRepository ticketRepository, VectorRepository vectorRepository) {
        this.ticketRepository = ticketRepository;
        this.vectorRepository = vectorRepository;
    }

    @Transactional
    public Ticket register(Ticket newTicket) {

        if (newTicket == null) {
            throw new IllegalArgumentException("Ticket: campo obrigatório.");
        }
        Ticket savedTicket = ticketRepository.save(newTicket);
        Document document = ticketToDocument(savedTicket);
        vectorRepository.add(Collections.singletonList(document));
        return ticketRepository.save(newTicket);
    }

    @Transactional
    public Optional<Ticket> getById(UUID uuid) {
        return ticketRepository.findById(uuid);
    }

    @Transactional
    @Tool(description = "Recupera tickets registrados no sistema que sejam semanticamente semelhantes à descrição fornecida. Essa ferramenta realiza uma busca por similaridade no conteúdo textual dos tickets (título, descrição, categoria, etc.), permitindo encontrar tickets mesmo que as palavras usadas não sejam exatamente as mesmas. Ideal para identificar tickets anteriores com problemas parecidos, auxiliar em diagnósticos, ou sugerir soluções com base em históricos semelhantes.")
    public List<Ticket> searchTicketsBySimilarity(@ToolParam(description = "A query é a String utilizada para procurar conteúdos mais similares com ela em relação ao seu conteúdo.") String query) {
        List<Document> documents = vectorRepository.searchTicket(query);
        List<Ticket> tickets = new ArrayList<>();
        for (Document document : documents) {
            UUID ticketId = (UUID) document.getMetadata().get("ticketId");
            Optional<Ticket> ticket = getById(ticketId);
            ticket.ifPresent(tickets::add);
        }
        return tickets;
    }

    @Transactional
    public Optional<Ticket> update(Ticket ticket) {
        if (ticket == null) {
            throw new IllegalArgumentException("Ticket: campo obrigatório.");
        }

        Optional<Ticket> existingTicket = getById(ticket.getTicketId());
        if (existingTicket.isEmpty()) {
            return Optional.empty();
        }

        existingTicket.get().update(ticket);

        vectorRepository.delete(existingTicket.get().getTicketId().toString());

        vectorRepository.add(ticketToDocument(existingTicket.get()));

        return existingTicket;
    }

    @Transactional
    public Optional<Ticket> delete(UUID id) {
        Optional<Ticket> deletedTicket = this.ticketRepository.findById(id);
        if (deletedTicket.isPresent()) {
            this.ticketRepository.deleteById(id);
            return deletedTicket;
        }
        return Optional.empty();
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Document ticketToDocument(Ticket ticket) {
        String content = """
            ticketId: %s,
            customerName: %s,
            customerCompany: %s,
            contactEmail: %s,
            title: %s,
            initialProblem: %s,
            status: %s,
            creationTimestamp: %s,
            triageSummary: %s,
            triageSuggestedTitle: %s,
            triageUrgencyAnalysis: %s,
            triageImpactAnalysis: %s,
            triagePredictedCategory: %s,
            triageAssignedPriority: %s
        """.formatted(
                ticket.getTicketId(),
                ticket.getCustomerInfo().getCustomerName(),
                ticket.getCustomerInfo().getCustomerName(),
                ticket.getCustomerInfo().getContactEmail(),
                ticket.getTitle(),
                ticket.getInitialProblem(),
                ticket.getStatus(),
                ticket.getCreationTimestamp().toString(),
                ticket.getTriageAnalysis().getSummary(),
                ticket.getTriageAnalysis().getSuggestedTitle(),
                ticket.getTriageAnalysis().getUrgencyAnalysis(),
                ticket.getTriageAnalysis().getImpactAnalysis(),
                ticket.getTriageAnalysis().getPredictedCategory(),
                ticket.getTriageAnalysis().getAssignedPriority()
        );

        return Document.builder().text(content).id(ticket.getTicketId().toString()).build();
    }
}
