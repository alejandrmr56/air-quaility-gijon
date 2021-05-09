package com.alejandromartinezremis.airquailitygijon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.alejandromartinezremis.airquailitygijon.logic.ListViewItem;

import java.util.ArrayList;
import java.util.List;

public class StationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station);

        ((TextView)findViewById(R.id.textViewStationName)).setText("[Station name]"); //TODO: Remove this
        ((TextView)findViewById(R.id.textViewAirQualityDescription)).setText("[Air quality description]"); //TODO: Remove this
        List<ListViewItem> listViewItems = new ArrayList<>();
        listViewItems.add(new ListViewItem("Test Name", "Test description")); //TODO: Remove this
        listViewItems.add(new ListViewItem("Test Name 2", "Test description 2")); //TODO: Remove this
        ((ListView)findViewById(R.id.listView)).setAdapter(new StationAdapter(listViewItems, this));
    }
}