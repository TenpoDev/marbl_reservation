package com.marbl.reservation.token.verification;

import com.marbl.reservation.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VerificationToken implements Serializable {

    @Serial
    private static final long serialVersionUID = -2358285761204745075L;

    //ExpirationTime 10 min
    private static final int EXPIRATION_TIME = 10;


    @Id
    @SequenceGenerator(name = "verification_token_sequence", sequenceName = "verification_token_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "verification_token_sequence")
    @Column(name = "id", nullable = false)
    private Long id;

    private String token;

    private Date expirationTime;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_Id", referencedColumnName = "userId", nullable = false, foreignKey = @ForeignKey(name = "FK_USER_VERIFY_TOKEN"))
    private User user;

    public VerificationToken(String token, User user) {
        super();
        this.user = user;
        this.token = token;
        this.expirationTime = expirationTime();
    }

    public VerificationToken(String token) {
        super();
        this.token = token;
        this.expirationTime = expirationTime();
    }

    private Date expirationTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, VerificationToken.EXPIRATION_TIME);
        return new Date(calendar.getTime().getTime());
    }

}
