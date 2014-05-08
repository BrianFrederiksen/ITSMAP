package dk.iha.itsmap.grp11662.handin03.app;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AndroidVersionArrayAdapter extends BaseAdapter {

    private ArrayList<AndroidVersion> data;
    private Activity activity;
    private LayoutInflater inflater = null;

    public AndroidVersionArrayAdapter(Activity a, ArrayList<AndroidVersion> result) {
        activity = a;
        data = result;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).describeContents();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(convertView == null) {
            view = LayoutInflater.from(activity).inflate(R.layout.android_list_layout,null);
        }

        TextView codeNameTextView = (TextView) view.findViewById(R.id.codeName_textView);
        TextView versionTextView = (TextView) view.findViewById(R.id.version_textView);

        codeNameTextView.setText(data.get(position).getCodeName());
        versionTextView.setText(data.get(position).getVersion());

        return view;
    }
}
