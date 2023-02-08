package com.deyeon209.transapp.model;

import java.io.Serializable;

public class Trans implements Serializable {

    public String input;
    public String output;

    public String region;

    public Trans(){

    }

    public Trans(String input, String output, String region) {
        this.input = input;
        this.output = output;
        this.region = region;
    }
}
