package kz.kbtu.googlemaps;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button btnFind;
    private EditText etFind;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        bindViews();
    }

    private void bindViews() {
        btnFind = findViewById(R.id.btn_find);
        etFind = findViewById(R.id.et_find);
        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = etFind.getText().toString().trim();
                moveToPlace(text);
            }
        });
    }

    private void moveToPlace(String text) {
        mMap.clear();
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocationName(text, 3);
            if(!addresses.isEmpty()){
                Double lat = (double)addresses.get(0).getLatitude();
                Double lon = (double)addresses.get(0).getLongitude();
                final LatLng place = new LatLng(lat, lon);
                Marker point = mMap.addMarker(new MarkerOptions()
                .position(place)
                .title(text));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 15));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(16), 2000, null);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
