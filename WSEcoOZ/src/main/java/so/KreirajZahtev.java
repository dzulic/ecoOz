/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import domen.Zahtev;
import java.util.List;
import org.springframework.http.HttpStatus;

/**
 *
 * @author ciricj
 */
public class KreirajZahtev extends SOAbstract {

    Zahtev zahtev;
    private boolean isCreated;

    public KreirajZahtev(Zahtev zahtev) {
        this.zahtev = zahtev;
    }

    @Override
    public void execute() {
        if (zahtev != null) {
            if (zahtev.getZahtevID() == 0) {
                List list = session.createNativeQuery("SELECT zahtevID FROM zahtev").list();
                int max = 1;
                for (Object object : list) {
                    if (max < Integer.parseInt(object.toString())) {
                        max = Integer.parseInt(object.toString());
                    }
                }
                zahtev.setZahtevID(max + 1);
                session.save(zahtev);
            }
            objekat = zahtev;
            zahtev.setListaStavki(null);
            isCreated = true;
            httpStatus = HttpStatus.OK;

        }
    }
}
