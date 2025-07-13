package br.ufrn.imd.nexus.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InitialApiResponse {
    private String messageToCustomer;
    private String estimatedResolutionTime;
}
