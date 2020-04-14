package com.sample.zomatosample.ui.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.sample.zomatosample.data.model.api.FailureResponse;
import com.sample.zomatosample.data.model.api.restuarantsresponse.GroupedRestuarants;
import com.sample.zomatosample.data.model.api.restuarantsresponse.RestuarantListResponse;
import com.sample.zomatosample.ui.base.RichMediatorLiveData;

import java.util.ArrayList;

public class SearchViewModel extends ViewModel {

    private RichMediatorLiveData<ArrayList<GroupedRestuarants>> restuarantsLiveData;
    private Observer<Throwable> mErrorObserver;
    private Observer<FailureResponse> mFailureObserver;

    private SearchRepo mSearchRepo = new SearchRepo();
    private MutableLiveData<Boolean> _isSearchingRestuarants = new MutableLiveData<>();
    private MutableLiveData<String> _userAddress = new MutableLiveData<>();
    private MutableLiveData<String>  _searchedText = new MutableLiveData<>();

    public LiveData<Boolean> isSearchingRestuarants = _isSearchingRestuarants;
    public LiveData<String> userAddress = _userAddress;
    public LiveData<String> searchedText = _searchedText;

    public SearchViewModel(Observer<Throwable> errorObserver, Observer<FailureResponse>
            failureResponseObserver, Observer<Boolean> loadingStateObserver) {
        mErrorObserver = errorObserver;
        mFailureObserver = failureResponseObserver;

        if (restuarantsLiveData == null) {
            restuarantsLiveData = new RichMediatorLiveData<ArrayList<GroupedRestuarants>>() {
                @Override
                protected Observer<FailureResponse> getFailureObserver() {
                    return mFailureObserver;
                }

                @Override
                protected Observer<Throwable> getErrorObserver() {
                    return mErrorObserver;
                }
            };
        }
        mSearchRepo.updateUserAddress(_userAddress);
    }


    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String searchedText = s.toString();
        _searchedText.setValue(searchedText);
        if (searchedText.length() > 2) {
            hitSearchRestuarantApi(searchedText);
        }
    }

    public void onClickClear() {
        _searchedText.setValue("");
    }

    public void hitSearchRestuarantApi(String searchedText) {
        mSearchRepo.searchRestaurants(restuarantsLiveData, searchedText);
        _isSearchingRestuarants.setValue(true);
    }

    public RichMediatorLiveData<ArrayList<GroupedRestuarants>> getRestuaranrs() {
        return restuarantsLiveData;
    }

    public void setSearchingRestuarants(boolean searchingRestuarants) {
        _isSearchingRestuarants.setValue(searchingRestuarants);
    }
}
