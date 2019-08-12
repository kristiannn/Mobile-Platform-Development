
//
// Name                 Kristiyan Nenov
// Student ID           S1512113
// Programme of Study   Computer Games(Software Development)


package com.example.mpd_kristiyannenov_s1512113;

import java.util.ArrayList;
import java.util.Collections;


public class ParsedDataSorter {

    ArrayList<ParsedData> pdEntry = new ArrayList<ParsedData>();


    public ParsedDataSorter(ArrayList<ParsedData> pdEntry)
    {
        this.pdEntry = pdEntry;
    }

    public ArrayList<ParsedData> sortByMagnitude()
    {
        Collections.sort(pdEntry, ParsedData.magnitudeComparator);
        return pdEntry;
    }
    public ArrayList<ParsedData> sortByLocation()
    {
        Collections.sort(pdEntry, ParsedData.locationComparator);
        return pdEntry;
    }
    public ArrayList<ParsedData> sortByDepth()
    {
        Collections.sort(pdEntry, ParsedData.depthComparator);
        return pdEntry;
    }

    public ArrayList<ParsedData> sortByLong()
    {
        Collections.sort(pdEntry, ParsedData.longComparator);
        return pdEntry;
    }

    public ArrayList<ParsedData> sortByLat()
    {
        Collections.sort(pdEntry, ParsedData.latComparator);
        return pdEntry;
    }
}
