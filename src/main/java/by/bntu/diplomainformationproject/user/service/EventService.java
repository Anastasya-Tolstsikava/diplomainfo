package by.bntu.diplomainformationproject.user.service;

import by.bntu.diplomainformationproject.user.dto.EventDto;

import java.util.List;

public interface EventService {
    List<EventDto> findAll();
}
