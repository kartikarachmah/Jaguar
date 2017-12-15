package jp.tubes.pbp.jaguar.OrangTua.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import jp.tubes.pbp.jaguar.OrangTua.App.ListMapController;
import jp.albasyir.jundana.familytracer.R;

public class ListMapAdapter extends ArrayAdapter<ListMapController> {
    private List<ListMapController> lokasiList;

    private Context mJb;

    public ListMapAdapter(List<ListMapController> lokasiList, Context mJb) {
        super(mJb, R.layout.orangtua_list_lokasi_anak, lokasiList);
        this.lokasiList = lokasiList;
        this.mJb = mJb;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mJb);

        View listViewItem = inflater.inflate(R.layout.orangtua_list_lokasi_anak, null, true);

        TextView textViewNamaAnak   = listViewItem.findViewById(R.id.textViewNamaAnak);
        TextView textViewLat        = listViewItem.findViewById(R.id.textViewLat);
        TextView textViewLng        = listViewItem.findViewById(R.id.textViewLng);

        ListMapController map = lokasiList.get(position);

        textViewNamaAnak.setText(map.getNama());
        textViewLat.setText(map.getLat());
        textViewLng.setText(map.getLng());

        return listViewItem;
    }
}
