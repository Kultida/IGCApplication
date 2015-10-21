package inducesmile.com.androidnavigation.PhotoUploader;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class ImageAdapter extends ArrayAdapter {
    private Context mContext;
    private ArrayList<photoPath> imageUrl;
    private Integer issue_id;

    public ImageAdapter(Context c,Integer issue_id) {
        super(c,0);

        imageUrl = new ArrayList<>();
        mContext = c;
        this.issue_id = issue_id;
        getPhotoPath();
//        imageUrl.add("http://igc.kmodoo.com:8888/uploads/image.jpg");
//        imageUrl.add("http://igc.kmodoo.com:8888/uploads/image.jpg");
//        imageUrl.add("http://igc.kmodoo.com:8888/uploads/image.jpg");
//        imageUrl.add("http://igc.kmodoo.com:8888/uploads/image.jpg");
//        imageUrl.add("http://igc.kmodoo.com:8888/uploads/image.jpg");
//        imageUrl.add("http://igc.kmodoo.com:8888/uploads/image.jpg");
    }

    public int getCount() {
        return imageUrl.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView;

        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(250 , 250));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }
        Glide.with(mContext).load(imageUrl.get(position).file_path).into(imageView);
//        Glide.with(get).load("http://goo.gl/gEgYUd").into(imageView);
//        imageView.setImageURI(imageUrl.get(position));
        return imageView;
    }

    public void getPhotoPath(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://igc.kmodoo.com:8888")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        uploadInterface photoUploader = retrofit.create(uploadInterface.class);


        Call<ArrayList<photoPath>> getPhotoPath = photoUploader.getPhotoPath(issue_id);

        getPhotoPath.enqueue(new Callback<ArrayList<photoPath>>() {

            @Override
            public void onResponse(Response<ArrayList<photoPath>> response, Retrofit retrofit) {
                imageUrl = response.body();
                notifyDataSetInvalidated();

            }

            @Override
            public void onFailure(Throwable t) {
                Log.i("test", "ERROR!!");
            }
        });
    }

}
