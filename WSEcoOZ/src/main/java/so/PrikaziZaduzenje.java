/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import domen.OpstiDomenskiObjekat;
import domen.StavkaZahteva;
import domen.Zaduzenja;
import java.util.List;
import org.springframework.http.HttpStatus;

/**
 *
 * @author dzuli_c
 */
public class PrikaziZaduzenje extends SOAbstract {

    private final Zaduzenja zaduzenja;

    public PrikaziZaduzenje(Zaduzenja zaduzenja) {
        this.zaduzenja = zaduzenja;
    }

    @Override
    public void execute() {
        List<OpstiDomenskiObjekat> lista = session.createNativeQuery("SELECT * from zaduzenja where zaduzenjaID=" + zaduzenja.getZaduzenjaID() ).addEntity(Zaduzenja.class).list();
        objekat = lista.get(0);
        httpStatus = HttpStatus.OK;
    }

}
