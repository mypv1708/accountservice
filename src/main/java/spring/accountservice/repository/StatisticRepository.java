package spring.accountservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.accountservice.model.StatisticDTO;

import java.util.List;

public interface StatisticRepository extends JpaRepository<StatisticDTO, Integer> {
    List<StatisticDTO> findByStatus(boolean status);

}
