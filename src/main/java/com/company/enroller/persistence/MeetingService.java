package com.company.enroller.persistence;

import com.company.enroller.model.Meeting;
import com.company.enroller.model.Participant;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.Collection;


@Component("meetingService")
public class MeetingService {

	Session session;

	public MeetingService() {
		session = DatabaseConnector.getInstance().getSession();
	}


	// Pobieranie wszystkich spotkań
    public Collection<Meeting> getAll() {
        String hql = "FROM Meeting";
        Query query = this.session.createQuery(hql);
        return query.list();
    }

    // Pobieranie pojedynczego spotkania po ID
    public Meeting findById(long id) {
        return session.get(Meeting.class, id);
    }

    // Dodawanie nowego spotkania
    public void add(Meeting meeting) {
        session.beginTransaction();
        session.save(meeting);
        session.getTransaction().commit();
    }

    // Usuwanie spotkania
    public void delete(Meeting meeting) {
        session.beginTransaction();
        session.delete(meeting);
        session.getTransaction().commit();
    }

    // Aktualizowanie spotkania
    public void update(Meeting meeting) {
        session.beginTransaction();
        session.update(meeting);
        session.getTransaction().commit();
    }

    // Dodawanie uczestnika do spotkania
    public void addParticipant(Meeting meeting, Participant participant) {
        session.beginTransaction();
        meeting.getParticipants().add(participant);
        session.update(meeting);
        session.getTransaction().commit();
    }

    // Usuwanie uczestnika ze spotkania
    public void removeParticipant(Meeting meeting, Participant participant) {
        session.beginTransaction();
        meeting.getParticipants().remove(participant);
        session.update(meeting);
        session.getTransaction().commit();
    }

    // Pobieranie uczestników spotkania
    public Collection<Participant> getParticipants(Meeting meeting) {
        return meeting.getParticipants();
    }

	/*
	public Collection<Meeting> getAll() {
		String hql = "FROM Meeting";
		Query query = this.session.createQuery(hql);
		return query.list();
	}

	// Pobieranie pojedynczego spotkania po ID
	public Meeting findById(long id) {
		return session.get(Meeting.class, id);
	}

	// Dodawanie nowego spotkania
	public void add(Meeting meeting) {
		session.beginTransaction();
		session.save(meeting);
		session.getTransaction().commit();
	}

	// Usuwanie spotkania
	public void delete(Meeting meeting) {
		session.beginTransaction();
		session.delete(meeting);
		session.getTransaction().commit();
	}

	// Aktualizowanie spotkania
	public void update(Meeting meeting) {
		session.beginTransaction();
		session.update(meeting);
		session.getTransaction().commit();
	}
	/*
	 */
}
