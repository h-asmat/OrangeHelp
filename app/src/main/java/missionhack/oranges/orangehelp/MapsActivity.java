package missionhack.oranges.orangehelp;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    Alert alert = Alert.getInstance();

    private GoogleMap mMap;
    LocationManager lm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        GPStracker g = new GPStracker(getApplicationContext());
        Location l = g.getLocation();
        System.out.println(l);
        double lat = 0;
        double lon = 0;
        if (l != null) {
            lat = l.getLatitude();
            lon = l.getLongitude();
            // Toast.makeText(getApplicationContext(), "LAT: "+lat+"\n LON: "+lon, Toast.LENGTH_LONG).show();
        }

        // Add a marker in Sydney and move the camera
        LatLng astronaut = new LatLng(lat, lon);
        LatLng destination = new LatLng(alert.getLatitude(),alert.getLongitude());
        mMap.addMarker(new MarkerOptions()
                .position(astronaut)
                .title("Astronaut")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.astronaut)));
        mMap.addMarker(new MarkerOptions()
                .position(destination)
                .title("Crisis")
                .icon(getAppropriateResource()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(astronaut,2.0f));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(destination,2.0f));

    }

    public BitmapDescriptor getAppropriateResource() {
        switch (alert.getOccupation()){
            case Doctor:
                return BitmapDescriptorFactory.fromResource(R.mipmap.medical_kit);
            case Fireman:
                return BitmapDescriptorFactory.fromResource(R.mipmap.fire);
            case Cop:
                return BitmapDescriptorFactory.fromResource(R.mipmap.siren);
            default:
                return BitmapDescriptorFactory.defaultMarker();
        }
    }
}
