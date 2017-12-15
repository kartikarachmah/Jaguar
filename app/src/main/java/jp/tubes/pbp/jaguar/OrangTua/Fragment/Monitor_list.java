package jp.tubes.pbp.jaguar.OrangTua.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import jp.tubes.pbp.jaguar.OrangTua.Adapter.ListMapAdapter;
import jp.tubes.pbp.jaguar.OrangTua.App.ListMapController;
import jp.albasyir.jundana.familytracer.R;

import static android.content.Context.MODE_PRIVATE;
import static jp.tubes.pbp.jaguar.UrlLink.url_maps;

public class Monitor_list extends Fragment {

    ListView listView;
    List<ListMapController> mapsList;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.orangtua_fragment_monitor_list, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("familytracer_ortu", MODE_PRIVATE);
        final String id_ortu = sharedPreferences.getString("id_ortu", null);

        listView = view.findViewById(R.id.listView);
        mapsList = new ArrayList<>();

        final ProgressBar progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        String url_map = url_maps + "?id_ortu=" + id_ortu;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_map,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.INVISIBLE);
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray mapsArray = obj.getJSONArray("lokasi_anak");

                            for (int i = 0; i < mapsArray.length(); i++) {
                                JSONObject mapsObject = mapsArray.getJSONObject(i);

                                ListMapController listmap = new ListMapController(
                                        mapsObject.getString("id"),
                                        mapsObject.getString("id_user"),
                                        mapsObject.getString("lat"),
                                        mapsObject.getString("lng"));
                                mapsList.add(listmap);
                            }

                            ListMapAdapter adapter = new ListMapAdapter(mapsList, getActivity());
                            listView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                ListMapController map = mapsList.get(position);

//                Intent detail = new Intent(getActivity(), Detail_Monitor_List.class);
//                detail.putExtra(TAG_ID, map.getID());
//                detail.putExtra(TAG_NAMA, map.getNama());
//                startActivity(detail);

//                ViewGroup viewg = (ViewGroup) view;
//                TextView txt = viewg.findViewById(R.id.textViewNamaAnak);
//                Toast.makeText(getActivity(), txt.getText(), Toast.LENGTH_SHORT).show();


//                Object o = listView.getItemIdAtPosition(a);
//                String txt = o.toString();
//                Toast.makeText(getActivity(), "Anda Telah Menekan : " + " "+txt, Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }
}
