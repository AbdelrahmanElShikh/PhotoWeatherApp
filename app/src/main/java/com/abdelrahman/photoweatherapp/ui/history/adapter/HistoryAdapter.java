package com.abdelrahman.photoweatherapp.ui.history.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.abdelrahman.photoweatherapp.R;
import com.abdelrahman.photoweatherapp.databinding.WeatherImageItemBinding;
import com.abdelrahman.photoweatherapp.room.model.WeatherPhoto;
import com.abdelrahman.photoweatherapp.ui.history.listeners.OnHistoryItemClick;

import java.util.List;

/**
 * @Author : Abdel-Rahman El-Shikh
 * @Date : 09-Apr-21
 * @Project : com.abdelrahman.photoweatherapp.ui.history.adapter
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.WeatherPhotosViewHolder> {
    private final List<WeatherPhoto> weatherPhotos;
    private final OnHistoryItemClick listener;

    public HistoryAdapter(List<WeatherPhoto> weatherPhotos, OnHistoryItemClick listener) {
        this.weatherPhotos = weatherPhotos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public WeatherPhotosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weather_image_item, parent, false);
        return new WeatherPhotosViewHolder(view.getRootView());
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherPhotosViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return weatherPhotos == null || weatherPhotos.isEmpty() ? 0 : weatherPhotos.size();
    }

    public class WeatherPhotosViewHolder extends RecyclerView.ViewHolder {
        private final WeatherImageItemBinding binding;

        public WeatherPhotosViewHolder(@NonNull View itemView) {
            super(itemView);
            this.binding = DataBindingUtil.bind(itemView);
        }

        public void bind(int position) {
            WeatherPhoto weatherPhoto = weatherPhotos.get(position);
            binding.setPhoto(weatherPhoto);
            binding.container.setOnClickListener(view -> listener.OnHistoryItemClick(weatherPhoto));
        }
    }
}
