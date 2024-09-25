package spring.accountservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.accountservice.model.AccountDTO;

public interface AccountRepository extends JpaRepository<AccountDTO, Integer> {
}
