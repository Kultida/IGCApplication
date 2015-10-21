package inducesmile.com.androidnavigation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.lang.reflect.Field;

/**
 * Created by User on 15/10/2558.
 */
public class MapFragment extends Fragment  {


    GoogleMap googleMap;
    FragmentManager myFragmentManager;
    SupportMapFragment mySupportMapFragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        //googleMap.setMyLocationEnabled(true);

        try {
            // Loading map
            initilizeMap();
            googleMap.setMyLocationEnabled(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootView;
    }
    private void initilizeMap() {

        try
        {
            if (googleMap == null) {
                myFragmentManager = getFragmentManager();
                mySupportMapFragment = (SupportMapFragment)myFragmentManager.findFragmentById(R.id.map);
                googleMap = mySupportMapFragment.getMap();
                LatLng sydney = new LatLng(-34, 151);
                googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                if (googleMap == null) {

                    Toast.makeText(getActivity().getApplicationContext(),
                            "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        } catch (Exception e) {

            // TODO: handle exception
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        initilizeMap();

    }

    @Override
    public void onDetach() {
        // TODO Auto-generated method stub
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class
                    .getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}