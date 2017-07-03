/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import domen.OpstiDomenskiObjekat;
import domen.StavkaZahteva;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;

/**
 *
 * @author ciricj
 */
public class IzmeniZahtev extends SOAbstract {

    List<StavkaZahteva> stavke;

    public IzmeniZahtev(List<OpstiDomenskiObjekat> obj) {
        stavke = new ArrayList<>();
        for (OpstiDomenskiObjekat opstiDomenskiObjekat : obj) {
            stavke.add((StavkaZahteva) opstiDomenskiObjekat);
        }
    }

    @Override
    public void execute() {
        for (StavkaZahteva stavkaZahteva : stavke) {
            session.createNativeQuery("update stavka_zahteva set kolicina=:kol and materijal=:mat where redniBroj=:rb and zahtev_zahtevID=:ID")
                    .setParameter("kol", stavkaZahteva.getKolicina())
                    .setParameter("mat", stavkaZahteva.getMaterijal())
                    .setParameter("rb", stavkaZahteva.getRedniBroj())
                    .setParameter("ID", stavkaZahteva.getZahtev().getZahtevID()).executeUpdate();
        }
        session.createNativeQuery("update zahtev set ukupno=:sum where zahtevID=:ID")
                .setParameter("sum", stavke.get(0).getZahtev().getUkupno()).
                setParameter("ID", stavke.get(0).getZahtev().getZahtevID()).executeUpdate();

        httpStatus = HttpStatus.OK;
    }

}
