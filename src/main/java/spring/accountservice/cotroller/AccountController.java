package spring.accountservice.cotroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.accountservice.model.AccountDTO;
import spring.accountservice.model.MessageDTO;
import spring.accountservice.model.StatisticDTO;
import spring.accountservice.repository.AccountRepository;
import spring.accountservice.repository.MessageRepository;
import spring.accountservice.repository.StatisticRepository;

import java.util.Date;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    StatisticRepository statisticRepository;

    @Autowired
    MessageRepository messageRepository;

    @PostMapping("/new")
    public AccountDTO create(@RequestBody AccountDTO accountDTO) {
        StatisticDTO statisticDTO = new StatisticDTO("Account " + accountDTO.getEmail() + " is created", new Date());
        statisticDTO.setStatus(false);

        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setTo(accountDTO.getEmail());
        messageDTO.setToName(accountDTO.getName());
        messageDTO.setSubject("Kafka Spring Send Mail");
        messageDTO.setContent("Welcome Binh Duong Vo Tan");
        messageDTO.setStatus(false);

        accountRepository.save(accountDTO);
        statisticRepository.save(statisticDTO);
        messageRepository.save(messageDTO);

//        for (int i = 0; i < 100; i++) {
//            kafkaTemplate.send("notification", messageDTO)
//                    .whenComplete((result, ex) -> {
//                        if (ex == null) {
//                            System.out.println("Message sent successfully with id = " + result.getRecordMetadata().offset());
//                        } else {
//                            ex.printStackTrace();
//                        }
//                    });
//        }
//
//        kafkaTemplate.send("statistic", statisticDTO);

        return accountDTO;
    }
}
