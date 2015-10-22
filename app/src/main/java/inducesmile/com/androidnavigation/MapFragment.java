package inducesmile.com.androidnavigation;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by User on 15/10/2558.
 */
public class MapFragment extends Fragment {

    public int issue_id;
    //    MapView mapView;
//    GoogleMap map;/
    LatLng CENTER = null;

    public LocationManager locationManager;

    double longitudeDouble;
    double latitudeDouble;

    String snippet;
    String title;
    Location location;
    String myAddress;

    String LocationId;
    String CityName;
    String imageURL;

    MapView mapView;
    private GoogleMap map;


    FragmentManager myFragmentManager;
    SupportMapFragment mySupportMapFragment;

    public static MapFragment newInstance(int issue_id) {
        MapFragment fragment = new MapFragment();
        fragment.issue_id = issue_id;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);


        mapView = (MapView) rootView.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        setMapView();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    //    private void setMapView() {
//        mapView.onResume();
//        try {
//            MapsInitializer.initialize(getActivity().getApplicationContext());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        googleMap = mapView.getMap();
//        // latitude and longitude
//        double latitude = 17.385044;
//        double longitude = 78.486671;
//
//        // create marker
//        MarkerOptions marker = new MarkerOptions().position(
//                new LatLng(latitude, longitude)).title("Hello Maps");
//
//        // Changing marker icon
//        marker.icon(BitmapDescriptorFactory
//                .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
//
//
//        // adding marker
//        googleMap.addMarker(marker);
//        CameraPosition cameraPosition = new CameraPosition.Builder()
//                .target(new LatLng(17.385044, 78.486671)).zoom(12).build();
//        googleMap.animateCamera(CameraUpdateFactory
//                .newCameraPosition(cameraPosition));
//    }
    private void setMapView() {
        try {
            MapsInitializer.initialize(getActivity());

            switch (GooglePlayServicesUtil
                    .isGooglePlayServicesAvailable(getActivity())) {
                case ConnectionResult.SUCCESS:
                    // Toast.makeText(getActivity(), "SUCCESS", Toast.LENGTH_SHORT)
                    // .show();

                    // Gets to GoogleMap from the MapView and does initialization
                    // stuff
                    if (mapView != null) {

                        locationManager = ((LocationManager) getActivity()
                                .getSystemService(Context.LOCATION_SERVICE));

                        Boolean localBoolean = Boolean.valueOf(locationManager
                                .isProviderEnabled("network"));

                        if (localBoolean.booleanValue()) {

                            //CENTER = new LatLng(latitude, longitude);

                        } else {

                        }
                        map = mapView.getMap();
                        if (map == null) {

                            Log.d("", "Map Fragment Not Found or no Map in it!!");

                        }

                        map.clear();
                        try {
                            map.addMarker(new MarkerOptions().position(CENTER)
                                    .title(CityName).snippet(""));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        map.setIndoorEnabled(true);
                        map.setMyLocationEnabled(true);
                        map.moveCamera(CameraUpdateFactory.zoomTo(5));
                        if (CENTER != null) {
                            map.animateCamera(
                                    CameraUpdateFactory.newLatLng(CENTER), 1750,
                                    null);
                        }
                        // add circle
                        CircleOptions circle = new CircleOptions();
                        circle.center(CENTER).fillColor(Color.BLUE).radius(10);
                        map.addCircle(circle);
                        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                    }
                    break;
                case ConnectionResult.SERVICE_MISSING:

                    break;
                case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:

                    break;
                default:

            }
        } catch (Exception e) {

        }
    }


}


