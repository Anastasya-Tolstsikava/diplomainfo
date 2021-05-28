package by.bntu.diplomainformationproject.user.dto;

import by.bntu.diplomainformationproject.user.entity.Teacher;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventDto {

    private Long id;

    private LocalDateTime creationDate;

    private Boolean isCalendarEvent;

    private LocalDateTime eventDay;

    private String text;

    private Teacher creator;
}
