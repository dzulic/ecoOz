/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import domen.OpstiDomenskiObjekat;
import domen.StavkaZahteva;
import domen.Zahtev;
import java.util.List;
import org.springframework.http.HttpStatus;

/**
 *
 * @author ciricj
 */
public class PrikaziZahtev extends SOAbstract {

    Zahtev zahtev;

    public PrikaziZahtev(OpstiDomenskiObjekat odo) {
        zahtev = (Zahtev) odo;
    }

    @Override
    public void execute() {
            List<OpstiDomenskiObjekat> lista = session.createNativeQuery("SELECT * from stavka_zahteva sz join zahtev z on z.zahtevID=sz.zahtev_zahtevID where zahtevID=" + zahtev.getZahtevID()).addEntity(StavkaZahteva.class).list();
            listaObjekata = lista;
            httpStatus = HttpStatus.OK;
    
    }

}
