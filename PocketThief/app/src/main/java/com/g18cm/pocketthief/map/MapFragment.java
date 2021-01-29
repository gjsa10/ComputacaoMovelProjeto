package com.g18cm.pocketthief.map;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.g18cm.pocketthief.Constants;
import com.g18cm.pocketthief.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class MapFragment extends Fragment implements LocationListener {
    private View rootView;
    private GoogleMap map;
    private Location myLocation;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment smf = (SupportMapFragment) getChildFragmentManager().
                findFragmentById(R.id.google_map);

        smf.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {
                //When the map is ready
                map = googleMap;
                map.setMinZoomPreference(5); // Set a preference for minimum zoom (Zoom out).
                map.setMaxZoomPreference(35); // Set a preference for maximum zoom (Zoom In)

                centerOnMyLocation();

                map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        //When click on map
                    }
                });
            }
        });

        return rootView;
    }

    private void centerOnMyLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        map.setMyLocationEnabled(true);

        LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }

        myLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (myLocation != null) {
            LatLng location = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(location, Constants.default_player_zoom));
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        myLocation = location;
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(location.getLatitude(), location.getLongitude()), Constants.default_player_zoom));
    }

    @Override
    public void onProviderDisabled(String provider) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}
}