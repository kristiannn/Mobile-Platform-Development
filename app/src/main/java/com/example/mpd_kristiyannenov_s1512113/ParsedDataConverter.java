


// Name                 Kristiyan Nenov
// Student ID           S1512113
// Programme of Study   Computer Games(Software Development)


package com.example.mpd_kristiyannenov_s1512113;

import android.util.Log;

import java.util.ArrayList;

public class ParsedDataConverter {


    ParsedDataConverter (ArrayList<String> ari)
    {

    }

    public String getLocationFromXML (ArrayList<String> ari, int i)
    {
        String aristringy = "";
        String stringy = "";
        int startIndex = 0;
        int endIndex = 0;
        //for (int i = 0 ; i < ari.size() ; i++)
        //{
            /* +10 is because it finds the "L" letter in location
            "Location:" is 9 chars, 1 more for the space after that is added
            -1 is because it finds the ";" char's location, 1 is removed from that
            because there is always a space before it, we don't need to add that space.
            */
            startIndex = ari.get(i).indexOf("Location:") + 10;
            endIndex = ari.get(i).indexOf("; Lat/long") - 1;
            aristringy = ari.get(i).toString();
            stringy = aristringy.substring(startIndex, endIndex);
        //}
        return stringy;
    }

    public float getMagnitudeFromXML (ArrayList<String> ari, int i)
    {
        float magni = 0;
        String aristringy = "";
        String stringy = "";

        int startIndex = 0;
        //for (int i = 0 ; i < ari.size() ; i++)
        //{
            startIndex = ari.get(i).indexOf("Magnitude:") + 11;
            aristringy = ari.get(i).toString();
            stringy = aristringy.substring(startIndex);
            magni = Float.parseFloat(stringy);
        //}
        return magni;
    }

    public float getDepthFromXML (ArrayList<String> ari, int i)
    {
        float depth = 0;
        String aristringy = "";
        String stringy = "";
        int startIndex = 0;
        int endIndex = 0;

        //for (int i = 0 ; i < ari.size() ; i++)
        //{
            startIndex = ari.get(i).indexOf("Depth:") + 7;
            endIndex = ari.get(i).indexOf("km") - 1;
            aristringy = ari.get(i).toString();
            stringy = aristringy.substring(startIndex, endIndex); // error tuk
            depth = Float.parseFloat(stringy);
        //}
        return depth;
    }
}
