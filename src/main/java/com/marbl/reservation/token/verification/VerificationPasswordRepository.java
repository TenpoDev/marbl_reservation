package com.marbl.reservation.token.verification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationPasswordRepository extends JpaRepository<VerificationPassword,Long> {
}
