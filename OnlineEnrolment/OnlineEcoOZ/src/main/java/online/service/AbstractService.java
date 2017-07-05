/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package online.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import domen.Izvestaj;
import domen.Korisnik;
import domen.OpstiDomenskiObjekat;
import domen.StavkaIzvestaja;
import domen.StavkaZahteva;
import domen.Zaduzenja;
import domen.Zahtev;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import util.Constants;

/**
 *
 * @author ciricj
 */
@Service
@Component
public abstract class AbstractService {

    String jsonInString = null;
    ObjectMapper mapper;
    HttpHeaders headers;
    RestTemplate restTemplate;
    public String httpMessage;

    Logger logger = Logger.getLogger(AbstractService.class);

    public abstract String create(OpstiDomenskiObjekat odo, String type);

    public abstract OpstiDomenskiObjekat get(OpstiDomenskiObjekat odo, String type);

    public abstract List<LinkedHashMap> list(OpstiDomenskiObjekat odo, String type);

    public abstract void delete(OpstiDomenskiObjekat odo, String type);

    public abstract void update(List<OpstiDomenskiObjekat> odo, String type);

    public abstract void save(List<OpstiDomenskiObjekat> odo, String type);

    protected HttpEntity getRequestFromType(OpstiDomenskiObjekat odo, String type) {
        if (Constants.KORISNIK.equals(type)) {
            return objToJSON((Korisnik) odo);
        } else if (Constants.ZAHTEV.equals(type)) {
            return objToJSON((Zahtev) odo);
        } else if (Constants.IZVESTAJ.equals(type)) {
            if (odo instanceof StavkaIzvestaja) {
                return objToJSON((StavkaIzvestaja) odo);
            } else {
                return objToJSON((Izvestaj) odo);
            }
        } else if (Constants.ZADUZENJE.equals(type)) {
            System.out.println("zaduzenje");
            return objToJSON((Zaduzenja) odo);
        } else if (type.equals(Constants.STAVKA_IZVESTAJA)) {
            return objToJSON((StavkaIzvestaja) odo);
        } else if (type.equals(Constants.STAVKA_ZAHTEVA)) {
            return objToJSON((StavkaZahteva) odo);
        }
        return null;
    }

    protected HttpEntity objToJSON(OpstiDomenskiObjekat odo) {
        try {
            jsonInString = mapper.writeValueAsString(odo);
        } catch (JsonProcessingException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return new HttpEntity(jsonInString, headers);
    }

    protected void initializeRest() {
        restTemplate = new RestTemplate();
        mapper = new ObjectMapper();

        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Accept", "application/json;charset=utf-8");

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.setMessageConverters(messageConverters);

    }

    public String getHttpMessage() {
        return httpMessage;
    }

}
