/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package online.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import domen.Izvestaj;
import domen.OpstiDomenskiObjekat;
import domen.StavkaIzvestaja;
import domen.StavkaZahteva;
import domen.Zaduzenja;
import domen.Zahtev;
import exceptions.CustomException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import static online.controller.EnrolmentController.izvestaj;
import static online.controller.EnrolmentController.listaZahteva;
import static online.controller.EnrolmentController.message;
import static online.controller.EnrolmentController.noviZahtev;
import static online.controller.EnrolmentController.sluzba;
import static online.controller.EnrolmentController.stavkeZah;
import static online.controller.EnrolmentController.zahtev;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import util.Constants;
import utils.Wrapper;

/**
 *
 * @author dzuli_c
 */
@Controller
@RequestMapping("/zapamti")
public class CuvanjeController extends EnrolmentController {

    @RequestMapping(path = "/zahtev", method = RequestMethod.POST, params = "action=sacuvaj")
    public String zapamtiZahtev(@ModelAttribute("zahtev") Zahtev newObject) throws CustomException {
        message = "";
        if (zahtev != null) {
            newObject.setZahtevID(zahtev.getZahtevID());
        }
        List<OpstiDomenskiObjekat> listaTransfer = new ArrayList<>();
        int rb = 1;
        double sum = 0;
        zahtev = newObject;
        if (newObject.getListaStavki() != null) {
            for (StavkaZahteva s : newObject.getListaStavki()) {
                s.setZahtev(newObject);
                s.setRedniBroj(rb);
                if (s.getKolicina() == 0) {
                    throw new CustomException("Količina ne može biti 0");
                }
                if (s.getMaterijal().equals("select")) {
                    throw new CustomException("Izaberite materijal");
                }

                sum += s.getKolicina();
                listaTransfer.add(s);
                rb += 1;
            }
            if (listaTransfer.isEmpty()) {
                throw new CustomException("Ne mozete da sacuvajte prazan zahtev");
            }
            newObject.setUkupno(sum);
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

    @RequestMapping(value = "/zahtev", params = "action=addRowStavkaZah")
    public String addRowZah(@ModelAttribute("zahtev") Zahtev z) {
        message = "";
        zahtev = z;
        if (zahtev != null) {
            if (zahtev.getListaStavki() == null) {
                zahtev.setListaStavki(new ArrayList<>());
            }
            zahtev.getListaStavki().add(new StavkaZahteva());
            modelAndView.addObject("zahtev", zahtev);
        }
        return "redirect:/";
    }

    @RequestMapping(path = "/izvestaj", method = RequestMethod.POST, params = "action=sacuvaj")
    public String zapamtiIzvestaj(@ModelAttribute("izvestaj") Izvestaj newObject) throws CustomException {
        message = "";
        Izvestaj transferIzvestaj = new Izvestaj();
        transferIzvestaj.setID(izvestaj.getID());
        transferIzvestaj.setSluzba(sluzba);
        int rb = 0;
        List<StavkaIzvestaja> listaTransfer = new ArrayList<>();
        if (newObject.getListaStavki() != null) {
            for (StavkaIzvestaja s : newObject.getListaStavki()) {
                rb += 1;
                s.setRedniBroj(rb);

                if (s.getKolicina() == 0) {
                    throw new CustomException("Kolicina ne sme bii 0");
                }
                if (s.getMaterijal().equals("izaberi")) {
                    throw new CustomException("Izaberite materijal");
                }
                listaTransfer.add(s);
            }
        }
        transferIzvestaj.setListaStavki(listaTransfer);
        if (listaTransfer.isEmpty()) {
            throw new CustomException("Molimo Vas dodajte stavke");
        }
        try {
            List<OpstiDomenskiObjekat> transfer = new ArrayList<>();
            transfer.add(transferIzvestaj);
            service.save(transfer, Constants.IZVESTAJ);
            message = "Sistem je zapamtio izvestaj";
            return "redirect:/";
        } catch (Exception e) {
            throw new CustomException("Sistem ne moze da zapamti izvestaj");
        }
    }

    @RequestMapping(value = "/izvestaj", params = "action=addRowIzvestaj")
    public String addRowIzv(@ModelAttribute("izvestaj") Izvestaj iz) {
        message = "";
        izvestaj = iz;
        if (izvestaj != null) {
            if (izvestaj.getListaStavki() == null) {
                izvestaj.setListaStavki(new ArrayList<>());
            }
            izvestaj.getListaStavki().add(new StavkaIzvestaja());

            modelAndView.addObject("izvestaj", izvestaj);
        }
        return "redirect:/";
    }

    @RequestMapping("/izvestaj/add")
    public String foo(@ModelAttribute("wrapper") Wrapper w) {
        for (Zaduzenja wZ : w.getListaZaduzenja()) {
            if (wZ.isChecked()) {
                StavkaZahteva szPretraga = new StavkaZahteva();
                szPretraga.setZahtev(wZ.getZahtev());
                wZ.getZahtev().setListaStavki(getZahteve(service.list(szPretraga, Constants.STAVKA_ZAHTEVA)));
                for (StavkaZahteva sz : wZ.getZahtev().getListaStavki()) {
                    StavkaIzvestaja si = new StavkaIzvestaja();
                    si.setKolicina(sz.getKolicina());
                    si.setKorisnik(wZ.getZahtev().getKorisnik());
                    si.setMaterijal(sz.getMaterijal());
                    si.setDatum(wZ.getZahtev().getDatum());
                    si.setZaduzenja(wZ);
                    if (izvestaj.getListaStavki() == null) {
                        izvestaj.setListaStavki(new ArrayList<StavkaIzvestaja>());
                    }
                    izvestaj.getListaStavki().add(si);
                }
            }
        }
        return "redirect:/";
    }

    public List<StavkaZahteva> getZahteve(List<LinkedHashMap> list) {
        ObjectMapper mapper = new ObjectMapper();
        List<StavkaZahteva> stavke = new ArrayList<>();
        for (LinkedHashMap map : list) {
            StavkaZahteva s = mapper.convertValue(map, StavkaZahteva.class);
            stavke.add(s);
        }
        return stavke;
    }
}
