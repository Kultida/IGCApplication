package inducesmile.com.androidnavigation;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import inducesmile.com.androidnavigation.DetailFragment.IssueDetailFragment;

public class DetailActivity extends AppCompatActivity {

    String[] TAB_TITLES={"ภาพรวม","บันทึกงาน","แผนที่"};
    TabLayout tabLayout;
    ViewPager viewPager;
    int issue_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

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
                return IssueDetailFragment.newInstance();
            else if(position == 1)
                return IssueListFragment.newInstance();
            else
                return IssueListFragment.newInstance();

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
