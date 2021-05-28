package by.bntu.diplomainformationproject.user.controller;

import by.bntu.diplomainformationproject.user.dto.EventDto;
import by.bntu.diplomainformationproject.user.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diplomainfo/events")
public class EventController {

    private final EventService eventService;

    @GetMapping
    public ResponseEntity<List<EventDto>> findAll() {
        List<EventDto> allEvents = eventService.findAll();
        return ResponseEntity.ok(allEvents);
    }
}
