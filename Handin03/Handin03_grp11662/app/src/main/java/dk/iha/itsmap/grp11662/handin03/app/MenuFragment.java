package dk.iha.itsmap.grp11662.handin03.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


public class MenuFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ListView choiceMenuList = (ListView) container.findViewById(R.id.choice_menu_list);

        return inflater.inflate(R.layout.fragment_menu, container, false);
    }
}
