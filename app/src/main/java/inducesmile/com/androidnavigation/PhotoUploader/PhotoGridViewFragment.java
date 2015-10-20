package inducesmile.com.androidnavigation.PhotoUploader;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import inducesmile.com.androidnavigation.ImageAdapter;
import inducesmile.com.androidnavigation.R;

/**
 * Created by gnomerock on 10/20/15.
 */
public class PhotoGridViewFragment extends Fragment{


    public static PhotoGridViewFragment newInstance(int issue_id) {
        PhotoGridViewFragment fragment = new PhotoGridViewFragment();
//        fragment.issue_id = issue_id;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_grid, container, false);

        GridView gridview = (GridView) view.findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(getContext()));
        return view;
    }
}
