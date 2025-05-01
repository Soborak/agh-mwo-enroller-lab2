package com.company.enroller.persistence;

import com.company.enroller.model.Participant;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Collection;

@Component("participantService")
public class ParticipantService {

    private DatabaseConnector connector;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ParticipantService() {
        connector = DatabaseConnector.getInstance();
    }

    public Collection<Participant> getAll(String sortBy, String sortOrder, String key) {
        String hql = "FROM Participant";
        Query query = connector.getSession().createQuery(hql);
        return query.list();
    }

    public Participant findByLogin(String login) {
        return connector.getSession().get(Participant.class, login);
    }

    public Participant add(Participant participant) {
        Transaction transaction = connector.getSession().beginTransaction();
        connector.getSession().save(participant);
        transaction.commit();
        return participant;
    }

    public void registerParticipant(Participant participant) {
        String encodedPassword = passwordEncoder.encode(participant.getPassword());
        participant.setPassword(encodedPassword);
        add(participant); // dodaj uczestnika do bazy
    }

    public void update(Participant participant) {
        Transaction transaction = connector.getSession().beginTransaction();
        connector.getSession().merge(participant);
        transaction.commit();
    }

    public void delete(Participant participant) {
        Transaction transaction = connector.getSession().beginTransaction();
        connector.getSession().delete(participant);
        transaction.commit();
    }

    // Jeśli chcesz mieć drugą wersję getAll — OK, tylko popraw obsługę key
    public Collection<Participant> getAll(String sortBy, String sortOrder) {
        String hql = "FROM Participant";
        // Możesz dodać tutaj obsługę sortowania, jeśli potrzebujesz
        if ("login".equalsIgnoreCase(sortBy)) {
            hql += " ORDER BY login";
            if ("DESC".equalsIgnoreCase(sortOrder)) {
                hql += " DESC";
            } else {
                hql += " ASC";
            }
        }
        Query query = connector.getSession().createQuery(hql);
        return query.list();
    }
}
