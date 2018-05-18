package com.james.workouttracker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class WorkoutLogListAdapter extends ArrayAdapter<Workout> {

    private Context mContext;
    private int mResource;

    public WorkoutLogListAdapter(Context context, int resource, ArrayList<Workout> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String exerciseName = getItem(position).getExerciseName();
        String rep1 = Integer.toString(getItem(position).getRep1());
        String rep2 = Integer.toString(getItem(position).getRep2());
        String rep3 = Integer.toString(getItem(position).getRep3());
        String weight = Integer.toString(getItem(position).getWeight());

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvRep1 = (TextView) convertView.findViewById(R.id.rep1);
        TextView tvRep2 = (TextView) convertView.findViewById(R.id.rep2);
        TextView tvRep3 = (TextView) convertView.findViewById(R.id.rep3);
        TextView tvWeight = (TextView) convertView.findViewById(R.id.weight);

        tvRep1.setText(rep1);
        tvRep2.setText(rep2);
        tvRep3.setText(rep3);
        tvWeight.setText(weight);

        return convertView;
    }
}
