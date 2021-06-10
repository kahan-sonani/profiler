package com.reb3llion.profiler.presenter.enums;

import android.content.Context;

import com.reb3llion.profiler.R;

public enum STATUS {

    ADD_PROFILE_SUCCESSFUL(R.string.add_profile_successful),
    ADD_PROFILE_FAIL(R.string.add_profile_fail),

    UPDATE_PROFILE_SUCCESSFUL(R.string.update_profile_successful),
    UPDATE_PROFILE_FAIL(R.string.update_profile_fail),

    DELETE_PROFILE_SUCCESSFUL(R.string.delete_profile_successful),
    DELETE_PROFILE_FAIL(R.string.delete_profile_fail),

    INVALID_CREDENTIALS(R.string.invalid_profile_credentials),

    DND_PERMISSIONS_REQUIRED(R.string.dnd_required);

    private int id;

    STATUS(int id){
        this.id = id;
    }

    public String getMessage(Context context){
       return context.getString(id);
    }
}
