package com.spcba.bpassconductor.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.spcba.bpassconductor.R;
import com.spcba.bpassconductor.databinding.ItemScannedItemBinding;
import com.spcba.bpassconductor.datamodels.ScannedItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ScannedItemsAdapter extends RecyclerView.Adapter<ScannedItemsAdapter.ScannedItemsViewHolder>{
    private List<ScannedItem> scannedItems = new ArrayList<>();

    public ScannedItemsAdapter() {
    }
    public void insertItems(List<ScannedItem> scannedItems){
        this.scannedItems = scannedItems;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ScannedItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_scanned_item, parent, false);
        ItemScannedItemBinding binder = ItemScannedItemBinding.bind(view);
        return new ScannedItemsViewHolder(binder);


    }

    @Override
    public void onBindViewHolder(@NonNull ScannedItemsViewHolder holder, int position) {
        ScannedItem scannedItem = scannedItems.get(position);
        if (scannedItem.getType().equals("Receipt"))
            holder.scannedTypeIv.setImageDrawable(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.ic_receipt));
        else if (scannedItem.getType().equals("Ticket"))
            holder.scannedTypeIv.setImageDrawable(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.ic_bus_ticket_small));

        holder.scannedTypeTv.setText(scannedItem.getType());
        holder.amountTv.setText(String.valueOf(scannedItem.getAmount()));


        Date dateScanned = scannedItem.getDateScanned();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateScanned);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);

        holder.dateScannedTv.setText(month +"/"+day+"/"+year);
        holder.refId.setText(scannedItem.getRefId());
    }

    @Override
    public int getItemCount() {
        return scannedItems.size();
    }


    class ScannedItemsViewHolder extends RecyclerView.ViewHolder {
        private ImageView scannedTypeIv;
        private TextView scannedTypeTv;
        private TextView dateScannedTv;
        private TextView refId;
        private TextView amountTv;

        public ScannedItemsViewHolder(@NonNull ItemScannedItemBinding binder) {
            super(binder.getRoot());
            scannedTypeIv = binder.scannedItemTypeIv;
            scannedTypeTv = binder.scannedItemTypeTv;
            dateScannedTv = binder.dateTv;
            refId = binder.refIdTv;
            amountTv = binder.amountTv;
        }
    }
}
