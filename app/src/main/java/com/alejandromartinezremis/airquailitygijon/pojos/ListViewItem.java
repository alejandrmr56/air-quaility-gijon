package com.alejandromartinezremis.airquailitygijon.pojos;

public class ListViewItem {
    private String id;
    private String value;

    public ListViewItem(String id, String value){
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public String getValue() {
        return value;
    }
}