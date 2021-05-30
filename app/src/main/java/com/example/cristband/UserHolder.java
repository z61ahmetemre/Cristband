package com.example.cristband;

import java.util.List;

public class UserHolder {

    private String        name;
    private String        mail;
    private int           gender;
    private int           age;
    private String        password;
    private List<Data>    accerelator;
    private List<Data>    heart;
    private List<Data>    humidity;
    private List<Data>    oxygen;
    private List<Data>    temperature;
    private List<Data>    temperaturebody;
    private int           lastindex;
    private String        lastseen;
    private String        updater;

    public UserHolder() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Data> getAccerelator() {
        return accerelator;
    }

    public void setAccerelator(List<Data> accerelator) {
        this.accerelator = accerelator;
    }

    public List<Data> getHeart() {
        return heart;
    }

    public void setHeart(List<Data> heart) {
        this.heart = heart;
    }

    public List<Data> getHumidity() {
        return humidity;
    }

    public void setHumidity(List<Data> humidity) {
        this.humidity = humidity;
    }

    public List<Data> getOxygen() {
        return oxygen;
    }

    public void setOxygen(List<Data> oxygen) {
        this.oxygen = oxygen;
    }

    public List<Data> getTemperature() {
        return temperature;
    }

    public void setTemperature(List<Data> temperature) {
        this.temperature = temperature;
    }

    public List<Data> getTemperatureBody() {
        return temperaturebody;
    }

    public void setTemperatureBody(List<Data> temperatureBody) {
        this.temperaturebody = temperatureBody;
    }

    public int getLastindex() {
        return lastindex;
    }

    public void setLastindex(int lastindex) {
        this.lastindex = lastindex;
    }

    public String getLastseen() {
        return lastseen;
    }

    public void setLastseen(String lastseen) {
        this.lastseen = lastseen;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }
}