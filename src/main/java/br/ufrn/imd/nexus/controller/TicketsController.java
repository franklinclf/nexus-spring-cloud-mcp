package br.ufrn.imd.nexus.controller;

import br.ufrn.imd.nexus.model.Ticket;
import br.ufrn.imd.nexus.service.TicketService;
import jakarta.ws.rs.QueryParam;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("tickets")
public class TicketsController {

    private final TicketService ticketService;

    public TicketsController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public Ticket register(@RequestBody Ticket ticket){
        return this.ticketService.register(ticket);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicket(@PathVariable UUID id){
        Optional<Ticket> existingTicket = this.ticketService.getById(id);
        return existingTicket.map(ticket -> ResponseEntity.ok().body(ticket)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTickets(){
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    @PatchMapping
    public ResponseEntity<Ticket> updateTicket(@RequestBody Ticket ticket){
        Optional<Ticket> updatedTicket = this.ticketService.update(ticket);
        return updatedTicket.map(returnTicket -> ResponseEntity.ok().body(returnTicket)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Ticket> deleteTicket(@PathVariable("id") UUID id){
        Optional<Ticket> deletedTicket = this.ticketService.delete(id);
        return deletedTicket.map(ticket -> ResponseEntity.ok().body(ticket)).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
