package jp.tubes.pbp.jaguar.OrangTua.App;

public class ListMapController {
    private String ID, NAMA, LAT, LNG;

    public ListMapController(String id, String id_anak, String lat, String lng){
        this.ID = id;
        this.NAMA = id_anak;
        this.LAT = lat;
        this.LNG = lng;
    }

    public String getID(){
        return ID;
    }

    public String getNama() {
        return NAMA;
    }

    public String getLat(){
        return LAT;
    }

    public String getLng(){
        return LNG;
    }
}
