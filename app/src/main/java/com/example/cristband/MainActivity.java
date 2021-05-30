package com.example.cristband;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    User              user      = User.getInstance();
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth      mAuth     = FirebaseAuth.getInstance();
    TextView          nameText;
    TextView          hearthText;
    TextView          oxygenText;
    TextView          temperatureText;
    TextView          bodyTemperatureText;
    TextView          humidityText;
    TextView          accelerationText;
    TextView          heartTimestamp;
    TextView          oxygenTimestamp;
    TextView          temperatureTimestamp;
    TextView          bodyTemperatureTimestamp;
    TextView          humidityTimestamp;
    //TextView          accelerationTimestamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameText                  = findViewById(R.id.name_text);
        hearthText                = findViewById(R.id.heart_data);
        oxygenText                = findViewById(R.id.oxygen_data);
        temperatureText           = findViewById(R.id.temperature_data);
        bodyTemperatureText       = findViewById(R.id.temperature_body_data);
        humidityText              = findViewById(R.id.humidity_data);
      //accelerationText          = findViewById(R.id.acceleration_data);
        heartTimestamp            = findViewById(R.id.heart_timestamp);
        oxygenTimestamp           = findViewById(R.id.oxygen_timestamp);
        temperatureTimestamp      = findViewById(R.id.temperature_timestamp);
        bodyTemperatureTimestamp  = findViewById(R.id.temperature_body_timestamp);
        humidityTimestamp         = findViewById(R.id.humidity_timestamp);
      //accelerationTimestamp     = findViewById(R.id.acceleration_timestamp);

        final UserHolder[] holder = new UserHolder[1];
        holder[0] = new UserHolder();


        mDatabase.child(mAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    holder[0] = snapshot.getValue(UserHolder.class);
                    user.setUserInformation(holder[0]);
                    nameText.setText("" + user.getName());
                    hearthText.setText( ""+ (int)user.getHeart().get(user.getHeart().size() -1).getData());
                    oxygenText.setText("" + user.getOxygen().get(user.getOxygen().size() - 1).getData());
                    temperatureText.setText("" + user.getTemperature().get(user.getTemperature().size() - 1).getData());
                    bodyTemperatureText.setText("" + user.getTemperaturebody().get(user.getTemperaturebody().size() - 1).getData());
                    humidityText.setText("" + user.getHumidity().get(user.getHumidity().size() - 1).getData());
                    //accelerationText.setText("" + user.getAccerelator().get(user.getAccerelator().size() - 1).getData());
                    heartTimestamp.setText( getDate(user.getHeart().get(user.getHeart().size() -1).getTimestamp()));
                    oxygenTimestamp.setText( getDate(user.getOxygen().get(user.getOxygen().size() -1).getTimestamp()));
                    temperatureTimestamp.setText( getDate(user.getTemperature().get(user.getTemperature().size() -1).getTimestamp()));
                    bodyTemperatureTimestamp.setText(getDate(user.getTemperaturebody().get(user.getTemperaturebody().size() - 1).getTimestamp()));
                    humidityTimestamp.setText( getDate(user.getHumidity().get(user.getHumidity().size() -1).getTimestamp()));
                    //accelerationTimestamp.setText( ""+ user.getAccerelator().get(user.getAccerelator().size() -1).getTimestamp());
                } catch (Exception e) {
                    Log.e("**********SYNC ERROR***", "ERROR: onDataChange() " + e);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private String getDate(long time) {
        Date date = new Date(time*1000L); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm, dd MMM yyyy "); // the format of your date
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+3"));

        return sdf.format(date);
    }

}