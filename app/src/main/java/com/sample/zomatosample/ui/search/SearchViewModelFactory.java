package com.sample.zomatosample.ui.search;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.sample.zomatosample.data.model.api.FailureResponse;

public class SearchViewModelFactory implements ViewModelProvider.Factory {
    private Observer<Throwable> errorObserver;
    private Observer<FailureResponse> failureResponseObserver;
    private Observer<Boolean> loadingStateObserver;

    public SearchViewModelFactory(Observer<Throwable> errorObserver, Observer<FailureResponse>
            failureResponseObserver, Observer<Boolean> loadingStateObserver) {
        this.errorObserver = errorObserver;
        this.failureResponseObserver = failureResponseObserver;
        this.loadingStateObserver = loadingStateObserver;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new SearchViewModel(errorObserver, failureResponseObserver, loadingStateObserver);
    }
}
