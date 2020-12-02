package com.example.githubapi.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.githubapi.Data.TempData;
import com.example.githubapi.R;
import com.example.githubapi.api.ApiClient;
import com.example.githubapi.model.DetailsApi;

import static android.os.SystemClock.sleep;

public class Detail extends AppCompatActivity {
    ApiClient client = new ApiClient();
    Handler handler=new Handler();
    Button detailbtn;
    TextView detailname,detailbio,detaillogin,detaillocate,detailblog,detailsite;
    ImageView detailavatar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_detail);
        Log.d("TEMP",TempData.currentlogin);
        init();
        DetailThread();








    }

    private void init() {
        detailavatar=findViewById(R.id.detailavatar);
        detailbio=findViewById(R.id.detailbio);
        detailblog=findViewById(R.id.detailhyper);
        detailbtn=findViewById(R.id.back);
        detaillocate=findViewById(R.id.detaillocat);
        detaillogin=findViewById(R.id.detaillogin);
        detailname=findViewById(R.id.detailname);
        detailsite=findViewById(R.id.detailsite);
    }

    private void DetailThread(){

        Thread List= new Thread(new Runnable() {
            @Override
            public void run() {
                // do something in a new thread
                client.sendDetailGet("https://api.github.com/users/"+TempData.currentlogin);
                sleep(2000);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // do something to update UI when the thread job is completed
                        //
                        DetailList(TempData.DetailAPi);
                        // tv.setText("Job is done~");
                    }
                });
            }

        });
        List.start();

    }

    private void DetailList(DetailsApi detailsApi) {
        detailbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it =new Intent(Detail.this,List.class);
                Detail.this.startActivity(it);
                finish();
            }
        });
        Glide.with(this)
                .load(detailsApi.getAvatar_url())
                .apply(
//                        RequestOptions.placeholderOf(R.drawable.ic_github)
                        RequestOptions.bitmapTransform(new CircleCrop())
                )
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(detailavatar);
        detailname.setText(TempData.DetailAPi.getName());
        detailbio.setText(TempData.DetailAPi.getBio());
        detaillogin.setText(TempData.DetailAPi.getLogin());
        if (detailsApi.isSite_admin()){
            detailsite.setVisibility(View.VISIBLE);
        }else {
            detailsite.setVisibility(View.GONE);
        }
        detaillocate.setText(TempData.DetailAPi.getLocation());
        detailblog.setText(TempData.DetailAPi.getBlog());

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}