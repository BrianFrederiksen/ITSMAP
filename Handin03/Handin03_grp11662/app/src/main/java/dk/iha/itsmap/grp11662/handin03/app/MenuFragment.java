package dk.iha.itsmap.grp11662.handin03.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class MenuFragment extends Fragment  {

    private boolean isTwoPane = false;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        if (getActivity().findViewById(R.id.content_container) != null) {
            isTwoPane = true;
        }

        final ListView choiceMenuList = (ListView) getActivity().findViewById(R.id.choice_menu_list);
        final ArrayList<AndroidVersion> list = getAndroidVersList();
        choiceMenuList.setAdapter(new AndroidVersionArrayAdapter(getActivity(), list));

        choiceMenuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AndroidVersion androidVersion = (AndroidVersion) choiceMenuList.getItemAtPosition(position);
                Toast.makeText(getActivity(),androidVersion.getCodeName(),Toast.LENGTH_SHORT).show();

                if (isTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putParcelable("data", androidVersion);
                    ContentFragment contentFragment = new ContentFragment();
                    contentFragment.setArguments(arguments);
                    getFragmentManager().beginTransaction().
                            replace(R.id.content_container, contentFragment).commit();
                } else {
                    Intent contentIntent = new Intent(getActivity(), ContentActivity.class);
                    contentIntent.putExtra("data", androidVersion);
                    startActivity(contentIntent);
                }


            }
        });

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    public ArrayList<AndroidVersion> getAndroidVersList() {
        ArrayList<AndroidVersion> arrayList = new ArrayList<>();

        arrayList.add(new AndroidVersion("Cupcake","Android 1.5","On 27 April 2009, the Android 1.5 update was released, based on Linux kernel 2.6.27.[28][29] This was the first release to officially use a codename based on a dessert item (\"Cupcake\"), a theme which would be used for all releases henceforth."));
        arrayList.add(new AndroidVersion("Donut","Android 1.6","On 15 September 2009, the Android 1.6 SDK – dubbed Donut – was released, based on Linux kernel 2.6.29."));
        arrayList.add(new AndroidVersion("Eclair","Android 2.0","On 26 October 2009, the Android 2.0 SDK – codenamed Eclair – was released, based on Linux kernel 2.6.29."));
        arrayList.add(new AndroidVersion("Froyo","Android 2.2","On 20 May 2010, the SDK for Android 2.2 (Froyo, short for frozen yogurt) was released, based on Linux kernel 2.6.32."));
        arrayList.add(new AndroidVersion("Gingerbread","Android 2.3","On 6 December 2010, the Android 2.3 (Gingerbread) SDK was released, based on Linux kernel 2.6.35."));
        arrayList.add(new AndroidVersion("Honeycomb","Android 3.0","On 22 February 2011, the Android 3.0 (Honeycomb) SDK – the first tablet-only Android update – was released, based on Linux kernel 2.6.36. The first device featuring this version, the Motorola Xoom tablet, was released on 24 February 2011."));
        arrayList.add(new AndroidVersion("Ice Cream Sandwich","Android 4.0","The SDK for Android 4.0.1 (Ice Cream Sandwich), based on Linux kernel 3.0.1, was publicly released on 19 October 2011. Google's Gabe Cohen stated that Android 4.0 was \"theoretically compatible\" with any Android 2.3.x device in production at that time.[77] The source code for Android 4.0 became available on 14 November 2011. Ice Cream Sandwich was the last version to officially support Adobe Systems' Flash player."));
        arrayList.add(new AndroidVersion("Jelly Bean","Android 4.1","Google announced Android 4.1 (Jelly Bean) at the Google I/O conference on 27 June 2012. Based on Linux kernel 3.0.31, Jelly Bean was an incremental update with the primary aim of improving the functionality and performance of the user interface. The performance improvement involved \"Project Butter\", which uses touch anticipation, triple buffering, extended vsync timing and a fixed frame rate of 60 fps to create a fluid and \"buttery-smooth\" UI.[91] Android 4.1 Jelly Bean was released to the Android Open Source Project on 9 July 2012,[92] and the Nexus 7 tablet, the first device to run Jelly Bean, was released on 13 July 2012."));
        arrayList.add(new AndroidVersion("KitKat","Android 4.4","Google announced Android 4.4 KitKat on 3 September 2013.[121] The release had long been expected by technology bloggers to be numbered 5.0 and called \"Key Lime Pie\".[122] KitKat debuted on Google's Nexus 5 on 31 October 2013, and has been optimised to run on a greater range of devices than earlier Android Version, having 512 MB of RAM as a recommended minimum; those improvements were known as \"Project Svelte\" internally at Google.[123] The required minimum amount of RAM available to Android is 340 MB, and all devices with less than 512 MB of RAM must report themselves as \"low RAM\" devices."));

        return arrayList;
    };


}
