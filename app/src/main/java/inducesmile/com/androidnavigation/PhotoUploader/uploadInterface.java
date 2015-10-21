package inducesmile.com.androidnavigation.PhotoUploader;


import com.squareup.okhttp.RequestBody;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;

public interface uploadInterface

{
    @Multipart
    @POST("/upload")
    Call<UploadStatus> upload(
            @Part("file\"; filename=\"image.jpg\"") RequestBody file
    );

    @Multipart
    @POST("/uploadBeta")
    Call<UploadStatus> uploadWithIssueID(
            @Part("file\"; filename=\"image.jpg\" ") RequestBody file,
            @Part("issue_id") Integer issue_id,
            @Part("image_name") String image_name
    );

    @FormUrlEncoded
    @POST("/getPhotoList")
    Call<ArrayList<photoPath>> getPhotoPath(
      @Field("issue_id") Integer issue_id
    );

}