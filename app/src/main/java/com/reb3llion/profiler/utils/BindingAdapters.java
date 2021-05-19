package com.reb3llion.profiler.utils;

import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;
import androidx.lifecycle.ViewModelProvider;


import com.google.android.material.button.MaterialButton;
import com.google.android.material.slider.Slider;
import com.reb3llion.profiler.R;
import com.reb3llion.profiler.models.AddEditProfileFragmentModel;
import com.reb3llion.profiler.room.entities.Profile;

import java.util.ArrayList;
import java.util.List;


public class BindingAdapters {

    public static final String TAG = "Binding Adapters";
    @InverseBindingAdapter(attribute = "android:value", event = "android:valueAttrChanged")
    public static float getValue(Slider slider){
        return slider.getValue();
    }

    @BindingAdapter("android:valueAttrChanged")
    public static void setSliderValueChangeListener(Slider slider, final InverseBindingListener listener){
        slider.addOnChangeListener((slider1, value, fromUser) -> listener.onChange());
    }

    @BindingAdapter("checked")
    public static void setChecked(MaterialButton button, boolean value){
        if(button.isChecked() != value)
            button.setChecked(value);
    }
    @BindingAdapter("checkedAttrChanged")
    public static void setCheckedListener(MaterialButton view,InverseBindingListener listener){
        view.addOnCheckedChangeListener((button, isChecked) -> listener.onChange());
    }

    @InverseBindingAdapter(attribute = "checked", event = "checkedAttrChanged")
    public static boolean getChecked(MaterialButton button){
        return button.isChecked();
    }
}
