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
    String tip = "";
    int id = 0;

    public ObrisiZahtev(OpstiDomenskiObjekat odo, String tip) {
        this.tip = tip;
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
        double sum = 0;

        if (stavkaZahteva != null) {
            if (tip.equals("obrisi")) {
                session.createNativeQuery("delete from stavka_zahteva where redniBroj=" + stavkaZahteva.getRedniBroj() + " and zahtev_zahtevID=" + stavkaZahteva.getZahtev().getZahtevID())
                        .executeUpdate();

            } else {
                Zahtev found = (Zahtev) session.createNativeQuery("select * from zahtev where zahtevID=" + stavkaZahteva.getZahtev().getZahtevID())
                        .addEntity(Zahtev.class).list().get(0);
                for (StavkaZahteva s : found.getListaStavki()) {
                    sum += s.getKolicina();
                }
                session.createNativeQuery("update zahtev set ukupno=" + sum + " where zahtevID=" + stavkaZahteva.getZahtev().getZahtevID())
                        .executeUpdate();
            }
        } else if (zahtev != null) {
            if (tip.equals("obrisi")) {

                session.createNativeQuery("delete from stavka_zahteva zahtev_zahtevID=:ID")
                        .setParameter("ID", zahtev.getZahtevID()).executeUpdate();

                session.createNativeQuery("delete from zaduzenja where zahtev_zahtevID=:ID")
                        .setParameter("ID", stavkaZahteva.getZahtev().getZahtevID()).executeUpdate();

                session.delete(zahtev);
            } else {
                Zahtev found = (Zahtev) session.createNativeQuery("select * from zahtev where zahtevID=" + zahtev.getZahtevID())
                        .addEntity(Zahtev.class).list().get(0);
                for (StavkaZahteva s : found.getListaStavki()) {
                    sum += s.getKolicina();
                }
                session.createNativeQuery("update zahtev set ukupno=" + sum + " where zahtevID=" + zahtev.getZahtevID())
                        .executeUpdate();
            }

        }

        httpStatus = HttpStatus.OK;

    }

}
