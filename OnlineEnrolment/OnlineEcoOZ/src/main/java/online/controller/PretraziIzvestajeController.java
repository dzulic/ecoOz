/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package online.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import domen.Izvestaj;
import domen.StavkaIzvestaja;
import exceptions.CustomException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import util.Constants;

/**
 *
 * @author dzuli_c
 */
@Controller
@RequestMapping("/get/izvestaj")
public class PretraziIzvestajeController extends EnrolmentController {

    public void getIzvestaje(List<LinkedHashMap> list) {
        List<Izvestaj> izvestaji = new ArrayList<>();
        message = "";
        ObjectMapper mapper = new ObjectMapper();
        stavkeIzv = new ArrayList<>();
        for (LinkedHashMap map : list) {
            Izvestaj iz = mapper.convertValue(map, Izvestaj.class);
            boolean ok = checkIsThere(iz);
            if (!izvestaji.contains(iz) && ok) {
                izvestaji.add(iz);
                stavkeIzv.add(iz);
            }
        }
        modelAndView.addObject("listaStavkeIzvestaja", stavkeIzv);

    }

    @RequestMapping(path = "/selected")
    public String pretraziIzvestaje(@ModelAttribute("izvestaj") Izvestaj i) throws CustomException {
        message = "";
        izvestaj = i;
        izvestaj.setSluzba(sluzba);
        izvestaj.getListaStavki().get(0).setIzvestaj(null);
        try {
            List<LinkedHashMap> list = service.list(izvestaj, Constants.IZVESTAJ);
            if (list.isEmpty()) {
                message = "Sistem nije pronasao izvestaje";
                return "redirect:/";
            }
            getIzvestaje(list);
            message = "Sistem je nasao izvestaj";
        } catch (Exception e) {
            throw new CustomException("Sistem ne moze da nadje izvestaj po trazenoj vrednosti");
        }
        return "redirect:/";
    }

    private boolean checkIsThere(Izvestaj sz) {
        for (Izvestaj stavka : stavkeIzv) {
            if (stavka.getID() == sz.getID()) {
                return false;
            }
        }
        return true;
    }
}
