

//
// Name                 Kristiyan Nenov
// Student ID           S1512113
// Programme of Study   Computer Games(Software Development)


package com.example.mpd_kristiyannenov_s1512113;

import java.util.ArrayList;
import java.util.Comparator;

public class ParsedData {
    public ArrayList<String> xmlData;
    public String description; // Unused for now!
    public String location;
    public float magnitude;
    public float depth;
    public float locLat;
    public float locLong;

    public ParsedData(String location, float magnitude, float depth, float locLat, float locLong)
    {
        this.location = location;
        this.magnitude = magnitude;
        this.depth = depth;
        this.locLat = locLat;
        this.locLong = locLong;
    }

    //Unused for now! Delete if no use!
    public String getDescription()
    {
        return description;
    }

    public String getLocation()
    {
        return location;
    }

    public float getMagnitude()
    {
        return magnitude;
    }

    public float getDepth()
    {
        return depth;
    }
    public float getLocationLat()
    {
        return locLat;
    }

    public float getLocationLong()
    {
        return locLong;
    }

    public static Comparator<ParsedData> magnitudeComparator = new Comparator<ParsedData>() {
        @Override
        public int compare(ParsedData pd1, ParsedData pd2) {
            return (pd2.getMagnitude() < pd1.getMagnitude() ? -1 :
                    (pd2.getMagnitude() == pd1.getMagnitude() ? 0 : 1));
        }
    };

    public static Comparator<ParsedData> locationComparator = new Comparator<ParsedData>() {
        @Override
        public int compare(ParsedData pd1, ParsedData pd2) {
            return (int) (pd1.getLocation().compareTo(pd2.getLocation()));
        }
    };

    public static Comparator<ParsedData> depthComparator = new Comparator<ParsedData>() {
        @Override
        public int compare(ParsedData pd1, ParsedData pd2) {
            return (pd2.getDepth() < pd1.getDepth() ? -1 :
                    (pd2.getDepth() == pd1.getDepth() ? 0 : 1));
        }
    };

    public static Comparator<ParsedData> longComparator = new Comparator<ParsedData>() {
        @Override
        public int compare(ParsedData pd1, ParsedData pd2) {
            return (pd2.getLocationLong() < pd1.getLocationLong() ? -1 :
                    (pd2.getLocationLong() == pd1.getLocationLong() ? 0 : 1));
        }
    };

    public static Comparator<ParsedData> latComparator = new Comparator<ParsedData>() {
        @Override
        public int compare(ParsedData pd1, ParsedData pd2) {
            return (pd2.getLocationLat() < pd1.getLocationLat() ? -1 :
                    (pd2.getLocationLat() == pd1.getLocationLat() ? 0 : 1));
        }
    };
}


