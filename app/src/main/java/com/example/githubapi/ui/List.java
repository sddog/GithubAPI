package com.example.githubapi.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;

import com.example.githubapi.Data.TempData;
import com.example.githubapi.R;
import com.example.githubapi.api.ApiClient;
import com.example.githubapi.model.Users;

import static android.os.SystemClock.sleep;


public class List extends AppCompatActivity {
    private java.util.List<Users> users;
    private Adapter adapter;
    private RecyclerView recyclerView;



    ApiClient client = new ApiClient();
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        ListThread();




    }

    public void userList(java.util.List<Users> list) {
        if (users != null) {
            users.clear();
        }
        users = list;
        adapter = new Adapter(users, this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void init() {

        recyclerView = findViewById(R.id.recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


    }
    private void ListThread(){

        Thread List= new Thread(new Runnable() {
            @Override
            public void run() {
                // do something in a new thread
                 client.sendUsersGet("https://api.github.com/users?Since=0&per_page=100");
                 sleep(3000);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // do something to update UI when the thread job is completed
                        //
                        userList(TempData.ListApi);
                        // tv.setText("Job is done~");
                    }
                });
            }

        });
        List.start();

    }

}