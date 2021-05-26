package com.alejandromartinezremis.airquailitygijon.pojos;

public class ListViewItem {
    private final String id;
    private final String value;

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