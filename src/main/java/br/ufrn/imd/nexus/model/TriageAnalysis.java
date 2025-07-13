package br.ufrn.imd.nexus.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TriageAnalysis {
    private String suggestedTitle;
    private String summary;
    private String impactAnalysis;
    private String urgencyAnalysis;
    @Enumerated(EnumType.STRING) private ProblemCategory predictedCategory;
    @Enumerated(EnumType.STRING) private Priority assignedPriority;
    @Embedded private InitialApiResponse  initialApiResponse;
    private String suggestedConsultantResponse;
}