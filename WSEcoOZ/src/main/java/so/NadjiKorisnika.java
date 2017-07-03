/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import domen.Korisnik;
import domen.OpstiDomenskiObjekat;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;

/**
 *
 * @author ciricj
 */
public class NadjiKorisnika extends SOAbstract {

    Logger logger = Logger.getLogger(NadjiKorisnika.class);
    Korisnik korisnik;

    public NadjiKorisnika(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    @Override
    public void execute() {
        if (korisnik != null) {
            List list = session.createNativeQuery(
                    "select * from korisnik where user='" + korisnik.getUser() + "' AND pass='" + korisnik.getPass() + "'").addEntity(Korisnik.class).list();
            objekat = (OpstiDomenskiObjekat) list.get(0);
            httpStatus = HttpStatus.OK;

        }
    }

}
