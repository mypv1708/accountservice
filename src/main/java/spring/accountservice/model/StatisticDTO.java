package spring.accountservice.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "statistic")
public class StatisticDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String message;
    private Date createdDate;
    private Boolean status;

    public Integer getId() {
        return id;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getStatus() {
        return status;
    }

    public StatisticDTO() {
    }

    public StatisticDTO(String message, Date createdDate) {
        super();
        this.message = message;
        this.createdDate = createdDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
