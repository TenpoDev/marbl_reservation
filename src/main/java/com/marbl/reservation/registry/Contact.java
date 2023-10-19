package com.marbl.reservation.registry;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@AttributeOverride(name = "email", column = @Column(name = "user_email"))
@AttributeOverride(name = "mobile", column = @Column(name = "user_mobile"))
@AttributeOverride(name = "phone", column = @Column(name = "user_phone"))
public class Contact implements Serializable {

    @Serial
    private static final long serialVersionUID = 3938387523051958311L;

    private String email;
    private Long mobile;
    private  Long phone;
}
