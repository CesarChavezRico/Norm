package com.norm.norm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Cesar on 11/12/13.
 */


public class special {

    private String Restaurant;
    private String Name;
    private List Elements;

    special(String restaurant, String name){
        Restaurant = restaurant;
        Name = name;
        Elements = new ArrayList(Arrays.asList(""));

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
