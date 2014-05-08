package dk.iha.itsmap.grp11662.handin03.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class ContentFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        AndroidVersion itemWithDetail;
        itemWithDetail = savedInstanceState.getParcelable("data");

        if(itemWithDetail != null)
        {
            ((EditText) getActivity().findViewById(R.id.codeName)).setText(itemWithDetail.getCodeName());
            ((EditText) getActivity().findViewById(R.id.version)).setText(itemWithDetail.getVersion());
            ((EditText) getActivity().findViewById(R.id.description)).setText(itemWithDetail.getDescription());
        }
        return inflater.inflate(R.layout.fragment_content, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}

