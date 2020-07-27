package cvjetkovic.retrofit2rest.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cvjetkovic.retrofit2rest.R;
import cvjetkovic.retrofit2rest.model.Item;

import java.util.List;

/**
 * Created by Vladimir Cvjetkovic
 */

public class AdapterItem extends RecyclerView.Adapter<AdapterItem.ViewHolder> {

    private List<Item> list;

    public AdapterItem(List<Item> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,
                parent, false);
        ViewHolder holder = new ViewHolder(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Item dataItem = list.get(position);
        holder.id.setText(dataItem.getId().toString());
        holder.name.setText(dataItem.getNama());
        holder.price.setText(dataItem.getPrice().toString());
        holder.dataItem = dataItem;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView id, name, price;
        Item dataItem;

        public ViewHolder(View itemView) {
            super(itemView);

            id = (TextView) itemView.findViewById(R.id.itemId);
            name = (TextView) itemView.findViewById(R.id.itemName);
            price = (TextView) itemView.findViewById(R.id.itemPrice);

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public Item getItem(int position) {
        return list.get(position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
