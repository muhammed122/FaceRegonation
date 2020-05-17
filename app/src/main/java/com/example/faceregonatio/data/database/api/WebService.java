package com.example.faceregonatio.data.database.api;

import com.example.faceregonatio.data.model.ImageResponse;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface WebService {

    @Multipart   //recognize
    @POST("face/recognize/")
    Single<ImageResponse>faceRecognaize(@Part MultipartBody.Part image,
                                        @Part("image") RequestBody name);

}
