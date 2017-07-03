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
public class PretraziZahteve extends SOAbstract {

    Logger logger = Logger.getLogger(PretraziZahteve.class);
    StavkaZahteva stavkaZahteva;
    Zahtev zahtev;

    public PretraziZahteve(OpstiDomenskiObjekat odo) {
        listaObjekata = new ArrayList<>();
        stavkaZahteva = (StavkaZahteva) odo;
        zahtev = stavkaZahteva.getZahtev();
    }

    @Override
    public void execute() {
        String query = "";
        if (zahtev != null) {
            query = "SELECT * FROM zahtev where korisnik_brLicne=" + zahtev.getKorisnik().getBrLicne();

            if (zahtev.getDatum() != null) {
                query = "SELECT * FROM zahtev za where datum=" + new java.sql.Date(zahtev.getDatum().getTime());
            }
            if (zahtev.getUkupno() != 0) {
                query = "SELECT * FROM zahtev za where ukupno=" + zahtev.getUkupno();
            }

        }
        if (stavkaZahteva != null) {
            if (stavkaZahteva.getMaterijal() != null && !"".equals(stavkaZahteva.getMaterijal()) && !"izaberi".equals(stavkaZahteva.getMaterijal().toLowerCase()) && !"select".equals(stavkaZahteva.getMaterijal().toLowerCase())) {
                query = "SELECT * FROM zahtev za join stavka_zahteva sz on sz.zahtev_zahtevID=za.zahtevID where sz.materijal='" + stavkaZahteva.getMaterijal() + "'";
            }
        }
        List<Zahtev> list = session.createNativeQuery(query).addEntity(Zahtev.class).list();
        for (Zahtev z : list) {
            for (StavkaZahteva sz : z.getListaStavki()) {
                StavkaZahteva stavka = new StavkaZahteva(z, sz.getRedniBroj(), sz.getKolicina(), sz.getMaterijal());
                listaObjekata.add(stavka);
            }
        }
        httpStatus = HttpStatus.OK;

    }

}
