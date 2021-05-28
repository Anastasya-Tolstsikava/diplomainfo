package by.bntu.diplomainformationproject.user.repository;

import by.bntu.diplomainformationproject.user.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m FROM Message m WHERE m.isCalendarEvent = true")
    List<Message> findAllEvents();
}
