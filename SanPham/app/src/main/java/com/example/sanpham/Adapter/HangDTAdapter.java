package com.example.sanpham.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lib.Model.brand;
import com.example.sanpham.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HangDTAdapter extends RecyclerView.Adapter<HangDTAdapter.HangDTViewHolder>{

    List<brand> list;
    Context context;
    int layout;
    private OnNoteListener mListener;

    public HangDTAdapter(List<brand> list, Context context, int layout, OnNoteListener mListener) {
        this.list = list;
        this.context = context;
        this.layout = layout;
        this.mListener = mListener;
    }

    public void setList(List<brand> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HangDTViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hang_sx,parent,false);
        return new HangDTViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull HangDTViewHolder holder, int position) {
        brand hangDT = list.get(position);
        if(hangDT == null)
            return;
        else
        {
            //holder.imgHangSX.setImageResource(hangDT.getBrandImg());
            Picasso.get().load(hangDT.getBrandImg()).into(holder.imgHangSX);
            holder.Name.setText(hangDT.getBrandName());
        }

        holder.itemView.setOnClickListener(view -> {
            mListener.onNoteClick(list.get(position));
        });
    }

    @Override
    public int getItemCount() {
        if(list!= null)
            return list.size();
        return 0;
    }

    public   class  HangDTViewHolder extends RecyclerView.ViewHolder {
        ImageView imgHangSX;
        TextView Name;

        public HangDTViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.Name_HangSX);
            imgHangSX = itemView.findViewById(R.id.imageView);
        }
    }

    public interface OnNoteListener{
        void onNoteClick(brand position);
    }
}
