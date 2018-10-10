package com.example.henrylopez.monyapp.Metodos;

public class mMovimientos {
    private String Cant;
    private String Detail;
    private String Key;
    private String Title;
    private String Type;

    public mMovimientos(){

    }

    public mMovimientos(String cant, String detail, String key, String title, String type) {
        Cant = cant;
        Detail = detail;
        Key = key;
        Title = title;
        Type = type;
    }

    public String getCant() {
        return Cant;
    }

    public void setCant(String cant) {
        Cant = cant;
    }

    public String getDetail() {
        return Detail;
    }

    public void setDetail(String detail) {
        Detail = detail;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

}
