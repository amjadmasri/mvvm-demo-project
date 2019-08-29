package com.classic.mvvmapplication.utilities;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

/**
 * Created by Amjad on 11/08/2018.
 */

public class DateDeserializer implements JsonDeserializer<Date>  {

    private static final String[] DATE_FORMATS = new String[]{
            "yyyy-MM-dd HH:mm:ssZ",
            "yyyy-MM-dd HH:mm:ss",
            "yyyy-MM-dd",
            "EEE MMM dd HH:mm:ss z yyyy",
            "HH:mm:ss",
            "MM/dd/yyyy HH:mm:ss aaa",
            "yyyy-MM-dd HH:mm:ss.SSSSSS",
            "yyyy-MM-dd HH:mm:ss.SSSSSSS",
            "yyyy-MM-dd HH:mm:ss.SSSSSSS'Z'",
            "MMM d',' yyyy H:mm:ss a"
    };

    @Inject
    public DateDeserializer() {
    }

    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)throws JsonParseException {
        if(json.getAsString().trim().isEmpty()){
            return new Date();
        }
        for (String format : DATE_FORMATS) {
            try {
                return new SimpleDateFormat(format, Locale.US).parse(json.getAsString());
            } catch (ParseException e) {
            }
        }
        throw new JsonParseException("Unparseable date: \"" + json.getAsString()
                + "\". Supported formats: \n" + Arrays.toString(DATE_FORMATS));
    }
}
