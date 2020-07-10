package com.sakyo.azurcompendium;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SelectorAdapter extends RecyclerView.Adapter<SelectorAdapter.SelectorViewHolder> {

    private ArrayList<CardsShip> mList;

    private OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class SelectorViewHolder extends RecyclerView.ViewHolder {

        public ImageView iconShip;
        public TextView lblNameShip;

        public SelectorViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            iconShip = itemView.findViewById(R.id.viewImgShip);
            lblNameShip = itemView.findViewById(R.id.viewLblNameShip);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public SelectorAdapter (ArrayList<CardsShip> list) {
        mList = list;
    }

    @NonNull
    @Override
    public SelectorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_ship, parent, false);
        SelectorViewHolder evh = new SelectorViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull SelectorViewHolder holder, int position) {
        CardsShip currentItem = mList.get(position);

        holder.iconShip.setImageResource(currentItem.getIcon());
        holder.lblNameShip.setText(currentItem.getName());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
