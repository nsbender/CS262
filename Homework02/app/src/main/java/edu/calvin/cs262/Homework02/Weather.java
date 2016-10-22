package edu.calvin.cs262.Homework02;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Weather forecast object (POJO), one per day, based on openweathermap's RESTful API.
 * Based on Deitel's WeatherViewer app (chapter 17).
 *
 * @author deitel
 * @author kvlinden
 * @version spring, 2017
 */
public class Weather {

    private String name, email;
    private int id;

    public Weather(Integer id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Integer getId() {return id;}
    public String getName() {return name;}
    public String getEmail() {return email;}

}
