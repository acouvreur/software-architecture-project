package fr.unice.polytech.al.repository;

import fr.unice.polytech.al.model.Billing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingRepository extends JpaRepository<Billing, Long>  {


}
