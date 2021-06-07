package com.alejandromartinezremis.airquailitygijon.pojos;

/**
 * Object that represents the data inside each item of a ListView
 */
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