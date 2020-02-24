package com.demo.sample;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class RatesResponse {

    @SerializedName("baseCurrency")
    @Expose
    private String baseCurrency;


    @SerializedName("rates")
    @Expose
    Map<String,Double> rateList;


    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public Map<String, Double> getRateList() {
        return rateList;
    }

    public void setRateList(Map<String, Double> rateList) {
        this.rateList = rateList;
    }
}
