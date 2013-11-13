package com.norm.norm;

import java.util.List;

/**
 * Created by Cesar on 11/12/13.
 */


public class order {

    private String Restaurant;
    private String Name;
    private List Elements;

    order(String restaurant, String name, List elements){
        Restaurant = restaurant;
        Name = name;
        Elements = elements;
    }

    public String getRestaurant(){
        return Restaurant;
    }

    public String getName(){
        return Name;
    }

    public List getElements(){
        return Elements;
    }
}
