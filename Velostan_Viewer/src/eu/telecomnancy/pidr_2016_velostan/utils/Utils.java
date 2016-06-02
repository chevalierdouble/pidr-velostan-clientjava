package eu.telecomnancy.pidr_2016_velostan.utils;

import java.text.SimpleDateFormat;

/**
 * Created by yoann on 21/05/16.
 */
public class Utils {

    private final static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public static SimpleDateFormat getFormatter() {
        return formatter;
    }

}
