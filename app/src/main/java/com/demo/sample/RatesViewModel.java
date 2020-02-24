package com.demo.sample;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.Map;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class RatesViewModel extends AndroidViewModel {
    RatesRepository ratesRepository;
    MutableLiveData<Map<String,Double>> ratesObservable;
    CompositeDisposable compositeDisposable;

    MutableLiveData<Boolean> loading=new MutableLiveData<>();


    public RatesViewModel(@androidx.annotation.NonNull Application application) {
        super(application);
        ratesRepository=RatesRepository.getInstance();
            ratesObservable=new MutableLiveData<>();
            compositeDisposable=new CompositeDisposable();

        setTimer();
    }




    private void setTimer(){
        loading.setValue(true);
       Observable.interval(0,1,TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Long value) {
                        getRate();


                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    private void getRate(){
        compositeDisposable.add(ratesRepository.getRates().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<RatesResponse>() {
                    @Override
                    public void onSuccess(RatesResponse value) {
                        ratesObservable.setValue(value.getRateList());
                        loading.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        loading.setValue(false);
                    }
                }));
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
            compositeDisposable.clear();
            compositeDisposable = null;
        }
    }

    public MutableLiveData<Map<String,Double>> getRatesObservable() {
         return ratesObservable;
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }
}

