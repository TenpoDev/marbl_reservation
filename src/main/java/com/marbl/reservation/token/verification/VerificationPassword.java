package com.marbl.reservation.token.verification;

import com.marbl.reservation.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VerificationPassword implements Serializable {

    @Serial
    private static final long serialVersionUID = -4043306649114581896L;

    //ExpirationTime 10 min
    private static final int EXPIRATION_TIME = 30;

    @Id
    @SequenceGenerator(name = "verification_password_sequence", sequenceName = "verification_password_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "verification_password_sequence")
    @Column(name = "id", nullable = false)
    private Long id;

    private String passwordHash;

    private Date expirationTime;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_Id", referencedColumnName = "userId", nullable = false, foreignKey = @ForeignKey(name = "FK_PASSWORD_VERIFY_TOKEN"))
    private User user;

    public VerificationPassword(String passwordHash, User user) {
        super();
        this.user = user;
        this.passwordHash = passwordHash;
        this.expirationTime = expirationTime();
    }

    public VerificationPassword(String passwordHash) {
        super();
        this.passwordHash = passwordHash;
        this.expirationTime = expirationTime();
    }

    public VerificationPassword(User user) {
        super();
        this.user = user;
        this.expirationTime = expirationTime();
    }

    private Date expirationTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, VerificationPassword.EXPIRATION_TIME);
        return new Date(calendar.getTime().getTime());
    }
}
