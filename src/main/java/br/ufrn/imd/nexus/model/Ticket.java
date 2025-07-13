package br.ufrn.imd.nexus.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID ticketId;
    @Embedded
    private CustomerInfo customerInfo;
    private String title;
    private String initialProblem;
    @Enumerated(EnumType.STRING) private TicketStatus status;
    @CreationTimestamp
    private LocalDateTime creationTimestamp;
    @Embedded
    TriageAnalysis triageAnalysis;

    public void update(Ticket ticket) {
        if (ticket.getTitle() != null && !ticket.getTitle().equals(this.title)) {
            this.title = ticket.getTitle();
        }

        if (ticket.getInitialProblem() != null && !ticket.getInitialProblem().equals(this.initialProblem)) {
            this.initialProblem = ticket.getInitialProblem();
        }

        if (ticket.getStatus() != null && !ticket.getStatus().equals(this.status)) {
            this.status = ticket.getStatus();
        }

        if (ticket.getCustomerInfo() != null) {
            if (this.customerInfo == null) {
                this.customerInfo = ticket.getCustomerInfo();
            } else {
                CustomerInfo updatedCustomerInfo = new CustomerInfo(
                        ticket.getCustomerInfo().getCustomerName() != null ? ticket.getCustomerInfo().getCustomerName() : this.customerInfo.getCustomerName(),
                        ticket.getCustomerInfo().getCompanyName() != null ? ticket.getCustomerInfo().getCompanyName() : this.customerInfo.getCompanyName(),
                        ticket.getCustomerInfo().getContactEmail() != null ? ticket.getCustomerInfo().getContactEmail() : this.customerInfo.getContactEmail()
                );
                if (!updatedCustomerInfo.equals(this.customerInfo)) {
                    this.customerInfo = updatedCustomerInfo;
                }
            }
        }

        if (ticket.getTriageAnalysis() != null) {
            if (this.triageAnalysis == null) {
                this.triageAnalysis = ticket.getTriageAnalysis();
            } else {
                TriageAnalysis updatedTriageAnalysis = new TriageAnalysis(
                        ticket.getTriageAnalysis().getSuggestedTitle() != null ? ticket.getTriageAnalysis().getSuggestedTitle() : this.triageAnalysis.getSuggestedTitle(),
                        ticket.getTriageAnalysis().getSummary() != null ? ticket.getTriageAnalysis().getSummary() : this.triageAnalysis.getSummary(),
                        ticket.getTriageAnalysis().getImpactAnalysis() != null ? ticket.getTriageAnalysis().getImpactAnalysis() : this.triageAnalysis.getImpactAnalysis(),
                        ticket.getTriageAnalysis().getUrgencyAnalysis() != null ? ticket.getTriageAnalysis().getUrgencyAnalysis() : this.triageAnalysis.getUrgencyAnalysis(),
                        ticket.getTriageAnalysis().getPredictedCategory() != null ? ticket.getTriageAnalysis().getPredictedCategory() : this.triageAnalysis.getPredictedCategory(),
                        ticket.getTriageAnalysis().getAssignedPriority() != null ? ticket.getTriageAnalysis().getAssignedPriority() : this.triageAnalysis.getAssignedPriority(),
                        ticket.getTriageAnalysis().getInitialApiResponse() != null ?
                                new InitialApiResponse(
                                        ticket.getTriageAnalysis().getInitialApiResponse().getMessageToCustomer() != null ?
                                                ticket.getTriageAnalysis().getInitialApiResponse().getMessageToCustomer() :
                                                this.triageAnalysis.getInitialApiResponse().getMessageToCustomer(),
                                        ticket.getTriageAnalysis().getInitialApiResponse().getEstimatedResolutionTime() != null ?
                                                ticket.getTriageAnalysis().getInitialApiResponse().getEstimatedResolutionTime() :
                                                this.triageAnalysis.getInitialApiResponse().getEstimatedResolutionTime()
                                ) : this.triageAnalysis.getInitialApiResponse(),
                        ticket.getTriageAnalysis().getSuggestedConsultantResponse() != null ? ticket.getTriageAnalysis().getSuggestedConsultantResponse() : this.triageAnalysis.getSuggestedConsultantResponse()
                );
                if (!updatedTriageAnalysis.equals(this.triageAnalysis)) {
                    this.triageAnalysis = updatedTriageAnalysis;
                }
            }
        }
    }
}
