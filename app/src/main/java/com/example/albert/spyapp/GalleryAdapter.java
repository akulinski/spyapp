package com.example.albert.spyapp;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.CropSquareTransformation;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
    private ArrayList<Photo> galleryList;
    private Context context;

    public GalleryAdapter(Context context, ArrayList<Photo> galleryList) {
        this.galleryList = galleryList;
        this.context = context;
    }

    public void addPhoto(Photo photo){
        galleryList.add(0,photo);
        notifyDataSetChanged();
    }

    public void updateWholeData(ArrayList<Photo> gallery){
        galleryList.clear();
        galleryList.addAll(gallery);
        notifyDataSetChanged();
    }

    public void update(){
        notifyDataSetChanged();
    }

    @Override
    public GalleryAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.gallery_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GalleryAdapter.ViewHolder viewHolder, int i) {
        viewHolder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        viewHolder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imgIntent = new Intent(context, ImageViewer.class);
                imgIntent.putExtra("url", galleryList.get(i).getUrl());
                context.startActivity(imgIntent);
            }
        });
        Picasso
                .with(this.context)
                .load(galleryList.get(i).getUrl())
                .transform(new CropSquareTransformation())
                .into(viewHolder.img);
    }

    @Override
    public int getItemCount() {
        return galleryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        public ViewHolder(View view) {
            super(view);
            img = (ImageView) view.findViewById(R.id.image);
        }
    }
}