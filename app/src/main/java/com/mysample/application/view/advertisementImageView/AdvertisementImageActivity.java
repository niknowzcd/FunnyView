package com.mysample.application.view.advertisementImageView;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.mysample.application.R;


/**
 * 仿知乎广告效果
 * Created by dly on 2018/2/24.
 */

public class AdvertisementImageActivity extends AppCompatActivity {

    RecyclerView rv_content;
    private static final int LIST_TYPE_AD = 0x11;
    private static final int LIST_TYPE_NORMAL = LIST_TYPE_AD + 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_window);

        rv_content = (RecyclerView) findViewById(R.id.rv_content);
        rv_content.setLayoutManager(new LinearLayoutManager(this));
        rv_content.setAdapter(new MyAdapter());
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

        @Override
        public int getItemViewType(int position) {
            if (position == 10) {
                return LIST_TYPE_AD;
            }
            return LIST_TYPE_NORMAL;
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            if (viewType == LIST_TYPE_AD) {
                view = View.inflate(AdvertisementImageActivity.this, R.layout.item1, null);
            } else {
                view = View.inflate(AdvertisementImageActivity.this, R.layout.item0, null);
                RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                view.setLayoutParams(lp);
            }
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            if (position == 10) {
                holder.windowImageView.bindRecyclerView(rv_content);
                holder.windowImageView.setImageResource(R.drawable.timg);
            } else {
                holder.itemView.setBackgroundColor(Color.rgb((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
            }
        }

        @Override
        public int getItemCount() {
            return 20;
        }

        class MyHolder extends RecyclerView.ViewHolder {
            AdvertisementImageView2 windowImageView;
            MyHolder(View itemView) {
                super(itemView);
                windowImageView = itemView.findViewById(R.id.wiv);
                windowImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        }
    }
}
