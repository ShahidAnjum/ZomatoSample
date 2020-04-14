package com.sample.zomatosample;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.sample.zomatosample.data.model.api.FailureResponse;

public abstract class BaseActivity extends AppCompatActivity {

    private Observer<Throwable> errorObserver;
    private Observer<FailureResponse> failureResponseObserver;
    private Observer<Boolean> loadingStateObserver;
  //  private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initObservers();
    }

    private void initObservers() {
        errorObserver = new Observer<Throwable>() {
            @Override
            public void onChanged(@Nullable Throwable throwable) {
                onErrorOccurred(throwable);
            }
        };

        failureResponseObserver = new Observer<FailureResponse>() {
            @Override
            public void onChanged(@Nullable FailureResponse failureResponse) {
                onFailure(failureResponse);
            }
        };

        /**
         * experimental
         * */
        loadingStateObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                onLoadingStateChanged(aBoolean);
            }
        };
    }

    public Observer<Throwable> getErrorObserver() {
        return errorObserver;
    }

    public Observer<FailureResponse> getFailureResponseObserver() {
        return failureResponseObserver;
    }

    public Observer<Boolean> getLoadingStateObserver() {
        return loadingStateObserver;
    }

    protected void onLoadingStateChanged(boolean aBoolean) {

    }

    protected void onFailure(FailureResponse failureResponse) {
        showToastShort("failure:" + failureResponse.getErrorMessage());
        Log.e("onFailure: ", failureResponse.getErrorMessage() + "   " + failureResponse.getErrorCode());
    }

    protected void onErrorOccurred(Throwable throwable) {
        showToastShort("error");
        Log.e("onErrorOccurred: ", throwable.getMessage());
    }


    /**
     * hides keyboard onClick anywhere besides edit text
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int[] scrcoords = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }


    //    public void addFragment(int layoutResId, BaseFragment fragment, String tag) {
//        if (getSupportFragmentManager().findFragmentByTag(tag) == null)
//            getSupportFragmentManager().beginTransaction()
//                    .add(layoutResId, fragment, tag)
//                    .commit();
//    }
//
//    public void addFragmentWithBackstack(int layoutResId, BaseFragment fragment, String tag) {
//        getSupportFragmentManager().beginTransaction()
//                .add(layoutResId, fragment, tag)
//                .addToBackStack(tag)
//                .commit();
//    }
//
//
//    public void replaceFragment(int layoutResId, BaseFragment fragment, String tag) {
//        if (getSupportFragmentManager().findFragmentByTag(tag) == null)
//            getSupportFragmentManager().beginTransaction()
//
//                    .replace(layoutResId, fragment, tag)
//                    .commit();
//    }
//
//    public void replaceFragmentWithBackstack(int layoutResId, BaseFragment fragment, String tag) {
//        getSupportFragmentManager().beginTransaction()
//                .replace(layoutResId, fragment, tag)
//                .addToBackStack(tag)
//                .commit();
//    }
//
//    public void replaceFragmentWithBackstackWithStateLoss(int layoutResId, BaseFragment fragment, String tag) {
//        getSupportFragmentManager().beginTransaction()
//                .replace(layoutResId, fragment, tag)
//                .addToBackStack(tag)
//                .commitAllowingStateLoss();
//    }
//
    public void showToastLong(CharSequence message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void showToastShort(CharSequence message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

//    public void showProgressDialog() {
//        mProgressDialog = new ProgressDialog(this, R.style.MyTheme);
//        mProgressDialog.setIndeterminate(true);
//        mProgressDialog.setCancelable(false);
//        mProgressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
//        mProgressDialog.show();
//    }
//
//    public void hideProgressDialog() {
//        if (mProgressDialog != null && mProgressDialog.isShowing())
//            mProgressDialog.dismiss();
//    }

    public void popFragment() {
        if (getSupportFragmentManager() != null) {
            getSupportFragmentManager().popBackStackImmediate();
        }
    }
}
