package com.formbuilder.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class GsonParser {

    private static Gson gson = new Gson();

    public static Gson getGson() {
        if(gson == null){
            gson = new Gson();
        }
        return gson;
    }

    /**
     * @param typeCast : new TypeToken<ModelName>() {}
     */
    public static <T> String toJson(Object item, TypeToken<T> typeCast) {
        String toJson = "";
        try {
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .serializeNulls()
                    .create();
            toJson = gson.toJson(item, typeCast.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toJson;
    }

    /**
     * @param typeCast : new TypeToken<ModelName>() {}
     */
    public static <T> T fromJson(String jsonValue, TypeToken<T> typeCast) {
        try {
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .serializeNulls()
                    .create();
            return gson.fromJson(jsonValue, typeCast.getType());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param typeCast : new TypeToken<ModelName>() {}
     */
    public static <T> String toJsonAll(Object item, TypeToken<T> typeCast) {
        String toJson = "";
        try {
            toJson = new Gson().toJson(item, typeCast.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toJson;
    }


    /**
     * @param typeCast : new TypeToken<ModelName>() {}
     */
    public static <T> T fromJsonAll(String jsonValue, TypeToken<T> typeCast) {
        try {
            return new Gson().fromJson(jsonValue, typeCast.getType());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
