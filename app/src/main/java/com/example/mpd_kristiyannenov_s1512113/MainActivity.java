/*  Starter project for Mobile Platform Development in Semester B Session 2018/2019
    You should use this project as the starting point for your assignment.
    This project simply reads the data from the required URL and displays the
    raw data in a TextField
*/

//
// Name                 Kristiyan Nenov
// Student ID           S1512113
// Programme of Study   Computer Games(Software Development)


package com.example.mpd_kristiyannenov_s1512113;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;

import com.example.mpd_kristiyannenov_s1512113.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.example.mpd_kristiyannenov_s1512113.ParsedData;
import com.example.mpd_kristiyannenov_s1512113.ParsedDataConverter;
import com.example.mpd_kristiyannenov_s1512113.MapsActivity;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    private Button button1;
    private Button button2;
    private Button button3;

    ListView listView;
    SearchView searchView;
    ArrayList<String> title;
    ArrayList<String> description;
    ArrayList<Float> locationLat = new ArrayList<Float>();
    ArrayList<Float> locationLong = new ArrayList<Float>();
    public ArrayList<ParsedData> arrayPD = new ArrayList<ParsedData>();
    ArrayList<ParsedData> searchResults = new ArrayList<ParsedData>();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.item1:
                SortByLocation();
                break;
            case R.id.item2:
                SortByMagnitude();
                break;
            case R.id.item3:
                SortByDepth();
                break;
            case R.id.item4:
                SortByShallowest();
                break;
            case R.id.item5:
                SortByNortherly();
                break;
            case R.id.item6:
                SortBySoutherly();
                break;
            case R.id.item7:
                SortByWesterly();
                break;
            case R.id.item8:
                SortByEasterly();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);

        title = new ArrayList<String>();
        description = new ArrayList<String>();

        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(this);
        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(this);
        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(this);

        searchView = (SearchView) findViewById(R.id.search_bar);
        searchView.setQueryHint("Search for a location");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchLocation(s);
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), item_info.class);
                //intent.putExtra("clickedItem", position);
                intent.putExtra("description", arrayPD.get(position).getLocation() +
                        " , Magnitude: " + arrayPD.get(position).getMagnitude());
                intent.putExtra("location", arrayPD.get(position).getLocation());
                intent.putExtra("magnitude", arrayPD.get(position).getMagnitude());
                intent.putExtra("depth", arrayPD.get(position).getDepth());
                intent.putExtra("locLat", arrayPD.get(position).getLocationLat());
                intent.putExtra("locLong", arrayPD.get(position).getLocationLong());
                startActivity(intent);
            }
        });
        startProgress();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dropdown_menu, menu);
        return true;
    }

    public void onClick(View aview)
    {

        switch (aview.getId()) {

            case R.id.button1:
                SortByLocation();
                break;

            case R.id.button2:
                SortByMagnitude();
                break;

            case R.id.button3:
                SortByDepth();
                break;

            default:
                break;
        }

    }

    public void startProgress()
    {
        // Run network access on a separate thread;
        //new Thread(new Task(urlSource)).start();
        new ProcessInBackground().execute();
    } //


    //Must call AFTER description array is fully populated!
    void arrayToObjects()
    {
        ParsedDataConverter convPD = new ParsedDataConverter(description);

        for (int i = 0 ; i < description.size() ; i++)
        {
            arrayPD.add(new ParsedData(convPD.getLocationFromXML(description, i),
                    convPD.getMagnitudeFromXML(description, i),
                    convPD.getDepthFromXML(description, i), locationLat.get(i), locationLong.get(i)));
        }

        AdaptClass adapterView = new AdaptClass(this,R.layout.list_items, arrayPD);
        listView.setAdapter(adapterView);
    }


    //Stats to display
    void stats()
    {
        Float magnitudeAverage = 0f;
        Float depthAverage = 0f;
        for (int i = 0 ; i < arrayPD.size() ; i++)
        {
            magnitudeAverage += arrayPD.get(i).getMagnitude();
        }

        for (int i = 0 ; i < arrayPD.size() ; i++)
        {
            depthAverage += arrayPD.get(i).getDepth();
        }

        magnitudeAverage /= arrayPD.size();
        depthAverage /= arrayPD.size();
    }

    //Searches for all locations containing the currently written characters in search bar
    void searchLocation(String s)
    {
        //if there is something written
        if (s!= null)
        {
            searchResults.clear(); //clear from previous searches, user may delete characters for example
            for (int i = 0 ; i < arrayPD.size() ; i++)
            {
                //checks if current written characters match with anything from all locations
                //all are transformed to lowercase so all results are gathered
                if (arrayPD.get(i).getLocation().toLowerCase().contains(s.toLowerCase()))
                {
                    searchResults.add(arrayPD.get(i)); //if anything is found - add it to results
                }
            }
            //displays a different list, searchresults
            AdaptClass adapterView = new AdaptClass(this,R.layout.list_items, searchResults);
            listView.setAdapter(adapterView);
        }
        else
        {
            //if nothing is being searched for - displays usual list of all items
            AdaptClass adapterView = new AdaptClass(this,R.layout.list_items, arrayPD);
            listView.setAdapter(adapterView);
        }
    }

    //This should be done in the background if possible
    void SortByLocation()
    {
        //Convert objects to array
        ParsedDataSorter pdSorter = new ParsedDataSorter(arrayPD);
        pdSorter.sortByLocation();
        AdaptClass adapterView = new AdaptClass(this,R.layout.list_items, arrayPD);
        listView.setAdapter(adapterView);
    }

    void SortByMagnitude()
    {
        //Convert objects to array
        ParsedDataSorter pdSorter = new ParsedDataSorter(arrayPD);
        pdSorter.sortByMagnitude();
        AdaptClass adapterView = new AdaptClass(this,R.layout.list_items, arrayPD);
        listView.setAdapter(adapterView);
    }
    void SortByDepth()
    {
        //Convert objects to array
        ParsedDataSorter pdSorter = new ParsedDataSorter(arrayPD);
        pdSorter.sortByDepth();
        AdaptClass adapterView = new AdaptClass(this,R.layout.list_items, arrayPD);
        listView.setAdapter(adapterView);
    }

    void SortByShallowest()
    {
        //Convert objects to array
        ParsedDataSorter pdSorter = new ParsedDataSorter(arrayPD);
        pdSorter.sortByDepth();
        AdaptClass adapterView = new AdaptClass(this,R.layout.list_items, arrayPD);
        listView.setAdapter(adapterView);
        Collections.reverse(arrayPD);
    }


    void SortByEasterly()
    {
        //Convert objects to array
        ParsedDataSorter pdSorter = new ParsedDataSorter(arrayPD);
        pdSorter.sortByLong();
        AdaptClass adapterView = new AdaptClass(this,R.layout.list_items, arrayPD);
        listView.setAdapter(adapterView);
    }

    void SortByWesterly()
    {
        //Convert objects to array
        ParsedDataSorter pdSorter = new ParsedDataSorter(arrayPD);
        pdSorter.sortByLong();
        AdaptClass adapterView = new AdaptClass(this,R.layout.list_items, arrayPD);
        listView.setAdapter(adapterView);
        Collections.reverse(arrayPD);
    }

    void SortByNortherly()
    {
        //Convert objects to array
        ParsedDataSorter pdSorter = new ParsedDataSorter(arrayPD);
        pdSorter.sortByLat();
        AdaptClass adapterView = new AdaptClass(this,R.layout.list_items, arrayPD);
        listView.setAdapter(adapterView);
    }

    void SortBySoutherly()
    {
        //Convert objects to array
        ParsedDataSorter pdSorter = new ParsedDataSorter(arrayPD);
        pdSorter.sortByLat();
        AdaptClass adapterView = new AdaptClass(this,R.layout.list_items, arrayPD);
        listView.setAdapter(adapterView);
        Collections.reverse(arrayPD);
    }

    public InputStream getInputStream(URL url)
    {
        try
        {
            //openConnection() returns instance that represents a connection to the remote object referred to by the URL
            //getInputStream() returns a stream that reads from the open connection
            Log.e("Message", "Input stream successful;");
            return url.openConnection().getInputStream();
        }
        catch (IOException e)
        {
            Log.e("Message", "Input stream NOT successful;");
            return null;
        }
    }

    public class ProcessInBackground extends AsyncTask<Integer, Void, Exception>
    {
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

        Exception exception = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setMessage("Loading, please wait..");
            progressDialog.show();
            arrayPD.clear();
            title.clear();
            description.clear();
        }

        @Override
        protected Exception doInBackground(Integer... params) {

            try
            {
                URL url = new URL("http://quakes.bgs.ac.uk/feeds/MhSeismology.xml");

                //new instance of PPFactory, which is used to create pull parsers
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

                factory.setNamespaceAware(false);

                //new pull parser instance
                XmlPullParser xpp = factory.newPullParser();

                // configure xml link and encode type
                xpp.setInput(getInputStream(url), "UTF_8");

                //bool to check if we're inside an item tag, since "title" (for example) can be found outside items too
                boolean insideItem = false;

                // returns the current tag (END_DOCUMENT/START_TAG, etc)
                int eventType = xpp.getEventType(); //loop control variable

                //Parsing loop
                while (eventType != XmlPullParser.END_DOCUMENT)
                {
                    //if we enter the start tag of the document
                    if (eventType == XmlPullParser.START_TAG)
                    {
                        //when we find an item
                        if (xpp.getName().equalsIgnoreCase("item"))
                        {
                            insideItem = true;
                        }
                        //.. title
                        else if (xpp.getName().equalsIgnoreCase("title"))
                        {
                            //checks if this title tag is within an item tag
                            if (insideItem)
                            {
                                // extract the text between <title> and </title>
                                title.add(xpp.nextText());
                            }
                        }
                        //.. description
                        else if (xpp.getName().equalsIgnoreCase("description"))
                        {
                            if (insideItem)
                            {
                                // extract the text between <link> and </link>
                                description.add(xpp.nextText());
                            }
                        }

                        //.. coordinates
                        else if (xpp.getName().equalsIgnoreCase("geo:lat"))
                        {
                            if (insideItem)
                            {
                                // extract the text between <link> and </link>
                                locationLat.add(Float.parseFloat(xpp.nextText()));
                            }
                        }

                        //.. coordinates
                        else if (xpp.getName().equalsIgnoreCase("geo:long"))
                        {
                            if (insideItem)
                            {
                                // extract the text between <link> and </link>
                                locationLong.add(Float.parseFloat(xpp.nextText()));
                            }
                        }
                    }
                    //if an end tag is found and that end tag is ïtem", insideItem is back to false
                    //in order to not gather irrelevant information from outside of item (such as titles)
                    else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item"))
                    {
                        insideItem = false;
                    }

                    eventType = xpp.next(); //move to next element
                }


            }
            catch (MalformedURLException e)
            {
                exception = e;
            }
            catch (XmlPullParserException e)
            {
                exception = e;
            }
            catch (IOException e)
            {
                exception = e;
            }

            return exception;
        }

        @Override
        protected void onPostExecute(Exception s) {
            super.onPostExecute(s);
            //After all info has been parsed, convert to objects
            arrayToObjects();
            progressDialog.dismiss();
        }
    }


}
