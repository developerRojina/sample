package com.demo.sample;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface RateService {

    String BASE_URL="https://hiring.revolut.codes/api/";

    @GET("android/latest?base=EUR")
    Single<RatesResponse> getRateResponse();
}
