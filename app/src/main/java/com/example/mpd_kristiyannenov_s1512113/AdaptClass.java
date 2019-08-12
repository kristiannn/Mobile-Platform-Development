
//
// Name                 Kristiyan Nenov
// Student ID           S1512113
// Programme of Study   Computer Games(Software Development)


package com.example.mpd_kristiyannenov_s1512113;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdaptClass extends ArrayAdapter<ParsedData> {

    Context mContext;
    int mResource;

    public AdaptClass(Context context, int resource, ArrayList<ParsedData> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String location = getItem(position).getLocation();
        float magnitude = getItem(position).getMagnitude();
        float depth = getItem(position).getDepth();

        ParsedData pd = new ParsedData(location,magnitude,depth, 0, 0);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        TextView textViewLocation = (TextView) convertView.findViewById(R.id.location);
        TextView textViewMagnitude = (TextView) convertView.findViewById(R.id.magnitude);
        TextView textViewDepth = (TextView) convertView.findViewById(R.id.depth);

        textViewLocation.setText(location);
        textViewMagnitude.setText(Float.toString(magnitude));
        textViewDepth.setText(Float.toString(depth));

        return convertView;
    }
}
