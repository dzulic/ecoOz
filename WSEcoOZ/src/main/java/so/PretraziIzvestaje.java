/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import domen.Izvestaj;
import domen.OpstiDomenskiObjekat;
import domen.StavkaIzvestaja;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;

/**
 *
 * @author ciricj
 */
public class PretraziIzvestaje extends SOAbstract {

    Logger logger = Logger.getLogger(PretraziIzvestaje.class);
    Izvestaj izvestaj;
    StavkaIzvestaja stavkaIzvestaj;

    public PretraziIzvestaje(OpstiDomenskiObjekat odo) {
        izvestaj = (Izvestaj) odo;
    }

    @Override
    public void execute() {
        stavkaIzvestaj = izvestaj.getListaStavki().get(0);
        
        if (izvestaj.getDatum() != null) {
            listaObjekata = session.createNativeQuery("select * from izvestaj where sluzba_PIB='" + izvestaj.getSluzba().getPIB() + "' AND i.datum='" + izvestaj.getDatum() + "'").addEntity(Izvestaj.class).list();
        }
        if (stavkaIzvestaj != null) {
            if (stavkaIzvestaj.getMaterijal() != null && !"".equals(stavkaIzvestaj.getMaterijal()) && !"select".equals(stavkaIzvestaj.getMaterijal())) {
                listaObjekata = session.createNativeQuery("select * from stavka_izvestaja si left join izvestaj i on i.ID=si.izvestaj_ID where sluzba_PIB='" + izvestaj.getSluzba().getPIB() + "' AND si.materijal='" + stavkaIzvestaj.getMaterijal() + "'").addEntity(Izvestaj.class).list();
                httpStatus = HttpStatus.OK;
                return;
            } else if (stavkaIzvestaj.getKorisnik() != null) {
                if (stavkaIzvestaj.getKorisnik().getUser() != null && !"".equals(stavkaIzvestaj.getKorisnik().getUser())) {
                    listaObjekata = session.createNativeQuery("select * from stavka_izvestaja si left join izvestaj i on i.ID=si.izvestaj_ID join korisnik k on si.korisnik_BrLicne=k.brLicne where sluzba_PIB='" + izvestaj.getSluzba().getPIB() + "' AND k.user='" + stavkaIzvestaj.getKorisnik().getUser() + "'").addEntity(Izvestaj.class).list();
                    httpStatus = HttpStatus.OK;
                    return;
                }
            }
        }

        listaObjekata = session.createNativeQuery("select * from izvestaj where sluzba_PIB=" + izvestaj.getSluzba().getPIB()).addEntity(Izvestaj.class).list();
        httpStatus = HttpStatus.OK;
    }
}
