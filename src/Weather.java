import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;


public class Weather {


    public static String getWeather(float lat, float lon) throws IOException {
        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid=73da624cc498c291a17948c42a23b6a5&units=metric");

        Scanner in = new Scanner((InputStream) url.getContent());
        String result = "";

        Template template = new Template();

        while (in.hasNext()) result = String.format("%s%s", result, in.nextLine());

        JSONObject object = new JSONObject(result);
        template.setName(object.getString("name"));

        JSONObject main = object.getJSONObject("main");
        template.setTemp(main.getDouble("temp"));
        template.setHumidity(main.getDouble("humidity"));

        JSONArray getArray = object.getJSONArray("weather");
        for (int i = 0; i < getArray.length(); i++) {
            JSONObject obj = getArray.getJSONObject(i);
            template.setMain((String) obj.get("main"));
        }

        return "Температура: " + template.getTemp() + "C" + "\n" +
                "Влажность:" + template.getHumidity() + "%" + "\n" +
                "Облачность: " + template.getMain() + "\n";
    }

    public static Object getInstance() {
        return null;
    }
}
