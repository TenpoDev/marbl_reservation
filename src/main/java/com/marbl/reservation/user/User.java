package com.marbl.reservation.user;

import com.marbl.reservation.registry.Registry;
import com.marbl.reservation.event.Reservation;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "app_user")
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = -3423199905138220101L;

    @Id
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    private Long userId;
    private String userName;
    private String password;
    private AuthorizationLevel authLvl;

    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.REMOVE}, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "registry_Id",referencedColumnName = "registryId")
    private Registry registry;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private List<Reservation> reservations;

}
