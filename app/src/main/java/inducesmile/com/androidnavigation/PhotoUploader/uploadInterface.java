package inducesmile.com.androidnavigation.PhotoUploader;


import com.squareup.okhttp.RequestBody;

import retrofit.Call;
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
    @POST("/upload")
    Call<UploadStatus> uploadWithIssueID(
            @Part("file\"; filename=\"image.jpg\" ") RequestBody file,
            @Part("issue_id") Integer issue_id,
            @Part("image_name") String image_name
    );

}