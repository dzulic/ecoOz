/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import domen.Izvestaj;
import domen.Korisnik;
import domen.OpstiDomenskiObjekat;
import domen.SluzbaTransporta;
import domen.StavkaIzvestaja;
import domen.StavkaZahteva;
import domen.Zaduzenja;
import domen.Zahtev;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.http.HttpStatus;
import util.Constants;

/**
 *
 * @author ciricj
 */
public class IzmeniZahtev extends SOAbstract {

    List<StavkaZahteva> stavke;
    String tip = "";

    public IzmeniZahtev(List<OpstiDomenskiObjekat> obj, String tip) {
        stavke = new ArrayList<>();
        this.tip=tip;
        for (OpstiDomenskiObjekat opstiDomenskiObjekat : obj) {
            stavke.add((StavkaZahteva) opstiDomenskiObjekat);
        }

    }

    @Override
    public void execute() {
        int sum = 0;
        if (tip.equals(Constants.STAVKA_ZAHTEVA)) {
            for (StavkaZahteva stavkaZahteva : stavke) {
                session.createNativeQuery("update stavka_zahteva set kolicina="+ stavkaZahteva.getKolicina()+ " , materijal='"+stavkaZahteva.getMaterijal()+"' where redniBroj="+ stavkaZahteva.getRedniBroj()+" and zahtev_zahtevID="+stavkaZahteva.getZahtev().getZahtevID())
                        .executeUpdate();
            }
        } else {
            try {
                Zahtev found = (Zahtev) session.createNativeQuery("select * from zahtev where zahtevID=:ID")
                        .setParameter("ID", stavke.get(0).getZahtev().getZahtevID()).addEntity(Zahtev.class).list().get(0);
                for (StavkaZahteva s : found.getListaStavki()) {
                    sum += s.getKolicina();
                }
                session.getTransaction().commit();
                session.getTransaction().begin();

                session.createNativeQuery("update zahtev set ukupno=:sum where zahtevID=:ID")
                        .setParameter("sum", sum).
                        setParameter("ID", stavke.get(0).getZahtev().getZahtevID()).executeUpdate();
            } catch (Exception e) {

            }
        }
        httpStatus = HttpStatus.OK;

    }

}
