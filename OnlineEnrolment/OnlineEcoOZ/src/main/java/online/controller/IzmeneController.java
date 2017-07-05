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

        try {
            service.delete(z, Constants.ZAHTEV);
            message = "Sistem je obrisao zahtev";
            zaduzenjeSve = null;
            return "redirect:/";
        } catch (Exception e) {
            throw new CustomException("Sistem ne moze da obrise zahtev");
        }

    }

    @RequestMapping(value = "/zahtev/delete", method = RequestMethod.POST)
    public String obrisiStavkuZahteva(String rb, String zID) throws CustomException {
        Zahtev zah = new Zahtev();
        zah.setZahtevID(Integer.parseInt(zID));
        StavkaZahteva stavkaZahteva = new StavkaZahteva(zah, Integer.parseInt(rb), 0, null);
        try {
            service.delete(stavkaZahteva, Constants.STAVKA_ZAHTEVA);
            message = "Sistem je izmenio zahtev";
            zaduzenjeSve = null;
            return "redirect:/";
        } catch (Exception e) {
            throw new CustomException("Sistem ne moze da izmeni zahtev");
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

    @RequestMapping(value = "/zahtev", params = "action=addRowStavkaZah")
    public String addRowZah(@ModelAttribute("pomocniZahtev") Zahtev z) {
        message = "";
        pomocniZahtev = z;
        if (pomocniZahtev != null) {
            if (pomocniZahtev.getListaStavki() == null) {
                pomocniZahtev.setListaStavki(new ArrayList<>());
            }
            StavkaZahteva sz = new StavkaZahteva();
            sz.setRedniBroj(pomocniZahtev.getListaStavki().get(pomocniZahtev.getListaStavki().size()-1).getRedniBroj() + 1);
            pomocniZahtev.getListaStavki().add(sz);
            modelAndView.addObject("pomocniZahtev", pomocniZahtev);
        }
        return "redirect:/";
    }

    @RequestMapping(path = "/zahtev", method = RequestMethod.POST, params = "action=save")
    public String zapamtiZahtev(@ModelAttribute("pomocniZahtev") Zahtev newObject) throws CustomException {
        message = "";
        List<OpstiDomenskiObjekat> listaTransfer = new ArrayList<>();
        double sum = 0;
        pomocniZahtev = newObject;
        if (newObject.getListaStavki() != null) {
            for (StavkaZahteva s : newObject.getListaStavki()) {
                s.setZahtev(newObject);
                if (s.getKolicina() == 0) {
                    throw new CustomException("Količina ne može biti 0");
                }
                if (s.getMaterijal().equals("select")) {
                    throw new CustomException("Izaberite materijal");
                }

                sum += s.getKolicina();
                listaTransfer.add(s);
            }
            if (listaTransfer.isEmpty()) {
                throw new CustomException("Ne mozete da sacuvajte prazan zahtev");
            }
            newObject.setUkupno(sum);
            newObject.setKorisnik(korisnik);
            ((StavkaZahteva) listaTransfer.get(0)).setZahtev(newObject);
            try {

                service.save(listaTransfer, Constants.STAVKA_ZAHTEVA);
                message = "Sistem je zapamtio zahtev";
                noviZahtev = newObject;
            } catch (Exception e) {
                throw new CustomException("Sistem ne moze da zapamti zahtev");
            }
        }
        return "redirect:/";
    }
}
