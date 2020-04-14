package com.sample.zomatosample.util;

import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class BindingUtils {

    @BindingAdapter("imageUrl")
    public static void loadImage(AppCompatImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(view);
    }
}
