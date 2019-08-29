package com.classic.mvvmapplication.utilities;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import javax.inject.Inject;

/**
 * Created by Amjad on 11/08/2018.
 */

public class BooleanDeserializer implements JsonDeserializer<Boolean> {

    @Inject
    public BooleanDeserializer() {
    }

    public Boolean deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        try {
            int code = json.getAsInt();
            return code == 0 ? false :
                    code == 1 ? true :
                            null;
        } catch (Exception e) {
            if (e instanceof NumberFormatException) {
                return json.getAsBoolean();
            }
            else return null;
        }


    }

}
