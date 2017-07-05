/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import domen.OpstiDomenskiObjekat;
import domen.Zaduzenja;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;

/**
 *
 * @author ciricj
 */
public class PretraziZaduzenja extends SOAbstract {

    Logger logger = Logger.getLogger(PretraziZaduzenja.class);
    Zaduzenja zaduzenja;

    public PretraziZaduzenja(OpstiDomenskiObjekat odo) {
        zaduzenja = (Zaduzenja) odo;
    }

    @Override
    public void execute() {
        String query = "SELECT * FROM zaduzenja z join zahtev za on z.zahtev_zahtevID=za.zahtevID join sluzba_transporta s on z.sluzba_PIB=s.PIB where s.PIB=" + zaduzenja.getSluzba().getPIB()+" AND stanje=false";
        if (zaduzenja != null) {
            if (zaduzenja.getDatum() != null) {
                query = "SELECT * FROM zaduzenja join zahtev za on z.zahtev_zahtevID=za.zahtevID join stavka_zahteva sz on za.zahtevID=sz.zahtev_zahtevID where datum=" + new java.sql.Date(zaduzenja.getDatum().getTime())+" AND stanje=false";
            }
            if (zaduzenja.getZahtev() != null) {
                if (zaduzenja.getZahtev().getDatum() != null) {
                    query = "SELECT * FROM zaduzenja z join zahtev za on z.zahtev_zahtevID=za.zahtevID where za.datum='" + zaduzenja.getZahtev().getDatum() + "' AND stanje=false";
                } else if (zaduzenja.getZahtev().getKorisnik() != null && !"".equals(zaduzenja.getZahtev().getKorisnik().getUser())) {
                    query = "SELECT * FROM zaduzenja z join zahtev za on z.zahtev_zahtevID=za.zahtevID join korisnik k on za.korisnik_brLicne=k.brLicne where k.user='" + zaduzenja.getZahtev().getKorisnik().getUser() + "' AND stanje=false";
                }
            }
        }
        listaObjekata = session.createNativeQuery(query).addEntity(Zaduzenja.class).list();
        httpStatus = HttpStatus.OK;

    }
}
