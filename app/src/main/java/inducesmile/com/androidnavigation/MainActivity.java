package inducesmile.com.androidnavigation;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


public class MainActivity extends ActionBarActivity {


    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    String[]titles = {"ภาพรวม", "บันทึกงาน", "แผนที่", "อื่นๆ"};
    private CharSequence mTitle;
    private CharSequence mDrawerTitle;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar topToolBar;
    TextView tv;
    private ArrayList<Issue> issuelist;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    //TextView id, name, description, user_id;

    String url = "http://igc.kmodoo.com:8888";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tv = (TextView) findViewById(R.id.txtResult);
      // pbar = (ProgressBar) findViewById(R.id.pb);
        mTitle = mDrawerTitle = getTitle();

        topToolBar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(topToolBar);
        topToolBar.setLogoDescription(getResources().getString(R.string.logo_desc));

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        final List<ItemObject> listViewItems = new ArrayList<ItemObject>();
        listViewItems.add(new ItemObject("ภาพรวม", R.drawable.imagetwo));
        listViewItems.add(new ItemObject("บันทึกงาน", R.drawable.imageone));
        listViewItems.add(new ItemObject("แผนที่", R.drawable.imagetwo));
        listViewItems.add(new ItemObject("อื่นๆ", R.drawable.imagefour));

        mDrawerList.setAdapter(new CustomAdapter(this, listViewItems));

        mDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.setDrawerIndicatorEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // make Toast when click
                Toast.makeText(getApplicationContext(), "Position " + position, Toast.LENGTH_LONG).show();
                selectItemFragment(position);
            }
        });
        final SwipeRefreshLayout swipeView = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeView.setRefreshing(true);
                Log.d("Swipe", "Refreshing Number");
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeView.setRefreshing(false);
                        double f = Math.random();
                        //rndNum.setText(String.valueOf(f));
                    }
                }, 3000);
            }
        });

        ///API
        // id = (TextView) findViewById(R.id.txt_id);
        // name = (TextView) findViewById(R.id.txt_name);
        //description = (TextView) findViewById(R.id.txt_Des);
        // user_id = (TextView) findViewById(R.id.txt_userid);

        //making object of Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Creating Rest Services
        RestInterface restInterface = retrofit.create(RestInterface.class);
        Call<ArrayList<Issue>> searching = restInterface.searchIssue();


        //Calling method to get
        searching.enqueue(new Callback<ArrayList<Issue>>() {


            @Override
            public void onResponse(Response<ArrayList<Issue>>  response) {
                if (response.isSuccess()) {
                    Log.println(Log.INFO, "Hey", response.toString());
                     issuelist = response.body();
                  //  tv.setText("Id:" + issue.getId() + "\nName :" + issue.getName() + "\nDescription :" + issue.getDescription() + "\nUserId :" + issue.getUser_id());

                    Toast.makeText(getBaseContext(), "การค้นหา", Toast.LENGTH_SHORT).show();

                } else {
                    //request not successful (like 400,401,403 etc)
                    //Handle errors
                }

            }

            @Override
            public void onFailure(Throwable t) {
                Log.println(Log.ERROR, "GGWP", t.toString());
                Toast.makeText(getBaseContext(), "ไม่พบการค้นหา", Toast.LENGTH_SHORT).show();
            }
        });





    }


    private void selectItemFragment(int position){

        Fragment fragment = null;
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch(position) {
            default:
            case 0:
                fragment = new DefaultFragment();
                break;
            case 1:
                fragment = new DefaultFragment();
                break;
            case 2:
                fragment = new DefaultFragment();
                break;
            case 3:
                fragment = new DefaultFragment();
                break;
        }
        fragmentManager.beginTransaction().replace(R.id.main_fragment_container, fragment).commit();

        mDrawerList.setItemChecked(position, true);
        setTitle(titles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        // outState.putBooleanArray("status", status);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public class myAdapter extends RecyclerView.Adapter<myAdapter.ViewHolder> {

        private ArrayList<Issue> mIssues;

        public myAdapter(ArrayList<Issue> issues) {

            mIssues = issues;
        }

        public  class ViewHolder extends RecyclerView.ViewHolder{
            public TextView id;
            public TextView name;
            public TextView description;
            public TextView user_id;


            public ViewHolder(View itemView) {
                super(itemView);
                id = (TextView) itemView.findViewById(R.id.id);
                name = (TextView) itemView.findViewById(R.id.name);
                description = (TextView) itemView.findViewById(R.id.description);
                user_id = (TextView) itemView.findViewById(R.id.user_id);
            }
        }



        @Override
        public myAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view  = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.result_issue,parent,false);

            ViewHolder viewHolder = new ViewHolder(view);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Issue issue = mIssues.get(position);
            holder.id.setText(issue.id);
            holder.name.setText(issue.name);
            holder.description.setText(issue.description);
            holder.user_id.setText(issue.id);
        }

        @Override
        public int getItemCount() {
            return mIssues.size();
        }
    }


}

