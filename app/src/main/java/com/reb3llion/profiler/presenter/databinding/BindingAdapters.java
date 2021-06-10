package com.reb3llion.profiler.presenter.databinding;

import android.app.Application;

import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.slider.Slider;


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


    @InverseBindingAdapter(attribute = "android:checked", event = "android:checkedAttrChanged")
    public static boolean getValue(MaterialCheckBox checkBox){
        return checkBox.isChecked();
    }

    @BindingAdapter("android:checkedAttrChanged")
    public static void setCheckboxCheckedChangeListener(MaterialCheckBox checkBox, final InverseBindingListener listener){
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> listener.onChange());
    }

    @BindingAdapter(value = {"extraPadding", "context"}, requireAll = true)
    public static void setExtraPadding(MaterialCheckBox checkBox,float value, Application application){
        final float scale = application.getResources().getDisplayMetrics().density;
        checkBox.setPadding(checkBox.getPaddingLeft() + (int)(value * scale + 0.5f),
                checkBox.getPaddingTop(),
                checkBox.getPaddingRight(),
                checkBox.getPaddingBottom());
    }
}
