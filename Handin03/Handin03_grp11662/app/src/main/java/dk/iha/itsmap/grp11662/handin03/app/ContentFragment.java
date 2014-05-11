package dk.iha.itsmap.grp11662.handin03.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class ContentFragment extends Fragment{

    private AndroidVersion itemWithDetail;

    public ContentFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey("data")) {
            itemWithDetail = getArguments().getParcelable("data");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_content, container, false);

        if(itemWithDetail != null)
        {
            ((EditText) rootView.findViewById(R.id.codeName)).setText(itemWithDetail.getCodeName());
            ((EditText) rootView.findViewById(R.id.version)).setText(itemWithDetail.getVersion());
            ((EditText) rootView.findViewById(R.id.description)).setText(itemWithDetail.getDescription());
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}

