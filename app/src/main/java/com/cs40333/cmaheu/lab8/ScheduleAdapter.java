package com.cs40333.cmaheu.lab8;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


class ScheduleAdapter extends ArrayAdapter<Team> {
    ScheduleAdapter (Context context, ArrayList<Team> schedule) {
        super(context, com.cs40333.cmaheu.lab8.R.layout.schedule_item, schedule);
    }
    public View getView (int position, View convertView, ViewGroup parent) {
        LayoutInflater scheduleInflater = LayoutInflater.from(getContext());
        View scheduleView = scheduleInflater.inflate(com.cs40333.cmaheu.lab8.R.layout.schedule_item, parent, false);

        final Team matchItem = getItem(position);
        TextView teamName = (TextView) scheduleView.findViewById(com.cs40333.cmaheu.lab8.R.id.scheduleText);
        TextView dateName = (TextView) scheduleView.findViewById(com.cs40333.cmaheu.lab8.R.id.dateText);
        ImageView teamLogo = (ImageView) scheduleView.findViewById(com.cs40333.cmaheu.lab8.R.id.teamLogo);
        String mDrawableName = matchItem.getLogo();
        Context mycontext = getContext();
        int resID = mycontext.getResources().getIdentifier(mDrawableName , "drawable", mycontext.getPackageName());
        teamLogo.setImageResource(resID );
        teamName.setText(matchItem.getTeamName());
        dateName.setText(matchItem.getShortDate());

        return scheduleView;
    }
}
