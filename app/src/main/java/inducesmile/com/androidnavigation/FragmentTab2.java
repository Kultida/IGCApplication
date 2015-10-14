package inducesmile.com.androidnavigation;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

/**
 * Created by User on 29/9/2558.
 */
public class FragmentTab2 extends Fragment {
    //---the images to display---
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragmenttab2, container, false);

        final SwipeRefreshLayout swipeView = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);

        final GridView gridview = (GridView) rootView.findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(getActivity()));
      //  gridview.setOnItemClickListener(new OnItemClickListener() {
          //  public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // Send intent to SingleViewActivity
           //     Intent i = new Intent(getApplicationContext(), SingleViewActivity.class);

                // Pass image index
           //     i.putExtra("id", position);
          //      startActivity(i);
        //    }
      //  });
        //  swipeView.setColorScheme(android.R.color.holo_blue_dark, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_green_light);
        swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeView.setRefreshing(true);
                Log.d("Swipe", "Refreshing Number");
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeView.setRefreshing(false);
                        double f = Math.random();
                        //rndNum.setText(String.valueOf(f));
                    }
                }, 3000);
            }
        });
        return rootView;

    }

}