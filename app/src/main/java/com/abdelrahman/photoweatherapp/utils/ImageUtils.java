package com.abdelrahman.photoweatherapp.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @Author : Abdel-Rahman El-Shikh
 * @Date : 09-Apr-21
 * @Project : com.abdelrahman.photoweatherapp.utils
 */
public class ImageUtils {
    private static final String TAG = "IntentUtils";
    public static final String AUTHORITY = "com.abdelrahman.android.fileprovider";
    private static final String IMAGE_TYPE = "image/*";
    private static String imagePath;
    private static File file;

    public static File createImageFile(Context context) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(
                imageFileName,  /*   prefix    */
                ".jpg",   /*   suffix    */
                storageDir      /*   directory */
        );

        imagePath = imageFile.getAbsolutePath();
        return imageFile;
    }


    @SuppressLint("QueryPermissionsNeeded")
    public static void dispatchTakePictureIntent(Fragment fragment, int requestCode) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(fragment.getContext().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile(fragment.getContext());
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.e(TAG, "dispatchTakePictureIntent: ", ex);
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(fragment.getContext(),
                        AUTHORITY,
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                fragment.startActivityForResult(takePictureIntent, requestCode);
            }
        }
    }

    private static Bitmap convertViewToBitmap(View view) {
        Bitmap b = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.RGB_565);
        Canvas c = new Canvas(b);
        view.draw(c);
        return b;
    }

    public static void saveImageIntoTempFile(Activity activity, View v) {

        File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File myDir = new File(storageDir + "/weather_images");
        myDir.mkdirs();

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        imagePath = "robusta_" + timeStamp + ".jpg";

        file = new File(myDir, imagePath);

        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            convertViewToBitmap(v).compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void shareImage(Fragment fragment,String imagePath) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(IMAGE_TYPE);
        if(getFile() == null){
            File storageDir = fragment.getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File myDir = new File(storageDir + "/weather_images");
            file = new File(myDir, imagePath);
        }
        File photoFile = new File(getFile().getPath());
        Uri myPhotoFileUri = FileProvider.getUriForFile(fragment.getActivity(),
                ImageUtils.AUTHORITY, photoFile);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(Intent.EXTRA_STREAM, myPhotoFileUri);
        fragment.startActivity(Intent.createChooser(intent, "Share Image with..."));
    }

    public static String getImagePath() {
        return imagePath;
    }

    public static boolean deleteImageFromStorage() {
        try {
            File file = new File(imagePath);
            return file.delete();
        } catch (NullPointerException e) {
            //File is already deleted.
            e.printStackTrace();
            return false;
        }
    }

    public static File getFile() {
        return file;
    }

}
