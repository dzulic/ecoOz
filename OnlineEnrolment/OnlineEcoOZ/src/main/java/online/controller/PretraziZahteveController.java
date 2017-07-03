/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package online.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import domen.StavkaZahteva;
import domen.Zahtev;
import exceptions.CustomException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import static online.controller.EnrolmentController.korisnik;
import static online.controller.EnrolmentController.listaZahteva;
import static online.controller.EnrolmentController.message;
import static online.controller.EnrolmentController.stavkeZah;
import static online.controller.EnrolmentController.zahtev;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import util.Constants;

/**
 *
 * @author dzuli_c
 */
@Controller
@RequestMapping("/get/zahtev")
public class PretraziZahteveController extends EnrolmentController{
    
    @RequestMapping(path = "/all")
    public String pretraziZahteve() throws CustomException {
        message = "";
        if (zahtev == null) {
            zahtev = new Zahtev();
        }
        stavkeZah = new ArrayList<>();
        zahtev.setKorisnik(korisnik);
        StavkaZahteva sz = new StavkaZahteva();
        sz.setZahtev(zahtev);
        List<LinkedHashMap> list = null;
        try {
            list = service.list(sz, Constants.STAVKA_ZAHTEVA);
            if (!list.isEmpty()) {
                message = "Pronadjeni su zahtevi";
                getZahteve(list);
            } else {
                message = "Nisu pronadjeni zahtevi";
                return "redirect:/";
            }
        } catch (Exception ex) {
            throw new CustomException("Sistem ne moze da pronadje zahtev");
        }
        return "redirect:/";
    }

    @RequestMapping(path = "/selected")
    public String pretraziZahteveSelected(@ModelAttribute("zahtev") Zahtev z) throws CustomException {
        message = "";
        z.setKorisnik(korisnik);
        StavkaZahteva sz = z.getListaStavki().get(0);
        stavkeZah = new ArrayList<>();
        try {
            List<LinkedHashMap> list = service.list(sz, Constants.STAVKA_ZAHTEVA);
            if (list.isEmpty()) {
                message = "Nema trazenog zahteva";
                return "redirect:/";
            }
            getZahteve(list);
            message = "Prikazan je trazeni zahtev";
            return "redirect:/";
        } catch (Exception e) {
            throw new CustomException("Sistem ne moze da prikaze zahtev");
        }
    }

    public void getZahteve(List<LinkedHashMap> list) {
        ObjectMapper mapper = new ObjectMapper();
        listaZahteva = new ArrayList<>();
        for (LinkedHashMap map : list) {
            StavkaZahteva s = mapper.convertValue(map, StavkaZahteva.class);
            stavkeZah.add(s);
            if (!listaZahteva.contains(s.getZahtev())) {
                listaZahteva.add(s.getZahtev());
            }
        }
        modelAndView.addObject("stavkeZah", stavkeZah);
        modelAndView.addObject("listaZahteva", listaZahteva);
    }

}
