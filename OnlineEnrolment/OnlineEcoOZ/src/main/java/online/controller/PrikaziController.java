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
import java.util.LinkedHashMap;
import java.util.List;
import static online.controller.EnrolmentController.message;
import static online.controller.EnrolmentController.pomocneStavkeIzv;
import static online.controller.EnrolmentController.pomocneStavkeZah;
import static online.controller.EnrolmentController.pomocniZahtev;
import static online.controller.EnrolmentController.zaduzenjeSve;
import static online.controller.EnrolmentController.zahtev;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import util.Constants;

/**
 *
 * @author dzuli_c
 */
@Controller
@RequestMapping("/prikazi")
public class PrikaziController extends EnrolmentController {
    
    @RequestMapping(path = "/zahtev", method = RequestMethod.POST)
    public String prikaziZahteve(@RequestBody String id) throws CustomException {
        message = "";
        pomocniZahtev = new Zahtev();
        this.id = id.replace("=", "");
        pomocniZahtev.setZahtevID(Integer.parseInt(this.id));
        ObjectMapper mapper = new ObjectMapper();
        StavkaZahteva stavka = new StavkaZahteva();
        stavka.setZahtev(zahtev);
        pomocneStavkeZah = new ArrayList<>();
        try {
            List<LinkedHashMap> list = service.list(pomocniZahtev, Constants.ZAHTEV);
            if (list.isEmpty()) {
                message = "Ne moze se prikazati zahtev";
                return "redirect:/";
            }
            for (LinkedHashMap map : list) {
                StavkaZahteva s = mapper.convertValue(map, StavkaZahteva.class);
                pomocneStavkeZah.add(s);
            }
            pomocniZahtev.setListaStavki(pomocneStavkeZah);
            modelAndView.addObject("pomocniZahtev", pomocniZahtev);
            modelAndView.addObject("pomocneStavkeZah", pomocneStavkeZah);
            message = "Sistem je nasao zahtev";
            modelAndView.addObject("message", message);
            return "redirect:/";
        } catch (Exception e) {
            throw new CustomException("Sistem ne moze da prikaze zahtev");
        }
        
    }
    
    @RequestMapping(path = "/zaduzenje", method = RequestMethod.POST)
    public String prikaziZaduzenje(@RequestBody String id) throws CustomException {
        message = "";
        ObjectMapper mapper = new ObjectMapper();
        zaduzenjeSve = new Zaduzenja();
        id = id.replace("=", "");
        zaduzenjeSve.setZaduzenjaID(Integer.parseInt(id));
        zaduzenjeSve = (Zaduzenja) service.get(zaduzenjeSve, Constants.ZADUZENJE);
        List<StavkaZahteva> stavke = new ArrayList<>();
        try {
            if (zaduzenjeSve == null) {
                message = "Sistem ne moze da prikaze zaduzenje";
                return "redirect:/";
            }
            if (zaduzenjeSve.getZahtev() != null) {
                zaduzenjeSve.getZahtev().setListaStavki(stavke);
                modelAndView.addObject("zaduzenjeSve", zaduzenjeSve);
                message = "Prikazano je zaduzenje";
                modelAndView.addObject("message", message);
                return "redirect:/";
            } else {
                message = "Sistem ne moze da prikaze zaduzenje";
                return "/";
            }
        } catch (Exception ex) {
            throw new CustomException("Sistem ne moze da prikaze zaduzenja po datoj vrednosti");
        }
        
    }
    
    @RequestMapping(path = "/izvestaj", method = RequestMethod.POST)
    public String prikaziIzvestaj(@RequestBody String id) throws CustomException {
        message = "";
        pomocneStavkeIzv = new ArrayList<>();
        id = id.replace("=", "");
        Izvestaj iz = new Izvestaj();
        iz.setID(Integer.parseInt(id));
        ObjectMapper mapper = new ObjectMapper();
        try {
            iz = (Izvestaj) service.get(iz, Constants.IZVESTAJ);
            
            if (iz == null) {
                message = "Ne moze se prikazati izvestaj";
                return "redirect:/";
            }
            pomocneStavkeIzv = iz.getListaStavki();
            for (StavkaIzvestaja si : pomocneStavkeIzv) {
                si.setIzvestaj(iz);
            }
            modelAndView.addObject("pomocneStavkeIzv", pomocneStavkeIzv);
            message = "Prikazan je trazeni izvestaj";
            return "redirect:/";
        } catch (Exception e) {
            throw new CustomException("Sistem ne moze da prikaze trazeni izvestaj");
        }
    }
    
}
