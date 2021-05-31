package com.example.cristband;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class User {

    private static User instance = new User();

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
    private long          lastseen;
    private String        updater;

    private DatabaseReference   mDatabase;
    private FirebaseAuth        mAuth;

    private User() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth     = FirebaseAuth.getInstance();
    }

    public static User getInstance() {
        if (instance == null)
            instance = new User();
        return instance;
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

    public int getLastindex() {
        return lastindex;
    }

    public void setLastindex(int lastindex) {
        this.lastindex = lastindex;
    }

    public long getLastseen() {
        return lastseen;
    }

    public void setLastseen(long lastseen) {
        this.lastseen = lastseen;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public List<Data> getTemperaturebody() {
        return temperaturebody;
    }

    public void setTemperaturebody(List<Data> temperaturebody) {
        this.temperaturebody = temperaturebody;
    }

    public void synchronize() {
        final UserHolder[] holder = new UserHolder[1];
        holder[0] = new UserHolder();
        mDatabase.child(mAuth.getUid()).child("updater").setValue("+");
        try {
            mDatabase.child(mAuth.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    try {
                        holder[0] = snapshot.getValue(UserHolder.class);
                        setName(holder[0].getName());
                        setGender(holder[0].getGender());
                        setAge(holder[0].getGender());
                        setPassword(holder[0].getPassword());
                        setAccerelator(holder[0].getAccerelator());
                        setHeart(holder[0].getHeart());
                        setOxygen(holder[0].getOxygen());
                        setTemperature(holder[0].getTemperature());
                    } catch (Exception e) {
                        Log.e("**********SYNC ERROR***", "ERROR: onDataChange()");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        } catch (Exception e) {
            Log.e("**********SYNC ERROR***", "ERROR: synchronize()" + e);
        }

    }

    public void setUserInformation(UserHolder holder) {
        setName(holder.getName());
        setMail(holder.getMail());
        setGender(holder.getGender());
        setAge(holder.getGender());
        setPassword(holder.getPassword());
        setAccerelator(holder.getAccerelator());
        setHeart(holder.getHeart());
        setOxygen(holder.getOxygen());
        setTemperature(holder.getTemperature());
        setTemperaturebody(holder.getTemperatureBody());
        setLastindex(holder.getLastindex());
        setLastseen(holder.getLastseen());
        setUpdater(holder.getUpdater());
        setHumidity(holder.getHumidity());
    }
}