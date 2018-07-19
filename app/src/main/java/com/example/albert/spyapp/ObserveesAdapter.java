package com.example.albert.spyapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ObserveesAdapter extends RecyclerView.Adapter<ObserveesAdapter.ViewHolder> {
    private ArrayList<Observee> observeesList;
    private Context context;

    public ObserveesAdapter(Context context, ArrayList<Observee> List) {
        this.observeesList = List;
        this.context = context;
    }

    public void addObservee(Observee obs) {
        observeesList.add(obs);
        notifyDataSetChanged();
    }

    public void update() {
        notifyDataSetChanged();
    }

    @Override
    public ObserveesAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.observees_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ObserveesAdapter.ViewHolder viewHolder, int i) {
        viewHolder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        viewHolder.login.setText(observeesList.get(i).getLogin());
        int[] colors = {Color.parseColor(observeesList.get(i).getColour()),
                Color.parseColor(observeesList.get(i).getColour())};
        Bitmap bitmap = Bitmap.createBitmap(colors, 2, 1, Bitmap.Config.RGB_565);
        viewHolder.img.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return observeesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView img;
        private TextView login;

        public ViewHolder(View view) {
            super(view);
            img = (CircleImageView) view.findViewById(R.id.profile_colour);
            login = (TextView) view.findViewById(R.id.login_text);
        }
    }
}
