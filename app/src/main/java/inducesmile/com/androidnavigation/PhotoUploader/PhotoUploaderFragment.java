package inducesmile.com.androidnavigation.PhotoUploader;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import java.io.File;

import inducesmile.com.androidnavigation.R;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by gnomerock on 10/19/15.
 */
public class PhotoUploaderFragment extends Fragment{



    private static final int SELECT_PICTURE = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private Button btnCapturePicture;
    private Button btnChoosePicture;
    private String selectedImagePath;
    String mCurrentPhotoPath;

    private int issue_id;

    public static PhotoUploaderFragment newInstance(int issue_id) {
        PhotoUploaderFragment fragment = new PhotoUploaderFragment();
        fragment.issue_id = issue_id;
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.photo_uploader, container, false);

        btnChoosePicture = (Button) view.findViewById(R.id.btnFromCamera);
        btnCapturePicture = (Button) view.findViewById(R.id.btnFromGallery);

        btnChoosePicture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // capture picture
                selectImage();
            }
        });

        btnCapturePicture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // capture picture
                takePhoto();
            }
        });


        if (!isDeviceSupportCamera()) {
            Toast.makeText(getContext(),
                    "Sorry! Your device doesn't support camera",
                    Toast.LENGTH_LONG).show();
            // will close the app if the device does't have camera
            getActivity().finish();
        }

        return view;

    }

    private void takePhoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    private void selectImage() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }

    }


    private boolean isDeviceSupportCamera() {
        if (getContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
                Uri myUri = Uri.parse(selectedImagePath);
                sendPhoto(myUri);
                Log.i("TEST","sending... "+selectedImagePath);
            }
            else if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");

            }
        }
    }

    /**
     * helper to retrieve the path of an image URI
     */
    public String getPath(Uri uri) {
        // just some safety built in
        if( uri == null ) {
            // TODO perform some logging or show user feedback
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        // this is our fallback here
        return uri.getPath();
    }

    public void sendPhoto(Uri uri){

        ImageView preview = (ImageView) getView().findViewById(R.id.image_preview);
        preview.setImageURI(uri);
        File photo = new File(uri.getPath());
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), photo);
        Log.i("TEST",requestBody.contentType().toString());


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://igc.kmodoo.com:8888")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        uploadInterface photoUploader = retrofit.create(uploadInterface.class);


        Call<UploadStatus> uploadResult = photoUploader.upload(requestBody);

        uploadResult.enqueue(new Callback<UploadStatus>() {

            @Override
            public void onResponse(Response<UploadStatus> response, Retrofit retrofit) {
                UploadStatus result = response.body();
                //                if(!result.error){
//                    Log.i("TEST","photo upload failed");
//                }else {
//                    Log.i("TEST","photo sent success");
//                }

            }

            @Override
            public void onFailure(Throwable t) {
                Log.i("test", "ERROR!!");
            }
        });
    }



}
