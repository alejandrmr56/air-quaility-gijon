package com.alejandromartinezremis.airquailitygijon.logic;

import org.json.JSONException;
import org.json.JSONObject;

public class AirStation {
    private Integer estacion;
    private String titulo, fecha, periodo, co, pm10, pm25;
    private Double latitud, longitud, so2, no, no2, o3, dd, vv, tmp, hr, prb, rs, ll, ben, tol, mxil;

    public AirStation(JSONObject jsonObject) {
        try {
            this.estacion = jsonObject.getInt("estacion");
            this.titulo = jsonObject.getString("título");
            this.latitud = jsonObject.getDouble("latitud");
            this.longitud = jsonObject.getDouble("longitud");
            this.fecha = jsonObject.getString("fecha");
            this.periodo = jsonObject.getString("periodo");
            this.so2 = jsonObject.optDouble("so2");
            this.no = jsonObject.optDouble("no");
            this.no2 = jsonObject.optDouble("no2");
            this.co = jsonObject.optString("co");
            this.pm10 = jsonObject.optString("pm10");
            this.o3 = jsonObject.optDouble("o3");
            this.dd = jsonObject.optDouble("dd");
            this.vv = jsonObject.optDouble("vv");
            this.tmp = jsonObject.optDouble("tmp");
            this.hr = jsonObject.optDouble("hr");
            this.prb = jsonObject.optDouble("prb");
            this.rs = jsonObject.optDouble("rs");
            this.ll = jsonObject.optDouble("ll");
            this.ben = jsonObject.optDouble("ben");
            this.tol = jsonObject.optDouble("tol");
            this.mxil = jsonObject.optDouble("mxil");
            this.pm25 = jsonObject.optString("pm25");
        } catch (JSONException e) {
            e.printStackTrace();//TODO: Handle exception
        }
    }

    public Integer getEstacion() {
        return estacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getFecha() {
        return fecha;
    }

    public String getPeriodo() {
        return periodo;
    }

    public String getCo() {
        return co;
    }

    public String getPm10() {
        return pm10;
    }

    public String getPm25() {
        return pm25;
    }

    public Double getLatitud() {
        return latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public Double getSo2() {
        return so2;
    }

    public Double getNo() {
        return no;
    }

    public Double getNo2() {
        return no2;
    }

    public Double getO3() {
        return o3;
    }

    public Double getDd() {
        return dd;
    }

    public Double getVv() {
        return vv;
    }

    public Double getTmp() {
        return tmp;
    }

    public Double getHr() {
        return hr;
    }

    public Double getPrb() {
        return prb;
    }

    public Double getRs() {
        return rs;
    }

    public Double getLl() {
        return ll;
    }

    public Double getBen() {
        return ben;
    }

    public Double getTol() {
        return tol;
    }

    public Double getMxil() {
        return mxil;
    }
}
