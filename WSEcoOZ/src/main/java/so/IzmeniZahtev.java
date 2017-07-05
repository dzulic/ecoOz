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
        int sum = 0;
        for (StavkaZahteva stavkaZahteva : stavke) {
            sum += stavkaZahteva.getKolicina();
            StavkaZahteva found = null;
            try {
                found = session.get(StavkaZahteva.class, stavkaZahteva);
            } catch (Exception e) {

            }
            if (found == null) {
                session.save(stavkaZahteva);
            } else {
                session.createNativeQuery("update stavka_zahteva set kolicina=:kol and materijal=:mat where redniBroj=:rb and zahtev_zahtevID=:ID")
                        .setParameter("kol", stavkaZahteva.getKolicina())
                        .setParameter("mat", stavkaZahteva.getMaterijal())
                        .setParameter("rb", stavkaZahteva.getRedniBroj())
                        .setParameter("ID", stavkaZahteva.getZahtev().getZahtevID()).executeUpdate();
            }
        }
        session.createNativeQuery("update zahtev set ukupno=:sum where zahtevID=:ID")
                .setParameter("sum", sum).
                setParameter("ID", stavke.get(0).getZahtev().getZahtevID()).executeUpdate();

        httpStatus = HttpStatus.OK;
    }

}
