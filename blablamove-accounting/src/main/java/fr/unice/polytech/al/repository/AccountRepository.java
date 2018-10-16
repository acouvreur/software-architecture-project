package fr.unice.polytech.al.repository;

import fr.unice.polytech.al.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
