
//
// Name                 Kristiyan Nenov
// Student ID           S1512113
// Programme of Study   Computer Games(Software Development)


package com.example.mpd_kristiyannenov_s1512113;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class item_info extends AppCompatActivity {

    ListView listView;
    ArrayList<String> infoArray = new ArrayList<String>();
    private Button button2;
    float locLat = 0;
    float locLong = 0;
    String titleMaps = "";
    String location = "";
    float magnitude = 0;
    float depth = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_info);

        Intent intent = getIntent();
        titleMaps = intent.getStringExtra("description");
        location = intent.getStringExtra("location");
        magnitude = intent.getFloatExtra("magnitude", 0);
        depth = intent.getFloatExtra("depth", 0);
        locLat = intent.getFloatExtra("locLat", 0);
        locLong = intent.getFloatExtra("locLong", 0);

        //Hard coded, info is static
        infoArray.add("Location: " + location);
        infoArray.add("Magnitude: " + magnitude);
        infoArray.add("Depth: " + depth);
        infoArray.add("Lateral Coordinate: " + locLat);
        infoArray.add("Longitudinal Coordinate: " + locLong);


        listView = (ListView) findViewById(R.id.listView2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(item_info.this,android.R.layout.simple_list_item_1, infoArray);
        listView.setAdapter(adapter);

        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapMove();
            }
        });


       /* listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                //intent.putExtra("clickedItem", position);
                //intent.putExtra("title", arrayPD.get(position).getLocation() +
                //        " , Magnitude: " + arrayPD.get(position).getMagnitude());
               //intent.putExtra("locLat", arrayPD.get(position).getLocationLat());
                //intent.putExtra("locLong", arrayPD.get(position).getLocationLong());
                startActivity(intent);
            }
        });*/


    }

    public void mapMove()
    {
        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
        intent.putExtra("description", location +
                " , Magnitude: " + magnitude) ;
        intent.putExtra("location", location);
        intent.putExtra("magnitude",magnitude);
        intent.putExtra("depth", depth);
        intent.putExtra("locLat", locLat);
        intent.putExtra("locLong", locLong);
        startActivity(intent);
    }


}
