package dk.iha.itsmap.grp11662.telecare.app;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.sql.Date;
import java.util.ArrayList;

import dk.iha.itsmap.grp11662.telecare.app.model.Measurement;

public class MyMeasurementArrayAdapter extends BaseAdapter {
    private ArrayList<Measurement> measurements;
    private Context masterActivity; //Måske skal det være et fragment?

    public MyMeasurementArrayAdapter(ArrayList<Measurement> measurementItems, Activity activity) {
        measurements = measurementItems;
        masterActivity = activity.getApplicationContext();
    }

    @Override
    public int getCount() {
        return measurements.size();
    }

    @Override
    public Object getItem(int i) {
        return measurements.get(i);
    }

    @Override
    public long getItemId(int i) {
        return measurements.get(i).describeContents();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View convertView = view;
        if (view == null) {
            convertView = LayoutInflater.from(masterActivity).inflate(R.layout.mymeasurement_listitem,null);
        }

        Measurement measurementToPresentInList = (Measurement)this.getItem(position);

        TextView measurementListDate = (TextView) convertView.findViewById(R.id.txvListDate);
        TextView measurementListComment = (TextView) convertView.findViewById(R.id.txvListComments);

        measurementListDate.setText(measurementToPresentInList.getDate());
        measurementListComment.setText(measurementToPresentInList.getComments());

        return convertView;
    }
}
