package by.bntu.diplomainformationproject.user.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import java.time.LocalDateTime;

@Entity
@Data
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_ID_SEQ")
    @SequenceGenerator(name = "USER_ID_SEQ", sequenceName = "USER_ID_SEQ")
    private Long id;

    @CreationTimestamp
    private LocalDateTime creationDate;

    private Boolean isCalendarEvent;

    private LocalDateTime eventDay;

    private String text;

    @ManyToOne
    private Teacher creator;
}
