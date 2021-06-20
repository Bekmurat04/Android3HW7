package com.example.mapshomewrok.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.mapshomewrok.App;
import com.example.mapshomewrok.R;
import com.example.mapshomewrok.data.models.Figure;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements OnMapReadyCallback {

    private static final int LOCATION = 0;
    private List<LatLng> places = new ArrayList<>();
    private List<Figure> figureArrayList = new ArrayList<>();
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkLocation();
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        click();
    }

    private void checkLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION);
        }

    }

    private void click() {
        findViewById(R.id.hybrid_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            }
        });

        findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App.database.figureDao().deleteAll(figureArrayList);
                map.clear();
            }
        });
        findViewById(R.id.polygon_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PolygonOptions polygonOptions = new PolygonOptions();
                polygonOptions.strokeWidth(5f);
                polygonOptions.strokeColor(Color.GRAY);
                for (LatLng latLng:
                     places) {
                    polygonOptions.add(latLng);
                    Figure figure1 = new Figure(latLng.latitude,latLng.longitude);
                    figureArrayList.add(figure1);
                }
                map.addPolygon(polygonOptions);
                App.database.figureDao().putFigure(figureArrayList);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        location();
        CameraPosition cameraPosition = CameraPosition
                .builder()
                .target(new LatLng(42.8731844, 74.5834363))
                .zoom(19.75f).build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        figureArrayList = App.database.figureDao().getFigure();
        if (figureArrayList.size() > 0){
            PolygonOptions polygonOptions = new PolygonOptions();
            polygonOptions.strokeWidth(5f);
            polygonOptions.strokeColor(Color.GRAY);
            for (int i = 0; i < figureArrayList.size() ; i++) {
                LatLng lng = new LatLng(figureArrayList.get(i).getLatitude(), figureArrayList.get(i).getLatlng());
                polygonOptions.add(lng);
            }
            map.addPolygon(polygonOptions);
        }
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                places.add(latLng);
                Log.d("lat",latLng.toString());
                Log.d("onMapClick", "onMapClick");
                map.addMarker(new MarkerOptions().position(latLng).title("Marker")).setIcon(BitmapDescriptorFactory.defaultMarker());
            }
        });
    }

    private void location() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);

        } else {
            Toast.makeText(this, "not denied", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        map.setMyLocationEnabled(true);
                    } else {
                        Toast.makeText(this, "denied", Toast.LENGTH_SHORT).show();
                    }
                }
        }
    }
}