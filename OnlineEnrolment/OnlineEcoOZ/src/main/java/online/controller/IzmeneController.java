/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package online.controller;

import domen.OpstiDomenskiObjekat;
import domen.StavkaZahteva;
import domen.Zahtev;
import exceptions.CustomException;
import java.util.ArrayList;
import java.util.List;
import static online.controller.EnrolmentController.message;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import util.Constants;

/**
 *
 * @author dzuli_c
 */
@Controller
@RequestMapping("/edit")
public class IzmeneController extends EnrolmentController {

    @RequestMapping(value = "/zahtev", method = RequestMethod.POST, params = "action=delete")
    public String obrisiZahtev(@ModelAttribute("pomocniZahtev") Zahtev z) throws CustomException {
        List<OpstiDomenskiObjekat> lista = new ArrayList<>();
        int i = 1;
        for (StavkaZahteva stavkaZahteva : z.getListaStavki()) {
            Zahtev zah = new Zahtev(z.getZahtevID(), z.getDatum(), z.getKorisnik(), z.getUkupno());
            stavkaZahteva.setZahtev(zah);
            lista.add(stavkaZahteva);
            i++;
        }
        try {
            service.delete(lista, Constants.STAVKA_ZAHTEVA);
            message = "Sistem je obrisao zahtev";
            zaduzenjeSve = null;
            return "redirect:/";
        } catch (Exception e) {
            throw new CustomException("Sistem ne moze da obrise zahtev");
        }

    }

    @RequestMapping(value = "/zahtev", method = RequestMethod.POST, params = "action=update")
    public String izmeniZahtev(@ModelAttribute("pomocniZahtev") Zahtev z) throws CustomException {
        message = "";
        if (!"".equals(id)) {
            z.setZahtevID(Integer.parseInt(id));
        }

        int i = 0;

        List<OpstiDomenskiObjekat> lista = new ArrayList<>();
        for (StavkaZahteva stavkaZahteva : z.getListaStavki()) {
            Zahtev zah = new Zahtev(z.getZahtevID(), z.getDatum(), z.getKorisnik(), z.getUkupno());
            stavkaZahteva.setZahtev(zah);
            stavkaZahteva.setRedniBroj(i);
            lista.add(stavkaZahteva);
            i += stavkaZahteva.getKolicina();
        }
        ((StavkaZahteva) lista.get(0)).getZahtev().setUkupno(i);
        try {
            service.update(lista, Constants.STAVKA_ZAHTEVA);
            message = "Sistem je izmenio zahtev";
            return "redirect:/";
        } catch (Exception ex) {
            throw new CustomException("Sistem ne moze da izmeni zahtev");
        }

    }
}
