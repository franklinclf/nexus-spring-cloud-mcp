package br.ufrn.imd.nexus.httpInterface;

import br.ufrn.imd.nexus.model.Ticket;
import org.springframework.web.service.annotation.GetExchange;

public interface AnexaAI {

    @GetExchange(url = "/triage")
    Ticket sendToTriage(Ticket consumerTicket);
}