package com.example.githubapi.ui;

import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.githubapi.Data.TempData;
import com.example.githubapi.R;
import com.example.githubapi.model.Users;


import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private static List<Users> listusers;
    private Context context;

    public Adapter(List<Users> listusers, Context context) {
        Log.d("Adapter", String.valueOf(listusers.get(0).getLogin()));
        this.listusers = listusers;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
//
        holder.name.setText(listusers.get(position).getLogin());
        Glide.with(context)
                .load(listusers.get(position).getAvatar_url())
                .apply(
//                        RequestOptions.placeholderOf(R.drawable.ic_github)
                        RequestOptions.bitmapTransform(new CircleCrop())
                )
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.avatar);
        if (!listusers.get(position).isSite_admin()) {
            holder.site_admin.setVisibility(View.INVISIBLE);
        } else {
            holder.site_admin.setVisibility(View.VISIBLE);
        }
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TempData.currentlogin = listusers.get(position).getLogin();
                Intent intent = new Intent(context, Detail.class);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {

        return listusers.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView avatar;
        TextView site_admin;
        RelativeLayout relativeLayout;

        MyViewHolder(final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            avatar = itemView.findViewById(R.id.avatar);
            site_admin = itemView.findViewById(R.id.site_admin);
            relativeLayout = itemView.findViewById(R.id.recycleitem);
//            relativeLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//
//                }
//            });

        }
    }

    public static Bitmap toRoundCorner(Bitmap bitmap, float pixels) {
        System.out.println("图片是否变成圆角模式了+++++++++++++");
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);

        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        System.out.println("pixels+++++++" + pixels);

        return output;
    }
}

