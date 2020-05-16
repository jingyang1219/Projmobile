package com.example.projectmobile.presentation.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectmobile.R;
import com.example.projectmobile.presentation.model.Dog;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<Dog> values;
    private OnItemClickListener listener;
    private Context context;
    private ImageView imageView;


    public interface OnItemClickListener {
        void onItemClick(Dog item);
    }



    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtHeader;
        TextView txtFooter;

        View layout;

        ViewHolder(View v) {
            super(v);
            layout = v;
            txtHeader = (TextView) v.findViewById(R.id.firstLine);
            txtFooter = (TextView) v.findViewById(R.id.secondLine);
            imageView = (ImageView) v.findViewById(R.id.icon_dog);
        }
    }

    public void add(int position, Dog item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    private void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    public ListAdapter(List<Dog> myDataset, Context context1, OnItemClickListener listener ) {
        this.values = myDataset;
        this.listener = listener;
        this.context = context1;

    }


    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent,
            int viewType
    ) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Dog curdog = values.get(position);

        holder.txtHeader.setText(curdog.getBreed());
        holder.txtHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(position);
            }
        });

        holder.txtFooter.setText(curdog.getOrigin());

        Glide.with(context).load(curdog.getImageurl()).optionalFitCenter().into(imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(curdog);
            }
        });

    }

    @Override
    public int getItemCount() {
        return values.size();
    }
}