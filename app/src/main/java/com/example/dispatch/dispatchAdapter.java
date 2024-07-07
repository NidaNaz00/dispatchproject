package com.example.dispatch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class dispatchAdapter extends RecyclerView.Adapter<dispatchAdapter.ViewHolder> {
    private List<dispatch> dispatchList;
    private Context context;

    public dispatchAdapter(List<dispatch> dispatchList, Context context) {
        this.dispatchList = dispatchList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dispatch_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        dispatch dispatch = dispatchList.get(position);
        holder.productId.setText(dispatch.getProductId());
        holder.truckId.setText(dispatch.getTruckId());
        holder.source.setText(dispatch.getSource());
        holder.destination.setText(dispatch.getDestination());
        holder.status.setText(dispatch.getStatus());
    }

    @Override
    public int getItemCount() {
        return dispatchList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productId, truckId, source, destination, status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productId = itemView.findViewById(R.id.productId);
            truckId = itemView.findViewById(R.id.truckId);
            source = itemView.findViewById(R.id.source);
            destination = itemView.findViewById(R.id.destination);
            status = itemView.findViewById(R.id.status);
        }
    }
}

