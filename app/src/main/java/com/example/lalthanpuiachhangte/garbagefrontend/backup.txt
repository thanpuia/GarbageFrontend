package com.example.lalthanpuiachhangte.garbagefrontend;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.text.DecimalFormat;

import static android.location.LocationManager.GPS_PROVIDER;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public int NO_OF_TRUCKS = 4;
    public Marker marker, marker1, marker2, marker3, marker4;
    public LocationManager locationManager;
    public LocationListener locationListener;
    private FusedLocationProviderClient mFusedLocationClient;


    public DatabaseReference databaseReference1, databaseReference2, databaseReference3, databaseReference4,databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        marker = marker1 = marker2 = marker3 = marker4 = null;

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {

                    Log.i("TAG/root",""+ messageSnapshot.getKey());
                }
            }
            @Override public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
            @Override public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }
            @Override public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
            @Override public void onCancelled(@NonNull DatabaseError databaseError) { }});


        //TRUCK 1
        databaseReference1 = FirebaseDatabase.getInstance().getReference("truck-1/location/");

        databaseReference1.orderByKey().limitToLast(1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String lat = "";
                String lng = "";

                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    lat = (String) messageSnapshot.child("latitude").getValue();
                    lng = (String) messageSnapshot.child("longitude").getValue();

                    Log.i("TAG",""+ messageSnapshot);
                }

                int truckNum = 1;
                if(lat != "" && lng != "" )
                    setMap(lat,lng,truckNum);
            }
            @Override public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        //TRUCK 2
        databaseReference2 = FirebaseDatabase.getInstance().getReference("truck-2/location/");

        databaseReference2.orderByKey().limitToLast(1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String lat = "";
                String lng = "";

                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    lat = (String) messageSnapshot.child("latitude").getValue();
                    lng = (String) messageSnapshot.child("longitude").getValue();

                    Log.i("TAG", "" + messageSnapshot);
                }

                int truckNum = 2;
                if (lat != "" && lng != "")
                    setMap(lat, lng, truckNum);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        //TRUCK 3
        databaseReference3 = FirebaseDatabase.getInstance().getReference("truck-3/location/");

        databaseReference3.orderByKey().limitToLast(1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String lat = "";
                String lng = "";

                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    lat = (String) messageSnapshot.child("latitude").getValue();
                    lng = (String) messageSnapshot.child("longitude").getValue();

                    Log.i("TAG", "" + messageSnapshot);
                }

                int truckNum = 3;
                if (lat != "" && lng != "")
                    setMap(lat, lng, truckNum);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        //TRUCK 4
        databaseReference4 = FirebaseDatabase.getInstance().getReference("truck-4/location/");

        databaseReference4.orderByKey().limitToLast(1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String lat = "";
                String lng = "";

                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    lat = (String) messageSnapshot.child("latitude").getValue();
                    lng = (String) messageSnapshot.child("longitude").getValue();

                    Log.i("TAG", "" + messageSnapshot);
                }

                int truckNum = 4;
                if (lat != "" && lng != "")
                    setMap(lat, lng, truckNum);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


       /* locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                Log.i("stringen","current"+location);


            }
            @Override public void onStatusChanged(String provider, int status, Bundle extras) { }
            @Override public void onProviderEnabled(String provider) { }
            @Override public void onProviderDisabled(String provider) { }
        };
        Location lastKnowLocation = locationManager.getLastKnownLocation(GPS_PROVIDER);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,800,5,locationListener);
        Log.i("stringen","last known: "+lastKnowLocation);


        locationManager.requestLocationUpdates(GPS_PROVIDER,800,5,locationListener);*/


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        Log.i("stringen", "last known: " + location.getLatitude()+ " "+ location.getLongitude());
                        LatLng sydney = new LatLng(location.getLatitude(),location.getLongitude());
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom
                                (sydney, 15f));

                        if (marker == null) {
                            MarkerOptions options = new MarkerOptions().position(sydney)
                                    .title("Marker Title")
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));;
                            marker = mMap.addMarker(options);
                        }
                        else {
                            //marker = mMap2.addMarker();
                            marker.setPosition(sydney);
                        }
                        if (location != null)
                            return;
                    }
                });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
       // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    public void setMap(String lat, String lng, int truckNum) {

        /*                             ZOOM THE CAMERA VIEW
         * *                          ---------------------------
         * *                       22     : we can see only one street
         * *                       18     : we can see around 3 streets
         * *                       16     : this is very clear but the visible area is too less
         * *                       15.5   : we can see one locality and its neighbour  THIS IS GOOD FOR NOW!!
         * *                       12     : we can see whole aizawl city and its surroundings
         */

        float cameraZoom = 7f;
        switch (truckNum){

            case 1:
                double lat1 = Double.parseDouble(lat);
                double lng1 = Double.parseDouble(lng);
                LatLng sydney1 = new LatLng(lat1, lng1);
                GoogleMap mMap1 = mMap;
          /*      mMap1.moveCamera(CameraUpdateFactory.newLatLng(sydney1));
                mMap1.animateCamera(CameraUpdateFactory.newLatLngZoom
                        (new LatLng(lat1, lng1), cameraZoom));*/
                if (marker1 == null) {
                    MarkerOptions options = new MarkerOptions().position(sydney1)
                            .title("Marker Title")
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    marker1 = mMap1.addMarker(options);
                }
                else {
                    //marker = mMap2.addMarker();
                    marker1.setPosition(sydney1);
                }
                break;

            case 2:
                double lat2 = Double.parseDouble(lat);
                double lng2 = Double.parseDouble(lng);
                LatLng sydney2 = new LatLng(lat2, lng2);
                GoogleMap mMap2 = mMap;

                /*mMap2.moveCamera(CameraUpdateFactory.newLatLng(sydney2));
                mMap2.animateCamera(CameraUpdateFactory.newLatLngZoom
                        (new LatLng(lat2, lng2), cameraZoom));
*/
                if (marker2 == null) {
                    MarkerOptions options = new MarkerOptions().position(sydney2)
                            .title("Marker Title")
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                    marker2 = mMap2.addMarker(options);
                }
                else {
                    //marker = mMap2.addMarker();
                    marker2.setPosition(sydney2);
                }


                break;

            case 3:
                double lat3 = Double.parseDouble(lat);
                double lng3 = Double.parseDouble(lng);
                LatLng sydney3 = new LatLng(lat3, lng3);
                GoogleMap mMap3 = mMap;

               /* mMap3.moveCamera(CameraUpdateFactory.newLatLng(sydney3));
                mMap3.animateCamera(CameraUpdateFactory.newLatLngZoom
                        (new LatLng(lat3, lng3), cameraZoom));*/
              //  mMap3.clear();
             //   mMap3.addMarker(new MarkerOptions().position(sydney3).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));

                if (marker3 == null) {
                    MarkerOptions options = new MarkerOptions().position(sydney3)
                            .title("Marker Title")
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
                    marker3 = mMap3.addMarker(options);
                }
                else {
                    //marker = mMap2.addMarker();
                    marker3.setPosition(sydney3);
                }
                break;

            case 4:
                double lat4 = Double.parseDouble(lat);
                double lng4 = Double.parseDouble(lng);
                LatLng sydney4 = new LatLng(lat4, lng4);
                GoogleMap mMap4 = mMap;
/*
                mMap4.moveCamera(CameraUpdateFactory.newLatLng(sydney4));
                mMap4.animateCamera(CameraUpdateFactory.newLatLngZoom
                        (new LatLng(lat4, lng4), cameraZoom));*/
             //  mMap4.clear();
               // mMap4.addMarker(new MarkerOptions().position(sydney4).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                if (marker4 == null) {
                    MarkerOptions options = new MarkerOptions().position(sydney4)
                            .title("Marker Title")
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));;
                    marker4 = mMap4.addMarker(options);
                }
                else {
                    //marker = mMap2.addMarker();
                    marker4.setPosition(sydney4);
                }
                break;

        }




    }
}
