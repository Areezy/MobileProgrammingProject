package com.example.mobileprogrammingproject;

public class items {

    String itemname;
    String itemprice;
     int itemimagepreview;


    public  items(){

    }

    public items(String itemname, String itemprice , int itemimagepreview){
        itemname = this.itemname;
        itemprice = this.itemprice;
        itemimagepreview = this.itemimagepreview;
    }


    public String getItemname() {
        return  itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getItemprice() {

        return itemprice;
    }

    public void setItemprice(String itemprice) {

        this.itemprice = itemprice;
    }

    public int getItemimagepreview() {

        return itemimagepreview;
    }

    public void setItemimagepreview(int itemimagepreview) {
        this.itemimagepreview = itemimagepreview;
    }





}
