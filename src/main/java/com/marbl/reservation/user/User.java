package com.marbl.reservation.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.marbl.reservation.booking.Booking;
import com.marbl.reservation.registry.Registry;
import com.marbl.reservation.token.Token;
import com.marbl.reservation.token.verification.VerificationPassword;
import com.marbl.reservation.token.verification.VerificationToken;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"userName"}))
public class User implements UserDetails,Serializable {

    @Serial
    private static final long serialVersionUID = -3423199905138220101L;

    @Id
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    private Long userId;

    @Email(regexp=".+@.+\\..+", flags = Pattern.Flag.CASE_INSENSITIVE)
    private String username;
    @Column(length = 60)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean enabled = false;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "registry_Id", referencedColumnName = "registryId")
    private Registry registry;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private List<Booking> bookings;

    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
    private List<Token> tokens;

    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
    private List<VerificationToken> verificationToken;

    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
    private List<VerificationPassword> verificationPasswords;

    public void addVerificationToken(VerificationToken verificationToken) {
        if (this.verificationToken == null) {
            this.verificationToken = new ArrayList<>();
        }
        verificationToken.setUser(this);
        this.verificationToken.add(verificationToken);
    }

       public void addVerificationPassword(VerificationPassword verificationPassword) {
        if (this.verificationPasswords == null) {
            this.verificationPasswords = new ArrayList<>();
        }
        verificationPassword.setUser(this);
        this.verificationPasswords.add(verificationPassword);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled(){
        return enabled;
    }
}
