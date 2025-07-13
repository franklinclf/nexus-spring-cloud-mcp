package br.ufrn.imd.nexus.service;

import br.ufrn.imd.nexus.model.Ticket;
import br.ufrn.imd.nexus.repository.TicketRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Transactional
    public Ticket register(Ticket newTicket) {

        if (newTicket == null) {
            throw new IllegalArgumentException("Ticket: campo obrigatório.");
        }

        return ticketRepository.save(newTicket);
    }

    @Transactional
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
}
