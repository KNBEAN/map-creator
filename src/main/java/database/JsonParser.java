package database;

import com.google.gson.Gson;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class JsonParser {

    public <T> ArrayList<T> getEntityArrayList(String jsonPath, Type type) {
        
        ArrayList<T> arrayList = null;

        try (InputStream inputStream = new FileInputStream(jsonPath)) {
            Gson gson = new Gson();
            Reader reader = new InputStreamReader(inputStream);
            arrayList = gson.fromJson(reader, type);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return arrayList;
    }
}
