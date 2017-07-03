/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package online.config;

import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author ciricj
 */
public class IntFormatter implements Formatter<Integer> {

    public IntFormatter() {
        super();
    }

    @Override
    public String print(Integer t, Locale locale) {
        if (t == 0) {
            return "";
        }
        return t + "";
    }

    @Override
    public Integer parse(String string, Locale locale) throws ParseException {
        return Integer.parseInt(string);
    }

}
