/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import domen.Korisnik;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;

/**
 *
 * @author ciricj
 */
public class KreirajKorisnika extends SOAbstract {

    Boolean isCreated;
    Korisnik korisnik;
    Logger logger = Logger.getLogger(KreirajKorisnika.class);

    public KreirajKorisnika(Korisnik korisnik) {
        isCreated = false;
        this.korisnik = korisnik;
    }

    public Boolean getIsCreated() {
        return isCreated;
    }

    @Override
    public void execute() {
        if (korisnik != null) {
            session.save(korisnik);
            objekat = korisnik;
            isCreated = true;
            httpStatus = HttpStatus.OK;
        }
    }

}
