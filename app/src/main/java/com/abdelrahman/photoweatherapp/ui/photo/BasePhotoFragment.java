package com.abdelrahman.photoweatherapp.ui.photo;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.abdelrahman.photoweatherapp.R;
import com.abdelrahman.photoweatherapp.utils.ImageUtils;

/**
 * @Author : Abdel-Rahman El-Shikh
 * @Date : 10-Apr-21
 * @Project : com.abdelrahman.photoweatherapp.ui.photo
 */
public abstract class BasePhotoFragment extends Fragment {
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.share_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_share) {
            doShareLogic();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void doShareLogic() {
        if (allowShare()) {
            ImageUtils.shareImage(this, getImagePath());
        } else {
            Toast.makeText(getContext(), R.string.not_allowed, Toast.LENGTH_SHORT).show();
        }
    }

    abstract String getImagePath();
    abstract boolean allowShare();
}
