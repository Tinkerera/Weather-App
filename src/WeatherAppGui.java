import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.io.File;

public class WeatherAppGui extends JFrame {
    private JSONObject weatherData;
    public WeatherAppGui() {
        setTitle("Weather App");
        setSize(450, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        addGuiComponents();
    }
    private void addGuiComponents(){
        JTextField searchTextFied = new JTextField();

        searchTextFied.setBounds(15,15,350,45);

        searchTextFied.setFont(new Font("Dialog", Font.PLAIN, 24));
        add(searchTextFied);

        JLabel weatherConditionImage = new JLabel(loadImage("src/assets/cloudy.png"));
        weatherConditionImage.setBounds(0, 125, 450, 217);
        add(weatherConditionImage);

        JLabel temperature = new JLabel("10°C", SwingConstants.CENTER);
        temperature.setBounds(0, 350, 450, 54);
        temperature.setFont(new Font("Dialog", Font.BOLD, 48));
        add(temperature);

        JLabel weatherCondititionDescription = new JLabel("Cloudy", SwingConstants.CENTER);
        weatherCondititionDescription.setBounds(0, 405, 450, 36);
        weatherCondititionDescription.setFont(new Font("Dialog", Font.PLAIN, 32));
        add(weatherCondititionDescription);

        JLabel humidity = new JLabel(loadImage("src/assets/humidity.png"), SwingConstants.CENTER);
        humidity.setBounds(15, 500, 74, 66);
        add(humidity);

        JLabel humidityValue = new JLabel("<html><b>Humidity</b> 100%</html>", SwingConstants.CENTER);
        humidityValue.setBounds(90, 510, 85, 55);
        humidityValue.setFont(new Font("Dialog", Font.PLAIN, 16));
        add(humidityValue);

        JLabel wind = new JLabel(loadImage("src/assets/windspeed.png"), SwingConstants.CENTER);
        wind.setBounds(230, 500, 74, 66);
        add(wind);

        JLabel windValue = new JLabel("<html><b>Wind</b> 10km/h</html>", SwingConstants.CENTER);
        windValue.setBounds(320, 510, 85, 55);
        windValue.setFont(new Font("Dialog", Font.PLAIN, 16));
        add(windValue);

        JButton searchButton = new JButton(loadImage("src/assets/search.png"));
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setBounds(375,13,50,45);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get location from user
                String userInput = searchTextFied.getText();

                // validate input - remove whitespace to ensure non-empty text
                if(userInput.replaceAll("\\s", "").length() <= 0){
                    return;
                }

                // retrieve weather data
                weatherData = WeatherApp.getWeatherData(userInput);

                // update gui

                // update weather image
                String weatherCondition = (String) weatherData.get("weather_condition");
                System.out.println(weatherCondition);
                // depending on the condition, we will update the weather image that corresponds with the condition
                switch(weatherCondition){
                    case "Clear":
                        weatherConditionImage.setIcon(loadImage("src/assets/clear.png"));
                        break;
                    case "Cloudy":
                        weatherConditionImage.setIcon(loadImage("src/assets/cloudy.png"));
                        break;
                    case "Rain":
                        weatherConditionImage.setIcon(loadImage("src/assets/rain.png"));
                        break;
                    case "Snow":
                        weatherConditionImage.setIcon(loadImage("src/assets/snow.pngImage"));
                        break;
                }
                System.out.println(weatherData);
                double temperatureValue = (double) weatherData.get("temperature");
                temperature.setText(temperatureValue + "°C");

                String weatherConditionDescription = (String) weatherData.get("weather_condition");
                weatherCondititionDescription.setText(weatherConditionDescription);

                long humidityValue1 = (long) weatherData.get("humidity");
                humidityValue.setText("<html><b>Humidity</b> " + humidityValue1 + "%</html>");

                double windSpeedValue = (double) weatherData.get("windSpeed");
                windValue.setText("<html><b>Wind</b> " + windSpeedValue + "km/h</html>");

            }
        });
        add(searchButton);

    }
    private ImageIcon loadImage(String path){
        try {
            BufferedImage image = ImageIO.read(new File(path));

            return new ImageIcon(image);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Failed to load image");
        return null;
}}
