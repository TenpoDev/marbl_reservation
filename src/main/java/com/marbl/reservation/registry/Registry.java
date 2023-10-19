package com.marbl.reservation.registry;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Registry implements Serializable {

    @Serial
    private static final long serialVersionUID = -7780202900850509446L;

    @Id
    @SequenceGenerator(name = "registry_sequence", sequenceName = "registry_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "registry_sequence")
    private Long registryId;

    private String userFirstName;
    private String userLastName;
    private Long userAddressId;
    @Embedded
    private Contact contact;
    private LocalDate userBirth;
    private GenderOption userGender;


}
