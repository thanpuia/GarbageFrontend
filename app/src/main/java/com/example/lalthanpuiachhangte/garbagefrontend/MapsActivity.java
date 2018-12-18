package com.example.lalthanpuiachhangte.garbagefrontend;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public int NO_OF_TRUCKS = 4;

    public DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

            databaseReference = FirebaseDatabase.getInstance().getReference("truck-2/location/");
       // databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.orderByKey().limitToLast(1).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    String lat = "";
                    String lng = "";

                    for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                        lat = (String) messageSnapshot.child("latitude").getValue();
                        lng = (String) messageSnapshot.child("longitude").getValue();

                        Log.i("TAG",""+ messageSnapshot);

                    }

                    if(lat != "" && lng != "" )
                        setMap(lat,lng);
                }
                @Override public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

       /* databaseReference.orderByKey().limitToLast(1).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    lat = (double) messageSnapshot.child("latitude").getValue();
                    lng = (double) messageSnapshot.child("longitude").getValue();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

       /* databaseReference.orderByKey().limitToLast(1).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.i("TAG","ChildEvent: "+dataSnapshot);

                Log.i("TAG","Clongitude: "+dataSnapshot.child("/longitude").getValue());
                Log.i("TAG","Clatitude: "+dataSnapshot.child("/latitude").getValue());

            }
            @Override public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
            @Override public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }
            @Override public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
            @Override public void onCancelled(@NonNull DatabaseError databaseError) { }
        });*/


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    public void setMap(String lat, String lng) {

        double lat1 = Double.parseDouble(lat);
        double lng1 = Double.parseDouble(lng);
        LatLng sydney = new LatLng(lat1, lng1);
        MarkerOptions a = new MarkerOptions().position(new LatLng(lat1, lng1));
        Marker m1 = mMap.addMarker(a);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        /*                             ZOOM THE CAMERA VIEW
         * *                          ---------------------------
         * *                       22     : we can see only one street
         * *                       18     : we can see around 3 streets
         * *                       16     : this is very clear but the visible area is too less
         * *                       15.5   : we can see one locality and its neighbour  THIS IS GOOD FOR NOW!!
         * *                       12     : we can see whole aizawl city and its surroundings
         */

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom
                (new LatLng(lat1, lng1), 15.5f));
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(sydney));
    }
}
