package com.example.ratnesh.isroproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.system.StructUtsname;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsMainActivity extends FragmentActivity implements OnMapReadyCallback,LocationListener {

    private GoogleMap mMap;
    LocationManager mManager;
    private LatLng preLoc;
    private boolean flag = false;
    ArrayList<Structure> arr= new ArrayList<>();
    DBAdapter obj;
    String Name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_main);

        mManager =(LocationManager) getSystemService(LOCATION_SERVICE);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        obj=DBAdapter.getDBAdapter(getApplicationContext());
        if(obj.checkDatabase()==false) obj.createDatabase(getApplicationContext());
        obj.openDatabase();


        Criteria ctr=new Criteria();
        ctr.setAccuracy(Criteria.ACCURACY_LOW);
        ctr.setPowerRequirement(Criteria.ACCURACY_LOW);
        String Provider=mManager.getBestProvider(ctr,true);

        Toast.makeText(getApplicationContext(), "Provider : "+Provider, Toast.LENGTH_SHORT).show();

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(),"Permission not granted",Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

//            double lat = mManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude();
//            double lang = mManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude();
//            preLoc = new LatLng(lat,lang);
//            mMap.addMarker(new MarkerOptions().position(preLoc).title("Get Startted"));
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        try {
            mManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0.5f, this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i("Location : ",location.toString());
        Toast.makeText(getApplicationContext(),"Tracing Location",Toast.LENGTH_SHORT).show();
        double lat = location.getLatitude();
        double lang  = location.getLongitude();
        LatLng myLoc = new LatLng(lat,lang);
        mMap.addMarker(new MarkerOptions().position(myLoc));
        Name=getIntent().getExtras().getString("Name");
        obj.insertDB(Name,lat,lang);
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            String address = addresses.get(0).getAddressLine(0)+", "+
                    addresses.get(0).getAddressLine(1)+", "+addresses.get(0).getAddressLine(2);

        }catch(Exception e)
        {
            e.printStackTrace();
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLng(myLoc));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16.0f));
        if(flag) {
            mMap.addPolyline(new PolylineOptions()
                    .add(preLoc, myLoc).width(10).color(Color.BLUE));
        }
        flag = true;
        preLoc = myLoc;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Toast.makeText(getApplicationContext(),"Status changed",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(getApplicationContext(),"Getting your current location...",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(getApplicationContext(),"Please enable GPS provider",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng Jodhpur = new LatLng(26.2389, 73.0243);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Jodhpur));


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent= new Intent(getApplicationContext(),MAINPAGE.class);
        startActivity(intent);
        finish();
    }
}
