/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import domen.Izvestaj;
import domen.Korisnik;
import domen.OpstiDomenskiObjekat;
import domen.StavkaIzvestaja;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;

/**
 *
 * @author ciricj
 */
public class ZapamtiIzvestaj extends SOAbstract {

    List<Izvestaj> iz;

    public ZapamtiIzvestaj(List<OpstiDomenskiObjekat> odo) {
        iz = new ArrayList<>();
        for (OpstiDomenskiObjekat o : odo) {
            iz.add((Izvestaj) o);
        }

    }

    @Override
    public void execute() {
        if (iz != null) {
            for (StavkaIzvestaja stavkaIzvestaja : iz.get(0).getListaStavki()) {
                stavkaIzvestaja.setIzvestaj(iz.get(0));
                List list = session.createNativeQuery(
                        "select * from korisnik where user='" + stavkaIzvestaja.getKorisnik().getUser() + "'").addEntity(Korisnik.class).list();
                stavkaIzvestaja.setKorisnik((Korisnik) list.get(0));
                if (stavkaIzvestaja.getZaduzenja() != null) {
                    session.createNativeQuery("UPDATE zaduzenja SET stanje = true WHERE zaduzenjaID=" + stavkaIzvestaja.getZaduzenja().getZaduzenjaID()).executeUpdate();
                    stavkaIzvestaja.getZaduzenja().setZahtev(null);

                }
                session.save(stavkaIzvestaja);
                httpStatus = HttpStatus.OK;
            }
        }
    }

}
