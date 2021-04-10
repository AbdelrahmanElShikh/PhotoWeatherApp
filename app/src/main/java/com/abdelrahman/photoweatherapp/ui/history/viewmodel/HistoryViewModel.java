package com.abdelrahman.photoweatherapp.ui.history.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.abdelrahman.core.resource.PresentationResource;
import com.abdelrahman.photoweatherapp.room.model.WeatherPhoto;
import com.abdelrahman.photoweatherapp.room.repository.LocalRepository;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @Author : Abdel-Rahman El-Shikh
 * @Date : 09-Apr-21
 * @Project : com.abdelrahman.photoweatherapp.ui.history
 */
public class HistoryViewModel extends AndroidViewModel {
    private final MutableLiveData<PresentationResource<List<WeatherPhoto>>> weatherResourceMutableLiveData = new MutableLiveData<>();
    private final LocalRepository historyRepository;

    public HistoryViewModel(@NonNull Application application) {
        super(application);
        historyRepository = new LocalRepository(application);
    }

    public LiveData<PresentationResource<List<WeatherPhoto>>> getWeatherPhotoList() {
        historyRepository.getWeatherPhotos()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<WeatherPhoto>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                        weatherResourceMutableLiveData.postValue(PresentationResource.Companion.loading());
                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull List<WeatherPhoto> weatherPhotos) {
                        weatherResourceMutableLiveData.setValue(PresentationResource.Companion.success(weatherPhotos));
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        weatherResourceMutableLiveData.setValue(PresentationResource.Companion.domainError(e));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return weatherResourceMutableLiveData;
    }

}
