package inducesmile.com.androidnavigation.PhotoUploader;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

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
 * Created by gnomerock on 10/20/15.
 */
public class PhotoGridViewFragment extends Fragment{


    private static final int SELECT_PICTURE = 1;
    private String selectedImagePath;
    private ImageAdapter imageAdapter;
    private Integer issue_id;
    GridView gridview;

    public static PhotoGridViewFragment newInstance(int issue_id) {
        PhotoGridViewFragment fragment = new PhotoGridViewFragment();
        fragment.issue_id = issue_id;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_grid, container, false);
        gridview = (GridView) view.findViewById(R.id.gridview);
        imageAdapter = new ImageAdapter(getContext(),issue_id);
        imageAdapter.clear();
        gridview.setAdapter(imageAdapter);

        FloatingActionButton myFab = (FloatingActionButton)  view.findViewById(R.id.myFAB);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selectImage();
            }
        });

        return view;
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
                Uri myUri = Uri.parse(selectedImagePath);
                sendPhoto(myUri);
                Log.i("TEST", "sending... " + selectedImagePath);
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

        File photo = new File(uri.getPath());
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), photo);
        Log.i("TEST",requestBody.contentType().toString());


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://igc.kmodoo.com:8888")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        uploadInterface photoUploader = retrofit.create(uploadInterface.class);

        String[] temp = uri.getPath().split("/");
        Call<UploadStatus> uploadResult = photoUploader.uploadWithIssueID(requestBody, issue_id,temp[temp.length-1]);
        Log.i("test","uploading file name:"+temp[temp.length-1]);
        uploadResult.enqueue(new Callback<UploadStatus>() {

            @Override
            public void onResponse(Response<UploadStatus> response, Retrofit retrofit) {
                UploadStatus result = response.body();

                if(result.error){
                    Log.i("TEST","photo upload failed");
                }else {
                    Log.i("TEST","photo sent success");
                }

            }

            @Override
            public void onFailure(Throwable t) {
                Log.i("test", "ERROR!!");
            }
        });
    }
}
