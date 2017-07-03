/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import domen.Izvestaj;
import domen.OpstiDomenskiObjekat;
import java.util.List;
import org.springframework.http.HttpStatus;

/**
 *
 * @author dzuli_c
 */
public class PrikaziIzvestaj extends SOAbstract {

    private final Izvestaj izestaj;

    public PrikaziIzvestaj(Izvestaj izvestaj) {
        this.izestaj = izvestaj;
    }

    @Override
    public void execute() {
        List<OpstiDomenskiObjekat> lista = session.createNativeQuery("SELECT * from izvestaj where ID=" + izestaj.getID()).addEntity(Izvestaj.class).list();
        objekat = lista.get(0);
        httpStatus = HttpStatus.OK;
    }

}
