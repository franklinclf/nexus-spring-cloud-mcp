package br.ufrn.imd.nexus.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerInfo {

    private String customerName;
    private String companyName;
    private String contactEmail;

}