package br.ufrn.imd.nexus.service;

import br.ufrn.imd.nexus.httpInterface.AnexaAI;
import br.ufrn.imd.nexus.model.Ticket;
import br.ufrn.imd.nexus.repository.TicketRepository;
import jakarta.transaction.Transactional;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final VectorStore vectorRepository;
    private final AnexaAI anexaAI;

    public TicketService(TicketRepository ticketRepository, VectorStore vectorRepository, AnexaAI anexaAI) {
        this.ticketRepository = ticketRepository;
        this.vectorRepository = vectorRepository;
        this.anexaAI = anexaAI;
    }

    @Transactional
    public Ticket register(Ticket newTicket) {

        if (newTicket == null) {
            throw new IllegalArgumentException("Ticket: campo obrigatório.");
        }

        Ticket postTriageTicket = anexaAI.sendToTriage(newTicket);

        Document document = ticketToDocument(postTriageTicket);
        vectorRepository.add(Collections.singletonList(document));

        return ticketRepository.save(postTriageTicket);
    }

    public Optional<Ticket> getById(UUID uuid) {
        return ticketRepository.findById(uuid);
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

        vectorRepository.add(Collections.singletonList(ticketToDocument(existingTicket.get())));

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
