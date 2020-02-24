package com.demo.sample;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.demo.sample.databinding.ItemRateBinding;

import java.util.Map;

public class RatesAdapter extends RecyclerView.Adapter<RatesAdapter.RateViewHolder> {
   private Map<String,Double> rateList;
   private String[] keys;


    public RatesAdapter(Map<String, Double> rateList) {
        this.rateList = rateList;
        keys= rateList.keySet().toArray(new String[rateList.size()]);
    }

    void updateAdapter(Map<String,Double> updatedRates){
        rateList.putAll(updatedRates);
    }

    @NonNull
    @Override
    public RateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     ItemRateBinding view= ItemRateBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
     return new RateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RateViewHolder holder, int position) {

        Rate rate=new Rate(rateList.get(keys[position]),keys[position]);
        holder.bindView(rate);
    }

    @Override
    public int getItemCount() {
        return rateList.size();
    }

    class RateViewHolder extends RecyclerView.ViewHolder{

        ItemRateBinding rateBinding;
        public RateViewHolder( ItemRateBinding rateBinding) {
            super(rateBinding.getRoot());
            this.rateBinding=rateBinding;

        }
        void bindView(Rate rate){
            rateBinding.setRate(rate);
           rateBinding.executePendingBindings();

        }
    }
}
