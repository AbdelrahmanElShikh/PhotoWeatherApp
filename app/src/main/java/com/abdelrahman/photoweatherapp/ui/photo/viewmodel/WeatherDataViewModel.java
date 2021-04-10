package com.abdelrahman.photoweatherapp.ui.photo.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.abdelrahman.core.models.WeatherData;
import com.abdelrahman.core.resource.PresentationResource;
import com.abdelrahman.core.usecase.GetWeatherDataUseCase;
import com.abdelrahman.photoweatherapp.room.model.WeatherPhoto;
import com.abdelrahman.photoweatherapp.room.repository.LocalRepository;

import io.reactivex.CompletableObserver;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @Author : Abdel-Rahman El-Shikh
 * @Date : 10-Apr-21
 * @Project : com.abdelrahman.photoweatherapp.ui.photo
 */
public class WeatherDataViewModel extends AndroidViewModel {
    private final MutableLiveData<PresentationResource<WeatherData>> weatherDataLiveData = new MutableLiveData<>();
    private final MutableLiveData<PresentationResource<Boolean>> isPhotoSaved = new MutableLiveData<>();
    private final GetWeatherDataUseCase getWeatherDataUseCase = new GetWeatherDataUseCase();
    private final LocalRepository historyRepository;

    public WeatherDataViewModel(@androidx.annotation.NonNull Application application) {
        super(application);
        historyRepository = new LocalRepository(application);
    }

    public LiveData<PresentationResource<WeatherData>> getWeatherData(Double latitude, Double longitude) {
        getWeatherDataUseCase.getWeatherData(latitude, longitude)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PresentationResource<WeatherData>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        weatherDataLiveData.postValue(PresentationResource.Companion.loading());
                    }

                    @Override
                    public void onNext(@NonNull PresentationResource<WeatherData> weatherDataPresentationResource) {
                        weatherDataLiveData.setValue(weatherDataPresentationResource);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        weatherDataLiveData.setValue(PresentationResource.Companion.domainError(e));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return weatherDataLiveData;

    }

    public LiveData<PresentationResource<Boolean>> insertPhoto(WeatherPhoto weatherPhoto) {
        historyRepository.insertPhoto(weatherPhoto)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        isPhotoSaved.setValue(PresentationResource.Companion.loading());
                    }

                    @Override
                    public void onComplete() {
                        isPhotoSaved.setValue(PresentationResource.Companion.success(true));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        isPhotoSaved.setValue(PresentationResource.Companion.domainError(e));
                    }
                });
        return isPhotoSaved;
    }

}
