package com.example.demo.commonfunctions;

import com.example.demo.entity.Weather;
import com.google.gson.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JsonOperations {

    public JsonOperations(){

    }
    public void writeJson(Weather weather) throws IOException {

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setLenient().create();

        File jsonFile = new File("weather.json");
        JsonElement obj = new JsonParser().parse(new FileReader(jsonFile.getAbsolutePath()));
        JsonArray arr = obj.getAsJsonArray();

        arr.add(gson.toJson(weather));

        String jsonString = gson.toJson(arr);
        FileWriter fileWriter = new FileWriter(jsonFile.getAbsolutePath());
        fileWriter.write(jsonString);
        fileWriter.close();
    }
}
