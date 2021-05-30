package com.example.cristband;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.Format;
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
    GraphView         tempGraph;
    GraphView         bodytempGraph;
    GraphView         heartGraph;
    GraphView         oxygenGraph;
    GraphView         humidityGraph;
    Button            showHideGraphs;
    CardView          heartCard;
    CardView          oxygenCard;
    CardView          tempCard;
    CardView          bodyTempCard;
    CardView          humidityCard;

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
        tempGraph                 = findViewById(R.id.graph_temperature);
        bodytempGraph             = findViewById(R.id.graph_temperature_body);
        heartGraph                = findViewById(R.id.graph_heart);
        oxygenGraph               = findViewById(R.id.graph_saturation);
        humidityGraph             = findViewById(R.id.graph_humidity);
        showHideGraphs            = findViewById(R.id.show_hide_graphs);
        heartCard                 = findViewById(R.id.card_pulse);
        oxygenCard                = findViewById(R.id.card_saturation);
        tempCard                  = findViewById(R.id.card_temp);
        bodyTempCard              = findViewById(R.id.card_temp_body);
        humidityCard              = findViewById(R.id.card_humidity);

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
                    oxygenText.setText("" + (int)user.getOxygen().get(user.getOxygen().size() - 1).getData());
                    temperatureText.setText("" + user.getTemperature().get(user.getTemperature().size() - 1).getData());
                    bodyTemperatureText.setText("" + user.getTemperaturebody().get(user.getTemperaturebody().size() - 1).getData());
                    humidityText.setText("" + (int) user.getHumidity().get(user.getHumidity().size() - 1).getData());
                    //accelerationText.setText("" + user.getAccerelator().get(user.getAccerelator().size() - 1).getData());
                    heartTimestamp.setText( getDate(user.getHeart().get(user.getHeart().size() -1).getTimestamp()));
                    oxygenTimestamp.setText( getDate(user.getOxygen().get(user.getOxygen().size() -1).getTimestamp()));
                    temperatureTimestamp.setText( getDate(user.getTemperature().get(user.getTemperature().size() -1).getTimestamp()));
                    bodyTemperatureTimestamp.setText(getDate(user.getTemperaturebody().get(user.getTemperaturebody().size() - 1).getTimestamp()));
                    humidityTimestamp.setText( getDate(user.getHumidity().get(user.getHumidity().size() -1).getTimestamp()));
                    //accelerationTimestamp.setText( ""+ user.getAccerelator().get(user.getAccerelator().size() -1).getTimestamp());
                    createChart();

                } catch (Exception e) {
                    Log.e("**********SYNC ERROR***", "ERROR: onDataChange() " + e);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        showHideGraphs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(showHideGraphs.getText().equals("Hıde Graphs")) {
                    showHideGraphs.setText("Show Graphs");
                    heartCard.setVisibility(View.GONE);
                    oxygenCard.setVisibility(View.GONE);
                    tempCard.setVisibility(View.GONE);
                    bodyTempCard.setVisibility(View.GONE);
                    humidityCard.setVisibility(View.GONE);
                } else if(showHideGraphs.getText().equals("Show Graphs")) {
                    showHideGraphs.setText("Hıde Graphs");
                    heartCard.setVisibility(View.VISIBLE);
                    oxygenCard.setVisibility(View.VISIBLE);
                    tempCard.setVisibility(View.VISIBLE);
                    bodyTempCard.setVisibility(View.VISIBLE);
                    humidityCard.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private String getDate(long time) {
        Date date = new Date(time*1000L); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm, dd MMM yyyy "); // the format of your date
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+3"));

        return sdf.format(date);
    }

    private Date getDateObject(long time) {
        return new Date(time*1000L);
    }

    private void createChart() {

        //**************************************
        //heart graph
        //**************************************
        LineGraphSeries<DataPoint> seriesheart = new LineGraphSeries<>();
        for(int i = 0; i < user.getHumidity().size(); i++) {
            seriesheart.appendData(new DataPoint(getDateObject(user.getHeart().get(i).getTimestamp()), (double)user.getHeart().get(i).getData()), true, user.getHeart().size());
        }
        seriesheart.setTitle("Pulse");
        heartGraph.addSeries(seriesheart);

        heartGraph.getLegendRenderer().setVisible(true);
        heartGraph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        heartGraph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    Format formatter = new SimpleDateFormat(/*"yyyy-MM-dd*/" HH:mm:ss");
                    return formatter.format(value);
                }
                return super.formatLabel(value, isValueX);
            }
        });

        //**************************************
        //humidity graph
        //**************************************
        LineGraphSeries<DataPoint> serieshumidity = new LineGraphSeries<>();
        for(int i = 0; i < user.getHumidity().size(); i++) {
            serieshumidity.appendData(new DataPoint(getDateObject(user.getHumidity().get(i).getTimestamp()), (double)user.getHumidity().get(i).getData()), true, user.getHumidity().size());
        }
        serieshumidity.setTitle("Humidity");
        humidityGraph.addSeries(serieshumidity);

        humidityGraph.getLegendRenderer().setVisible(true);
        humidityGraph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        humidityGraph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    Format formatter = new SimpleDateFormat(/*"yyyy-MM-dd*/" HH:mm:ss");
                    return formatter.format(value);
                }
                return super.formatLabel(value, isValueX);
            }
        });


        //**************************************
        //Outside temperature graph
        //**************************************
        LineGraphSeries<DataPoint> seriestemp = new LineGraphSeries<>();
        for(int i = 0; i < user.getTemperature().size(); i++) {
            seriestemp.appendData(new DataPoint(getDateObject(user.getTemperature().get(i).getTimestamp()), user.getTemperature().get(i).getData()), true, user.getTemperature().size());
        }
        seriestemp.setTitle("Temperature(outside)");
        tempGraph.addSeries(seriestemp);

        tempGraph.getLegendRenderer().setVisible(true);
        tempGraph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        tempGraph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    Format formatter = new SimpleDateFormat(/*"yyyy-MM-dd*/" HH:mm:ss");
                    return formatter.format(value);
                }
                return super.formatLabel(value, isValueX);
            }
        });

        //**************************************
        //Saturation graph
        //**************************************

        LineGraphSeries<DataPoint> seriesoxygen = new LineGraphSeries<>();
        for(int i = 0; i < user.getOxygen().size(); i++) {
            serieshumidity.appendData(new DataPoint(getDateObject(user.getOxygen().get(i).getTimestamp()), (double)user.getOxygen().get(i).getData()), true, user.getOxygen().size());
        }
        seriesoxygen.setTitle("Saturation");
        oxygenGraph.addSeries(seriesoxygen);

        oxygenGraph.getLegendRenderer().setVisible(true);
        oxygenGraph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        oxygenGraph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    Format formatter = new SimpleDateFormat(/*"yyyy-MM-dd*/" HH:mm:ss");
                    return formatter.format(value);
                }
                return super.formatLabel(value, isValueX);
            }
        });

        //**************************************
        //Body temperature graph
        //**************************************
        LineGraphSeries<DataPoint> seriestempbody = new LineGraphSeries<>();
        for(int i = 0; i < user.getTemperaturebody().size(); i++) {
            seriestempbody.appendData(new DataPoint(getDateObject(user.getTemperaturebody().get(i).getTimestamp()), user.getTemperaturebody().get(i).getData()), true, user.getTemperaturebody().size());
        }
        seriestempbody.setTitle("Temperature(Body)");
        bodytempGraph.addSeries(seriestempbody);

        bodytempGraph.getLegendRenderer().setVisible(true);
        bodytempGraph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        bodytempGraph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    Format formatter = new SimpleDateFormat(/*"yyyy-MM-dd*/" HH:mm:ss");
                    return formatter.format(value);
                }
                return super.formatLabel(value, isValueX);
            }
        });




    }
}