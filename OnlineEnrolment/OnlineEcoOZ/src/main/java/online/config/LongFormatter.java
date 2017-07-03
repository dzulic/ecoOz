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
public class LongFormatter implements Formatter<Long> {

    public LongFormatter() {
        super();
    }

    @Override
    public String print(Long t, Locale locale) {
        if (t == 0) {
            return "";
        } else {
            return t + "";
        }
    }

    @Override
    public Long parse(String string, Locale locale) throws ParseException {
        return Long.parseLong(string);
    }

}
