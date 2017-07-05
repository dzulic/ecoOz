/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import domen.OpstiDomenskiObjekat;
import domen.StavkaZahteva;
import domen.Zahtev;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;

/**
 *
 * @author ciricj
 */
public class ObrisiZahtev extends SOAbstract {

    Boolean isDeleted;
    Zahtev zahtev;
    Logger logger = Logger.getLogger(KreirajKorisnika.class);
    StavkaZahteva stavkaZahteva;

    public ObrisiZahtev(OpstiDomenskiObjekat odo) {
        if (odo instanceof Zahtev) {
            zahtev = (Zahtev) odo;
            stavkaZahteva = null;
        } else {
            stavkaZahteva = (StavkaZahteva) odo;
            zahtev = null;
        }
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    @Override
    public void execute() {
        if (stavkaZahteva != null) {
            session.createNativeQuery("delete from stavka_zahteva where redniBroj=:rb and zahtev_zahtevID=:ID")
                    .setParameter("rb", stavkaZahteva.getRedniBroj())
                    .setParameter("ID", stavkaZahteva.getZahtev().getZahtevID()).executeUpdate();

            session.createNativeQuery("delete from zaduzenja where zahtev_zahtevID=:ID")
                    .setParameter("ID", stavkaZahteva.getZahtev().getZahtevID()).executeUpdate();
        } else if (zahtev != null) {
            session.delete(zahtev);
            session.createNativeQuery("delete from zaduzenja where zahtev_zahtevID=:ID")
                    .setParameter("ID", zahtev.getZahtevID()).executeUpdate();
        }
        httpStatus = HttpStatus.OK;

    }

}
