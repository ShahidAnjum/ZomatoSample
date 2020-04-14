package com.sample.zomatosample.ui.search;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.sample.zomatosample.R;
import com.sample.zomatosample.data.model.api.restuarantsresponse.Restaurant_;
import com.sample.zomatosample.databinding.RowRestuarantBinding;
import com.sample.zomatosample.ui.search.SearchActivity.ListItemClickListener;

public class NestedRestuarantAdapter extends ListAdapter<Restaurant_, NestedRestuarantAdapter.RestuarantHolder> {

    private static DiffUtil.ItemCallback<Restaurant_> DIFF_CALLBACK = new DiffUtil.ItemCallback<Restaurant_>() {

        @Override
        public boolean areItemsTheSame(@NonNull Restaurant_ oldItem, @NonNull Restaurant_ newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Restaurant_ oldItem, @NonNull Restaurant_ newItem) {
            return oldItem.getId().equals(newItem.getId());
        }
    };

    final ListItemClickListener listItemClickListener;

    public NestedRestuarantAdapter(ListItemClickListener listItemClickListener) {
        super(DIFF_CALLBACK);
        this.listItemClickListener = listItemClickListener;
    }

    @NonNull
    @Override
    public RestuarantHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowRestuarantBinding binding = DataBindingUtil.inflate(LayoutInflater
                .from(parent.getContext()), R.layout.row_restuarant, parent, false);
        return new RestuarantHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RestuarantHolder holder, int position) {
        Restaurant_ restaurant = getItem(position);
        if (restaurant != null) {
            holder.bindTo(restaurant, listItemClickListener);
        }
    }

    static class RestuarantHolder extends RecyclerView.ViewHolder {
        RowRestuarantBinding mBinding;

        RestuarantHolder(RowRestuarantBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        void bindTo(Restaurant_ restaurant, ListItemClickListener listItemClickListener) {
            mBinding.setRestuarant(restaurant);
            mBinding.setClickListener(listItemClickListener);
            mBinding.executePendingBindings();
        }
    }
}