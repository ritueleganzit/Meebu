package com.meebu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.meebu.R;
import com.meebu.model.SearchData;

import java.util.ArrayList;
import java.util.List;

public class SearchesAdapter extends RecyclerView.Adapter<SearchesAdapter.MyViewHolder> implements Filterable {

    private Context context;
    private List<SearchData> searchList;
    private List<SearchData> searchListFiltered;
    private ContactsAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, phone;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            phone = view.findViewById(R.id.phone);
            thumbnail = view.findViewById(R.id.thumbnail);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onContactSelected(searchListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }


    public SearchesAdapter(Context context, List<SearchData> searchList, ContactsAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.searchList = searchList;
        this.searchListFiltered = searchList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_row_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final SearchData searchData = searchList.get(position);
        holder.name.setText(searchData.getStreet());
        holder.phone.setText(searchData.getAddress());

    }

    @Override
    public int getItemCount() {
        return searchListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    searchListFiltered = searchList;
                } else {
                    List<SearchData> filteredList = new ArrayList<>();
                    for (SearchData row : searchList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getStreet().toLowerCase().contains(charString.toLowerCase()) || row.getCity().contains(charSequence) || row.getState().contains(charSequence) || row.getCountry().contains(charSequence) || row.getAddress().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    searchListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = searchListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                searchListFiltered = (ArrayList<SearchData>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface ContactsAdapterListener {
        void onContactSelected(SearchData searchData);
    }

}
