package idv.lingerkptor.GoodsManager.core;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class AnalyzeJson<T> implements Analyzable {

    @Override
    public T analyze(HttpServletRequest req) {
        StringBuffer json = new StringBuffer();
        String line = null;
        BufferedReader reader;
        try {
            reader = req.getReader();
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        Gson gson = new Gson();
        try {
            //這裡未來要修改，將用泛型的類別
            return (T) gson.fromJson(json.toString(), (Class<T>) req.getAttribute("reqclass"));
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

}
