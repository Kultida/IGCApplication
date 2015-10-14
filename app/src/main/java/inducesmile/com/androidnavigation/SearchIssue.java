package inducesmile.com.androidnavigation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by User on 14/10/2558.
 */
public class SearchIssue extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Issue> issueList;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.search_issue);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            //Your toolbar is now an action bar and you can use it like you always do, for example:
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //making object of Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( "http://igc.kmodoo.com:8888")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RestInterface search = retrofit.create(RestInterface.class);


        Call<ArrayList<Issue>> searching = search.searchIssue();



        searching.enqueue(new Callback<ArrayList<Issue>>() {
            @Override
            public void onResponse(Response<ArrayList<Issue>> response) {

                issueList = response.body();
                mAdapter = new myAdapter(issueList);
                mRecyclerView.setAdapter(mAdapter);
                Toast.makeText(getBaseContext(), "พบการค้นหา: " + response.body().size(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Throwable t) {
                Log.println(Log.ERROR, "GGWP", t.toString());
                Toast.makeText(getBaseContext(), "FAILED...", Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


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

