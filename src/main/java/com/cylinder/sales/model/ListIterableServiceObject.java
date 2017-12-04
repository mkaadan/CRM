package com.cylinder.sales.model;

import java.util.ArrayList;
import java.util.List;

public abstract class ListIterableServiceObject<T> {

    /**
     * converts a iterable object into a list
     *
     * @param products  the iterable to convert
     * @return a list of all the products in the iterable
     */
    public static <T> List<T> iterableToList(Iterable<T> products){
        List<T> productList = new ArrayList<T>();
        for(T product : products){
            productList.add(product);
        }
        return productList;
    }


    /**
     * converts a list object into a iterable
     *
     * @param products  the list to convert
     * @return an iterable of all the products in the list
     */
    public static <T> Iterable<T> listToIterable(List<T> products){
        Iterable<T> productIterrable = products;
        return productIterrable;
    }

    public ListIterableServiceObject() {
    }
}
