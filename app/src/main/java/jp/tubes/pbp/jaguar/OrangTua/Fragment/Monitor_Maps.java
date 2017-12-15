package jp.tubes.pbp.jaguar.OrangTua.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import jp.tubes.pbp.jaguar.OrangTua.App.AppController;
import jp.albasyir.jundana.familytracer.R;

import static android.content.Context.MODE_PRIVATE;
import static jp.tubes.pbp.jaguar.UrlLink.url_maps;


public class Monitor_Maps extends Fragment implements OnMapReadyCallback {

    final ThreadLocal<GoogleMap> gM = new ThreadLocal<>();
    MarkerOptions mO;

    public String NAMA, LAT, LNG, JSON;

    public Monitor_Maps() {
        this.NAMA = "id_user";
        this.LAT = "lat";
        this.LNG = "lng";
        mO = new MarkerOptions();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.orangtua_fragment_monitor_maps, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("familytracer_ortu", MODE_PRIVATE);
        final String id_ortu = sharedPreferences.getString("id_ortu", null);

        MapFragment mapFragment = ((MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.map));
        mapFragment.getMapAsync(this);

        String url_map = url_maps + "?id_ortu=" + id_ortu;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_map, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response: ", response);
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray mapsArray = obj.getJSONArray("lokasi_anak");

                    for (int i = 0; i < mapsArray.length(); i++) {
                        JSONObject mapsObject = mapsArray.getJSONObject(i);

                        String nama = mapsObject.getString(NAMA);
                        LatLng latLng = new LatLng(
                                Double.parseDouble(mapsObject.getString(LAT)),
                                Double.parseDouble(mapsObject.getString(LNG)));
                        addMarker(latLng, nama);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error: ", error.getMessage());
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        AppController.getInstance().addToRequestQueue(stringRequest, JSON);
        return view;
    }


    public void onMapReady(GoogleMap googleMap) {
        gM.set(googleMap);

        LatLng center = new LatLng(-7.0520483, 110.4386057);

        CameraPosition cameraPosition = new CameraPosition.Builder().target(center).zoom(10).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

    private void addMarker(LatLng latlng, final String nama) {
        mO.position(latlng);
        mO.title(nama);
        gM.get().addMarker(mO);

        gM.get().setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Toast.makeText(getActivity().getApplicationContext(), marker.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
