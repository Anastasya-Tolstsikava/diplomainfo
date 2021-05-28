package by.bntu.diplomainformationproject.user.service.impl;

import by.bntu.diplomainformationproject.user.dto.EventDto;
import by.bntu.diplomainformationproject.user.entity.Message;
import by.bntu.diplomainformationproject.user.repository.MessageRepository;
import by.bntu.diplomainformationproject.user.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final MessageRepository messageRepository;

    @Override
    public List<EventDto> findAll() {
        return messageRepository.findAllEvents()
                              .stream().map(this::entityToDto).collect(Collectors.toList());

    }

    private EventDto entityToDto(Message message) {
        EventDto eventDto = new EventDto();
        eventDto.setId(message.getId());
        eventDto.setCreator(message.getCreator());
        eventDto.setEventDay(message.getEventDay());
        eventDto.setIsCalendarEvent(message.getIsCalendarEvent());
        eventDto.setText(message.getText());
        return eventDto;
    }
}
