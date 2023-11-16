package com.marbl.reservation.registration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 6481649745583500043L;

    private String email;
    private Long mobile;
    private  Long phone;

}
