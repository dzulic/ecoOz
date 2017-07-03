/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import domen.OpstiDomenskiObjekat;
import domen.SluzbaTransporta;
import domen.StavkaZahteva;
import domen.Zaduzenja;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.http.HttpStatus;

/**
 *
 * @author ciricj
 */
public class ZapamtiZahtev extends SOAbstract {

    List<StavkaZahteva> stavke;

    public ZapamtiZahtev(List<OpstiDomenskiObjekat> odo) {
        stavke = new ArrayList<>();
        for (OpstiDomenskiObjekat o : odo) {
            stavke.add((StavkaZahteva) o);
        }
    }

    @Override
    public void execute() {
        if (stavke != null) {
            session.update(stavke.get(0).getZahtev());
            for (StavkaZahteva stavkaZahteva : stavke) {
                session.save(stavkaZahteva);
            }
            Zaduzenja zaduzenja = new Zaduzenja();
            List list = session.createNativeQuery("SELECT zaduzenjaID FROM zaduzenja").list();
            int max = 1000;
            for (Object object : list) {
                if (max < Integer.parseInt(object.toString())) {
                    max = Integer.parseInt(object.toString());
                }
            }
            zaduzenja.setZaduzenjaID(max + 1);
            zaduzenja.setDatum(new Date());
            zaduzenja.setZahtev(stavke.get(0).getZahtev());

            if (stavke.get(0).getZahtev().getKorisnik().getGrad().equals("Nis")) {
                SluzbaTransporta sluzbaTransporta = (SluzbaTransporta) session.createNativeQuery("select * from sluzba_transporta where PIB=1").addEntity(SluzbaTransporta.class).list().get(0);
                zaduzenja.setSluzba(sluzbaTransporta);
            } else {
                SluzbaTransporta sluzbaTransporta1 = (SluzbaTransporta) session.createNativeQuery("select * from sluzba_transporta where PIB=2").addEntity(SluzbaTransporta.class).list().get(0);
                zaduzenja.setSluzba(sluzbaTransporta1);
            }
            session.save(zaduzenja);
            httpStatus = HttpStatus.OK;
        }
    }

}
