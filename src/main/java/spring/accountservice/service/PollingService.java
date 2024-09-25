package spring.accountservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import spring.accountservice.model.MessageDTO;
import spring.accountservice.model.StatisticDTO;
import spring.accountservice.repository.MessageRepository;
import spring.accountservice.repository.StatisticRepository;

import java.util.List;

@Component
public class PollingService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    StatisticRepository statisticRepository;

    @Scheduled(fixedDelay = 1000)
    public void producer() {

        List<MessageDTO> messageDTOS = messageRepository.findByStatus(false);
        for (MessageDTO messageDTO : messageDTOS) {
            kafkaTemplate.send("notification", messageDTO)
                    .whenComplete((result, ex) -> {
                        if (ex == null) {
                            logger.info("BROKE notification: Message sent successfully with id = " + result.getRecordMetadata().offset());
                            messageDTO.setStatus(true);
                            messageRepository.save(messageDTO);
                        } else {
                            logger.error("FAIL ", ex);
                        }
                    });
        }

        List<StatisticDTO> statisticDTOS = statisticRepository.findByStatus(false);
        for (StatisticDTO statisticDTO : statisticDTOS) {
            kafkaTemplate.send("statistic", statisticDTO)
                    .whenComplete((result, ex) -> {
                        if (ex == null) {
                            logger.info("BROKE statistic: Message sent successfully with id = " + result.getRecordMetadata().offset());
                            statisticDTO.setStatus(true);
                            statisticRepository.save(statisticDTO);
                        } else {
                            logger.error("FAIL ", ex);
                        }
                    });
        }
    }
    @Scheduled(fixedDelay = 60000)
    public void delete() {
        List<MessageDTO> messageDTOS = messageRepository.findByStatus(true);
        messageRepository.deleteAllInBatch(messageDTOS);

        List<StatisticDTO> statisticDTOS = statisticRepository.findByStatus(true);
        statisticRepository.deleteAllInBatch(statisticDTOS);

    }
}
