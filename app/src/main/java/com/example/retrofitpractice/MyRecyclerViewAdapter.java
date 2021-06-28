package com.example.retrofitpractice;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofitpractice.modelandentity.EntityResult;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {


    public void setItem(EntityResult tempEntityResult, int tempImageUpdatePosition) {
        this.results.set(tempImageUpdatePosition,tempEntityResult);
        notifyItemChanged(tempImageUpdatePosition);
    }

    public interface MyEditInterface {
        public void editAt(EntityResult entityResult,int position);
    }

    private ArrayList<EntityResult> results = new ArrayList<>();
    private MyEditInterface myEditInterface;

    public void setResults(ArrayList<EntityResult> results) {
        this.results.clear();
        this.results.addAll(results);
        notifyDataSetChanged();
    }

    public void setMyEditInterface(MyEditInterface myEditInterface) {
        this.myEditInterface = myEditInterface;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.view_holder, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tvCountry.setText((results.get(position).getCountry() == null) ? "Not Available" : results.get(position).getCountry());
        holder.tvMfrName.setText((results.get(position).getMfrName() == null) ? "Not Available" : results.get(position).getMfrName());
        holder.tvMfrId.setText((results.get(position) == null) ? "Not Available" : String.valueOf(results.get(position).getMfrId()));
        holder.tvVehiclesCount.setText((results.get(position) == null) ? "Not Available" : String.valueOf(results.get(position).getVehicleCount()));
        holder.tvPos.setText(String.valueOf(position + 1));
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (myEditInterface != null) {
                  //  notifyItemChanged(position);
                    myEditInterface.editAt(results.get(position),position);
                }

            }
        });
        if( results.get(position).isImageStatus() == true) {
            Log.e("RECYCLER",""+position+" "+results.get(position).isImageStatus());
            holder.imageView.setVisibility(View.VISIBLE);
            Bitmap b1 = BitmapFactory.decodeByteArray(results.get(position).getImage() , 0, results.get(position).getImage() .length);
            holder.imageView.setImageBitmap(b1);
        }
        else
        {
            holder.imageView.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return ((results != null) ? results.size() : 0);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvCountry;
        public TextView tvMfrId;
        public TextView tvMfrName;
        public TextView tvVehiclesCount;
        public TextView tvPos;
        public ImageView imageView;
        public ImageButton imageButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCountry = (TextView) itemView.findViewById(R.id.textView5);
            tvMfrId = (TextView) itemView.findViewById(R.id.textView7);
            tvMfrName = (TextView) itemView.findViewById(R.id.textView6);
            tvVehiclesCount = (TextView) itemView.findViewById(R.id.textView8);
            tvPos = (TextView) itemView.findViewById(R.id.tv_pos);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            imageButton = (ImageButton) itemView.findViewById(R.id.imageButton);
        }
    }
}
