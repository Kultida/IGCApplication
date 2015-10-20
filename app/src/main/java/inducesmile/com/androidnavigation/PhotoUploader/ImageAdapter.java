package inducesmile.com.androidnavigation.PhotoUploader;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> imageUrl;

    public ImageAdapter(Context c) {
        mContext = c;
        imageUrl.add("http://igc.kmodoo.com:8888/upload/test.jpg");
        imageUrl.add("http://igc.kmodoo.com:8888/upload/test.jpg");
        imageUrl.add("http://igc.kmodoo.com:8888/upload/test.jpg");
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
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }
        Glide.with(mContext).load(imageUrl.get(position)).into(imageView);
//        Glide.with(get).load("http://goo.gl/gEgYUd").into(imageView);
//        imageView.setImageURI(imageUrl.get(position));
        return imageView;
    }

}
