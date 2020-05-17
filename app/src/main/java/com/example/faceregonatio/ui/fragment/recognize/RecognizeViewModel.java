package com.example.faceregonatio.ui.fragment.recognize;


import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.faceregonatio.data.database.api.ApiManager;
import com.example.faceregonatio.data.model.ImageResponse;

import com.squareup.picasso.Picasso;

import java.io.File;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;

public class RecognizeViewModel extends AndroidViewModel {

    private static final String TAG = "RecognizeViewModel";
    MutableLiveData<Boolean>getImage=new MutableLiveData<>();
    MutableLiveData<Boolean>recoginze=new MutableLiveData<>();


    private static final int READ_REQUEST_CODE = 300;
    MutableLiveData<String>image_url=new MutableLiveData<>();
    MutableLiveData<String>message=new MutableLiveData<>();



    public RecognizeViewModel(@NonNull Application application) {
        super(application);
    }
    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);

            Log.d(TAG, "dola getRealPathFromURIPath: ");
            //my touch
            return cursor.getString(idx);
        }
    }
    public void chooseImage(){
        getImage.setValue(true);
    }

    public void uploadImage(Uri image_uri , Activity activity) {

        String filePath = getRealPathFromURIPath(image_uri, activity);
        File file = new File(filePath);
        Log.d(TAG, "filePath=" + filePath);
        //RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("image", file.getName(), mFile);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());


        ApiManager.getAPIService()
                .faceRecognaize(fileToUpload,filename)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ImageResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onSuccess(ImageResponse imageResponse) {

                        //ip of pc
                        String  image_link="http://192.168.1.4:4000/"+ imageResponse.getImage().
                                replace("\"","");

                        Log.d(TAG, "dola onSuccess: " + image_link);

                        if (imageResponse.isSuccess())
                            image_url.setValue(image_link);

                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "dola onError api: " +
                                 e.getLocalizedMessage()+"\n"
                                +e.getMessage());
                        message.setValue(e.getLocalizedMessage());

                    }
                });
    }

    // Loading Image using Picasso Library.
    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url){
        Picasso.get().load(url).into(imageView);
    }


    public void recognize(Uri image_uri , Activity activity){
        Log.d(TAG, "dola recognize: hereee");

        if (EasyPermissions.hasPermissions(activity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Log.d(TAG, "dola recognize: uploaded");
            uploadImage(image_uri,activity);

        } else {
            Log.d(TAG, "dola recognize: died ");
            EasyPermissions.requestPermissions(activity, "permission died",
                    READ_REQUEST_CODE,
                    Manifest.permission.READ_EXTERNAL_STORAGE);
        }


    }

public void recognizeImage(){
        recoginze.setValue(true);
}



}
