package com.alejandromartinezremis.airquailitygijon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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


        ((TextView)findViewById(R.id.textViewAirQualityDescription)).setText("[Air quality description]"); //TODO: Remove this
    }

    private void loadData(){
        ((TextView)findViewById(R.id.textViewStationName)).setText(this.getIntent().getStringExtra("stationName"));
        ((ImageView)findViewById(R.id.imageViewPicture)).setImageResource(this.getIntent().getIntExtra("stationPictureId", -1));
        airStation = (AirStation)this.getIntent().getSerializableExtra("station");

        ((ImageView)findViewById(R.id.imageViewCircle)).setImageResource(Utils.getDrawableIdForQualityCircle(airStation.getAirQuality()));

        final List<ListViewItem> listViewItems = new ArrayList<>();
        listViewItems.add(new ListViewItem(getString(R.string.date), airStation.getFecha())); //TODO: Include time (periodo(?), UTC)
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

        ((ListView)findViewById(R.id.listView)).setAdapter(new StationAdapter(listViewItems, this));
        //TODO: Add onClick listener to location (Google Maps)
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