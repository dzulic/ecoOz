/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package online.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import domen.Zaduzenja;
import domen.Zahtev;
import exceptions.CustomException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import static online.controller.EnrolmentController.listaZaduzenja;
import static online.controller.EnrolmentController.message;
import static online.controller.EnrolmentController.sluzba;
import static online.controller.EnrolmentController.zaduzenje;
import static online.controller.EnrolmentController.zaduzenjeSve;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import util.Constants;

/**
 *
 * @author dzuli_c
 */
@Controller
@RequestMapping("/get/zaduzenja")
public class PretraziZaduzenjaController extends EnrolmentController {
    //4

    @RequestMapping(path = "/all")
    public String pretraziZaduzenja() throws CustomException {
        message = "";
        zaduzenjeSve = null;
        if (zaduzenje == null) {
            zaduzenje = new Zaduzenja();
        }
        zaduzenje.setSluzba(sluzba);
        zaduzenje.setZahtev(new Zahtev());
        listaZaduzenja = new ArrayList<>();
        try {
            List<LinkedHashMap> list = service.list(zaduzenje, Constants.ZADUZENJE);
            if (list.isEmpty()) {
                message = "Sistem nije pronasao zaduzenja";
                return "redirect:/";
            }
            getZaduzenja(list);
            message = "Sistem je pronasao zaduzenja";
            return "redirect:/";
        } catch (Exception e) {
            throw new CustomException("Ne moze da nadje zaduzenja");
        }
    }

    @RequestMapping(path = "/selected")
    public String pretraziZaduzenja(@ModelAttribute("zaduzenja") Zaduzenja z) throws CustomException {
        message = "";
        zaduzenje = z;
        zaduzenje.setSluzba(sluzba);
        try {
            List<LinkedHashMap> list = service.list(zaduzenje, Constants.ZADUZENJE);
            getZaduzenja(list);
            if (list.isEmpty()) {
                message = "Sistem nije pronasao zaduzenja";
                return "redirect:/";
            }
            message = "Sistem je pronasao zaduzenja";
            return "redirect:/";
        } catch (Exception e) {
            throw new CustomException("Ne moze da nadje zaduzenja");
        }
    }

    public void getZaduzenja(List<LinkedHashMap> list) {
        ObjectMapper mapper = new ObjectMapper();
        listaZaduzenja = new ArrayList<>();
        for (LinkedHashMap map : list) {
            listaZaduzenja.add(mapper.convertValue(map, Zaduzenja.class));
        }
        modelAndView.addObject("listaZaduzenja", listaZaduzenja);
    }
}
