package inducesmile.com.androidnavigation.PhotoUploader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import inducesmile.com.androidnavigation.R;

/**
 * Created by gnomerock on 10/22/15.
 */
public class PhotoDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_detail_activity);

        String path = getIntent().getStringExtra("image_path");

        ImageView imageView = (ImageView) findViewById(R.id.image);

        Glide.with(getApplicationContext())
                .load(path)
                .placeholder(R.drawable.photo_placeholder_loading)
                .into(imageView);

    }
}