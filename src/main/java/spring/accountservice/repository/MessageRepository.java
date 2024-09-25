package spring.accountservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.accountservice.model.MessageDTO;

import java.util.List;

public interface MessageRepository extends JpaRepository<MessageDTO, Integer> {
    List<MessageDTO> findByStatus(boolean status);
}
