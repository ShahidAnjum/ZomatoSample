package com.sample.zomatosample.ui.search;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.sample.zomatosample.R;
import com.sample.zomatosample.data.model.api.restuarantsresponse.GroupedRestuarants;
import com.sample.zomatosample.databinding.RowRestuarantHeaderBinding;
import com.sample.zomatosample.ui.search.SearchActivity.ListItemClickListener;

public class RestuarantsAdapter extends ListAdapter<GroupedRestuarants, RestuarantsAdapter.RestuarantHolder> {

    private static DiffUtil.ItemCallback<GroupedRestuarants> DIFF_CALLBACK = new DiffUtil.ItemCallback<GroupedRestuarants>() {

        @Override
        public boolean areItemsTheSame(@NonNull GroupedRestuarants oldItem, @NonNull GroupedRestuarants newItem) {
            return oldItem.getCuisineType().equals(newItem.getCuisineType());
        }

        @Override
        public boolean areContentsTheSame(@NonNull GroupedRestuarants oldItem, @NonNull GroupedRestuarants newItem) {
            return oldItem.getCuisineType().equals(newItem.getCuisineType());
        }
    };
    final ListItemClickListener listItemClickListener;

    public RestuarantsAdapter(ListItemClickListener listItemClickListener) {
        super(DIFF_CALLBACK);
        this.listItemClickListener = listItemClickListener;
    }

    @NonNull
    @Override
    public RestuarantHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowRestuarantHeaderBinding binding = DataBindingUtil.inflate(LayoutInflater
                .from(parent.getContext()), R.layout.row_restuarant_header, parent, false);
        return new RestuarantHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RestuarantHolder holder, int position) {
        GroupedRestuarants restaurant = getItem(position);
        if (restaurant != null) {
            holder.bindTo(restaurant, listItemClickListener);
        }
    }

    static class RestuarantHolder extends RecyclerView.ViewHolder {
        RowRestuarantHeaderBinding mBinding;

        RestuarantHolder(RowRestuarantHeaderBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        void bindTo(GroupedRestuarants restaurant, ListItemClickListener clickListener) {
            mBinding.setGroupedRestuarant(restaurant);
            mBinding.executePendingBindings();
            NestedRestuarantAdapter nestedRestuarantAdapter = new NestedRestuarantAdapter(clickListener);
            mBinding.rvChild.setAdapter(nestedRestuarantAdapter);
            nestedRestuarantAdapter.submitList(restaurant.getRestaurants());
        }
    }
}