import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class WeatherApp {
    public static JSONObject getWeatherData(String city) {
        JSONArray locationData = getLocationData(city);

        JSONObject location = (JSONObject) locationData.get(0);
        System.out.println(location);
        double lat = (double) location.get("latitude");
        double lon = (double) location.get("longitude");

        String urlString = "https://api.open-meteo.com/v1/forecast?latitude="
                + lat + "&longitude=" + lon +
                "&hourly=temperature_2m,relative_humidity_2m,weather_code,wind_speed_10m";

        try {
            HttpURLConnection connection = fetchApiResponse(urlString);

            if (connection.getResponseCode() == 200) {
                StringBuilder resultJson = new StringBuilder();
                Scanner scanner = new Scanner(connection.getInputStream());
                while (scanner.hasNextLine()) {
                    resultJson.append(scanner.nextLine());
                }
                scanner.close();

                connection.disconnect();
                JSONParser parser = new JSONParser();
                JSONObject resultJsonObject = (JSONObject) parser.parse(resultJson.toString());

                JSONObject hourly = (JSONObject) resultJsonObject.get("hourly");
                JSONArray time = (JSONArray) hourly.get("time");
                int index = findIndexOfCurrentTime(time);

                JSONArray temperature = (JSONArray) hourly.get("temperature_2m");
                JSONArray humidity = (JSONArray) hourly.get("relative_humidity_2m");
                JSONArray weatherCode = (JSONArray) hourly.get("weather_code");
                JSONArray windSpeed = (JSONArray) hourly.get("wind_speed_10m");

                double temperatureValue = (double) temperature.get(index);
                long humidityValue = (long) humidity.get(index);
                double windSpeedValue = (double) windSpeed.get(index);
                String weatherCodeValue = convertWeatherCode((long) weatherCode.get(index));

                JSONObject weatherData = new JSONObject();
                weatherData.put("weather_condition", weatherCodeValue);
                weatherData.put("temperature", temperatureValue);
                weatherData.put("humidity", humidityValue);
                weatherData.put("windSpeed", windSpeedValue);
                weatherData.put("weatherCode", weatherCodeValue);

                return weatherData;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONArray getLocationData(String city) {
        // This method will be implemented in the next step
        city = city.replaceAll(" ", "+");
        String url = "https://geocoding-api.open-meteo.com/v1/search?name=" + city + "&count=10&language=en&format=json";

        try {
            HttpURLConnection connection = fetchApiResponse(url);

            if (connection.getResponseCode() == 200) {
                StringBuilder resultJson = new StringBuilder();
                Scanner scanner = new Scanner(connection.getInputStream());
                while (scanner.hasNextLine()) {
                    resultJson.append(scanner.nextLine());
                }
                scanner.close();

                connection.disconnect();
                JSONParser parser = new JSONParser();
                JSONObject resultJsonObject = (JSONObject) parser.parse(resultJson.toString());
                JSONArray locationData = (JSONArray) parser.parse(resultJsonObject.get("results").toString());

                return locationData;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static HttpURLConnection fetchApiResponse(String url) {
        // This method will be implemented in the next step
        try {
            URL apiUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) apiUrl.openConnection();

            conn.setRequestMethod("GET");

            conn.connect();
            return conn;
        } catch (IOException e) {
            e.printStackTrace();
    }
        return null;
}

    private static int findIndexOfCurrentTime(JSONArray time) {
        String currentTime = getCurrentTime();

        for (int i = 0; i < time.size(); i++) {
            if (time.get(i).equals(currentTime)) {
               String time1 = (String) time.get(i);
                if (time1.equals(currentTime)) {
                    return i;
                }
            }
        }
        return 0;
    }

    public static String getCurrentTime() {
        // This method will be implemented in the next step
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH':00'");

        String formattedDateTime = currentTime.format(formatter);
        return formattedDateTime;
    }

    // convert the weather code to something more readable
    private static String convertWeatherCode(long weathercode){
        String weatherCondition = "";
        if(weathercode == 0L){
            // clear
            weatherCondition = "Clear";
        }else if(weathercode > 0L && weathercode <= 3L){
            // cloudy
            weatherCondition = "Cloudy";
        }else if((weathercode >= 51L && weathercode <= 67L)
                    || (weathercode >= 80L && weathercode <= 99L)){
            // rain
            weatherCondition = "Rain";
        }else if(weathercode >= 71L && weathercode <= 77L){
            // snow
            weatherCondition = "Snow";
        }

        return weatherCondition;
    }
}
