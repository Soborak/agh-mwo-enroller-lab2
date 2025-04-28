package com.company.enroller.controllers;

import com.company.enroller.model.Meeting;
import com.company.enroller.persistence.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.Collection;

@RestController
@RequestMapping("/meetings")
public class MeetingRestController {

    @Autowired
    MeetingService meetingService;

    // Endpoint do pobierania wszystkich spotkań
    @GetMapping
    public Collection<Meeting> getMeetings() {
        return meetingService.getAll();
    }

    // Endpoint do pobierania pojedynczego spotkania po id
    @GetMapping("/{id}")
    public ResponseEntity<Meeting> getMeeting(@PathVariable("id") long id) {
        Meeting meeting = meetingService.findById(id);
        if (meeting == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(meeting);
    }

    //Endpoint do dodawania spotkania
    @PostMapping
    public ResponseEntity<Meeting> addMeeting(@RequestBody Meeting meeting) {
        meetingService.add(meeting);
        return ResponseEntity.ok(meeting);
    }

    //Endpoint do usuwania spotkania
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMeeting(@PathVariable("id") long id) {
        Meeting meeting = meetingService.findById(id);
        if (meeting == null) {
            return ResponseEntity.notFound().build();
        }
        meetingService.delete(meeting);
        return ResponseEntity.ok().build();
    }

    // Endpoint do edytowania spotkania po id
    @PutMapping("/{id}")
    public ResponseEntity<Meeting> updateMeeting(@PathVariable("id") long id, @RequestBody Meeting updatedMeeting) {
        Meeting meeting = meetingService.findById(id);
        if (meeting == null) {
            return ResponseEntity.notFound().build();
        }
        meeting.setTitle(updatedMeeting.getTitle());
        meeting.setDescription(updatedMeeting.getDescription());
        meeting.setDate(updatedMeeting.getDate());
        meetingService.update(meeting);
        return ResponseEntity.ok(meeting);
    }
    
}