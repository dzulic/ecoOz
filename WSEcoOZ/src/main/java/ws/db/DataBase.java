/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.db;

import domen.Izvestaj;
import domen.Korisnik;
import domen.SluzbaTransporta;
import domen.StavkaIzvestaja;
import domen.StavkaZahteva;
import domen.Zaduzenja;
import domen.Zahtev;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StaleStateException;
import org.hibernate.cfg.Configuration;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import so.SOAbstract;

/**
 *
 * @author ciricj
 */
@Component
@Repository
public class DataBase extends AbstractDataBase {

    Logger logger = Logger.getLogger(DataBase.class);

    @Override
    public boolean dataBaseOperation(SOAbstract operation) {
        SessionFactory sessionFactory = new Configuration().addAnnotatedClass(Korisnik.class).addAnnotatedClass(Zahtev.class)
                .addAnnotatedClass(SluzbaTransporta.class).addAnnotatedClass(Izvestaj.class).addAnnotatedClass(StavkaIzvestaja.class)
                .addAnnotatedClass(StavkaZahteva.class).addAnnotatedClass(Zaduzenja.class).configure().buildSessionFactory();
        //try with resources - for any class that implements AutoClosable
        // no need for finally block to close the session
        try (Session session = sessionFactory.openSession()) {
            try {
                session.getTransaction().begin();
                operation.setSession(session);
                operation.execute();
                session.flush();
                session.getTransaction().commit();
                isCreated = true;
            } catch (IndexOutOfBoundsException ex) {
                session.getTransaction().rollback();
                operation.setHttpStatus(HttpStatus.NOT_FOUND);
                isCreated = false;
            } catch (StaleStateException sse) {
                session.getTransaction().rollback();
                operation.setHttpStatus(HttpStatus.NOT_MODIFIED);
            }
        }
        return isCreated;
    }

}
