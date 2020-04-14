package com.sample.zomatosample.ui.search;

import android.os.Bundle;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.sample.zomatosample.BaseActivity;
import com.sample.zomatosample.R;

import com.sample.zomatosample.data.model.api.restuarantsresponse.Restaurant_;
import com.sample.zomatosample.databinding.ActivitySearchBinding;


public class SearchActivity extends BaseActivity {
    private SearchViewModel mViewModel;
    private ActivitySearchBinding mBinding;
    private RestuarantsAdapter mRestuarantsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        mBinding.setLifecycleOwner(this);

        SearchViewModelFactory searchViewModelFactory = new SearchViewModelFactory(getErrorObserver(),
                getFailureResponseObserver(), getLoadingStateObserver());
        mViewModel = ViewModelProviders.of(this, searchViewModelFactory).get(SearchViewModel.class);
        mBinding.setViewModel(mViewModel);

        mViewModel.getRestuaranrs().observe(this, listResponse -> {
            mViewModel.setSearchingRestuarants(false);
            mRestuarantsAdapter.submitList(listResponse);
        });
        mRestuarantsAdapter = new RestuarantsAdapter((restaurant) -> {
            showToastLong(restaurant.getName());
        });
        mBinding.rvRestuarant.setAdapter(mRestuarantsAdapter);
    }


    public interface ListItemClickListener {
        void onClickListItem(Restaurant_ restaurant);
    }
}
