package inducesmile.com.androidnavigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import inducesmile.com.androidnavigation.DetailFragment.IssueDetailFragment;
import inducesmile.com.androidnavigation.PhotoUploader.PhotoGridViewFragment;

public class DetailActivity extends AppCompatActivity {

    String[] TAB_TITLES={"ภาพรวม","บันทึกงาน","แผนที่"};
    TabLayout tabLayout;
    ViewPager viewPager;
    int issue_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //get issue id from intent
        Intent intent = getIntent();
        issue_id = Integer.parseInt(intent.getStringExtra("issue_id"));
        Log.i("test","Receive issue id :"+Integer.toString(issue_id));

        tabLayout = (TabLayout) findViewById(R.id.main_tab_layout);
        viewPager = (ViewPager) findViewById(R.id.main_view_pager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);



        loadPager();
    }

    public void loadPager() {
        if (viewPager.getAdapter() == null)
            viewPager.setAdapter(new MainFragmentAdapter(getSupportFragmentManager()));
        else
            viewPager.getAdapter().notifyDataSetChanged();
        tabLayout.setupWithViewPager(viewPager);
    }

    private class MainFragmentAdapter extends FragmentPagerAdapter {

        public MainFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            if(position == 0)
                return IssueDetailFragment.newInstance(issue_id);
            else if(position == 1)
                return PhotoGridViewFragment.newInstance(issue_id);
            else
                return PhotoGridViewFragment.newInstance(issue_id);

        }

        @Override
        public int getCount() {
            return TAB_TITLES.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TAB_TITLES[position];
        }
    }

}
