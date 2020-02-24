package com.demo.sample;

import androidx.lifecycle.MutableLiveData;

import java.util.Map;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RatesRepository {

    private static RatesRepository ratesRepository;

    public static RatesRepository getInstance(){
        if (ratesRepository == null){
            ratesRepository = new RatesRepository();
        }
        return ratesRepository;
    }

    private RateService rateService;

    public RatesRepository() {
        rateService = provideRetrofit().create(RateService.class);
    }

    public Retrofit provideRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(RateService.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }



    public Single<RatesResponse> getRates(){
        return rateService.getRateResponse();
    }


}
