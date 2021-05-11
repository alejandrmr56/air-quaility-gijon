package com.alejandromartinezremis.airquailitygijon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alejandromartinezremis.airquailitygijon.logic.AirStation;
import com.alejandromartinezremis.airquailitygijon.logic.ListViewItem;

import java.util.ArrayList;
import java.util.List;

public class StationActivity extends AppCompatActivity {

    AirStation airStation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station);

        getSupportActionBar().setHomeButtonEnabled(true);

        loadData();
    }

    private void loadData(){
        airStation = (AirStation)this.getIntent().getSerializableExtra("station");
        //Load station name
        ((TextView)findViewById(R.id.textViewStationName)).setText(getString(Utils.getStringIdForStationName(airStation.getEstacion())));
        //Load station picture
        ((ImageView)findViewById(R.id.imageViewPicture)).setImageResource(Utils.getDrawableIdForStationPicture(airStation.getEstacion()));
        //Load air quality circle
        ((ImageView)findViewById(R.id.imageViewCircle)).setImageResource(Utils.getDrawableIdForQualityCircle(airStation.getAirQuality()));

        //Loads air quality description. Only displays known component qualities.
        String str = getString(R.string.air_quality)+": " +formatQuality(airStation.getAirQuality()) +"\n";
        AirStation.Quality[] qualities = new AirStation.Quality[5];
        qualities[0] = airStation.getPm10Quality();
        qualities[1] = airStation.getSo2Quality();
        qualities[2] = airStation.getNo2Quality();
        qualities[3] = airStation.getCoQuality();
        qualities[4] = airStation.getO3Quality();
        String[] componentNames = new String[5];
        componentNames[0] = getString(R.string.pm10);
        componentNames[1] = getString(R.string.so2);
        componentNames[2] = getString(R.string.no2);
        componentNames[3] = getString(R.string.co);
        componentNames[4] = getString(R.string.o3);

        for(int i = 0; i < qualities.length; i++)
            if(!qualities[i].equals(AirStation.Quality.UNKNOWN))
                str += "\n" +componentNames[i] +": " +formatQuality(qualities[i]);

        ((TextView) findViewById(R.id.textViewAirQualityDescription)).setText(str);

        //Loads the list with the components of the air station
        final List<ListViewItem> listViewItems = new ArrayList<>();
        String dateAndTime = airStation.getFecha() +"T" +airStation.getPeriodo() +":00";
        listViewItems.add(new ListViewItem(getString(R.string.date), Utils.formatDate(dateAndTime)));
        listViewItems.add(new ListViewItem(getString(R.string.location), getString(R.string.location_message)));
        if(!Double.isNaN(airStation.getTmp()))
            listViewItems.add(new ListViewItem(getString(R.string.tmp), airStation.getTmp().toString() +" " +getString(R.string.temperature_unit)));
        if(!Double.isNaN(airStation.getLl()))
            listViewItems.add(new ListViewItem(getString(R.string.ll), airStation.getLl().toString() +" " +getString(R.string.precipitation_unit)));
        if(!Double.isNaN(airStation.getDd()))
            listViewItems.add(new ListViewItem(getString(R.string.dd), airStation.getDd().toString( )+" " +getString(R.string.wind_direction_unit) +" (" +formatWindDirection(airStation.getDd()) +")"));
        if(!Double.isNaN(airStation.getVv()))
            listViewItems.add(new ListViewItem(getString(R.string.vv), (airStation.getVv().doubleValue()*3.6) +" " +getString(R.string.wind_speed_unit)));
        if(!Double.isNaN(airStation.getRs()))
            listViewItems.add(new ListViewItem(getString(R.string.rs), airStation.getRs().toString() +" " +getString(R.string.solar_radiation_unit)));
        if(!Double.isNaN(airStation.getHr()))
            listViewItems.add(new ListViewItem(getString(R.string.hr), airStation.getHr().toString() +" " +getString(R.string.humidity_unit)));
        if(!Double.isNaN(airStation.getPrb()))
            listViewItems.add(new ListViewItem(getString(R.string.prb), airStation.getPrb().toString() +" " +getString(R.string.pressure_unit)));
        if(!airStation.getPm10().equals("null") && !airStation.getPm10().isEmpty())
            listViewItems.add(new ListViewItem(getString(R.string.pm10), airStation.getPm10() +" " +getString(R.string.unit_micro)));
        if(!airStation.getPm25().equals("null") && !airStation.getPm25().isEmpty())
            listViewItems.add(new ListViewItem(getString(R.string.pm25), airStation.getPm25() +" " +getString(R.string.unit_micro)));
        if(!Double.isNaN(airStation.getSo2()))
            listViewItems.add(new ListViewItem(getString(R.string.so2), airStation.getSo2().toString() +" " +getString(R.string.unit_micro)));
        if(!Double.isNaN(airStation.getNo()))
            listViewItems.add(new ListViewItem(getString(R.string.no), airStation.getNo().toString() +" " +getString(R.string.unit_micro)));
        if(!Double.isNaN(airStation.getNo2()))
            listViewItems.add(new ListViewItem(getString(R.string.no2), airStation.getNo2().toString() +" " +getString(R.string.unit_micro)));
        if(!airStation.getCo().equals("null") && !airStation.getCo().isEmpty())
            listViewItems.add(new ListViewItem(getString(R.string.co), airStation.getCo() +" " +getString(R.string.unit_milli)));
        if(!Double.isNaN(airStation.getO3()))
            listViewItems.add(new ListViewItem(getString(R.string.o3), airStation.getO3().toString() +" " +getString(R.string.unit_micro)));
        if(!Double.isNaN(airStation.getBen()))
            listViewItems.add(new ListViewItem(getString(R.string.ben), airStation.getBen().toString() +" " +getString(R.string.unit_micro)));
        if(!Double.isNaN(airStation.getTol()))
            listViewItems.add(new ListViewItem(getString(R.string.tol), airStation.getTol().toString() +" " +getString(R.string.unit_micro)));
        if(!Double.isNaN(airStation.getMxil()))
            listViewItems.add(new ListViewItem(getString(R.string.mxil), airStation.getMxil().toString() +" " +getString(R.string.unit_micro)));

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new StationAdapter(listViewItems, this));

        //
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, final long id) {
                ListViewItem property = (ListViewItem) listView.getItemAtPosition(position);

                if(property.getId().equals(getString(R.string.location))){
                    String str = "geo:" +airStation.getLatitud() +", " +airStation.getLongitud()
                            +"?q=" +airStation.getLatitud() +", " +airStation.getLongitud() +"(" +airStation.getTitulo() +")";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(str));
                    view.getContext().startActivity(intent);
                }
            }
        });
    }

    private String formatQuality(AirStation.Quality quality){
        switch (quality){
            case VERY_GOOD:
                return getString(R.string.air_quality_very_good);
            case GOOD:
                return getString(R.string.air_quality_good);
            case BAD:
                return getString(R.string.air_quality_bad);
            case VERY_BAD:
                return getString(R.string.air_quality_very_bad);
            default:
                return getString(R.string.air_quality_unknown);
        }
    }

    private String formatWindDirection(Double degrees){
        if(degrees >= 360 || degrees < 0) return "";

        if(degrees >= 338 || degrees <= 22) return getString(R.string.north);
        if(degrees >= 23 && degrees <= 67) return getString(R.string.north_east);
        if(degrees >= 68 && degrees <= 112) return getString(R.string.east);
        if(degrees >= 113 && degrees <= 157) return getString(R.string.south_east);
        if(degrees >= 158 && degrees <= 202) return getString(R.string.south);
        if(degrees >= 203 && degrees <= 247) return getString(R.string.south_west);
        if(degrees >= 248 && degrees <= 292) return getString(R.string.west);
        if(degrees >= 293 && degrees <= 337) return getString(R.string.north_west);
        return "";
    }
}