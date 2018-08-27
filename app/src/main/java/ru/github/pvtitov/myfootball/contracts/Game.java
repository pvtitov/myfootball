package ru.github.pvtitov.myfootball.contracts;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

public class Game {
    private double latitude;
    private double longitude;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private List<Integer> attendees = new ArrayList<>();
    private String title;
    private String description;
    private String key;

    public List<Integer> getAttendees() {
        return attendees;
    }

    public void addAttendee(User user) {
        if (user.getLogin() != null) {
            int hash = user.getLogin().hashCode();
            for (Integer attendee: attendees){
                if (attendee == hash) return;
            }
            attendees.add(hash);
        }
    }

    public String getTitle() {
        return title;
    }

    public void setAttendees(List<Integer> attendees) {
        this.attendees = new ArrayList<>();
        this.attendees.addAll(attendees);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public long calculateTimeInMillis() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.set(year, month, day, hour, minute);
        return calendar.getTimeInMillis();
    }
}
