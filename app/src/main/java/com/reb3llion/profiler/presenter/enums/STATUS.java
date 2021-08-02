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

    DND_PERMISSIONS_REQUIRED(R.string.dnd_required),

    TIME_INVALID(R.string.time_invalid),

    DAYS_NOT_SELECTED(R.string.days_not_selected),

    VOLUME_SETTINGS_NOT_SELECTED(R.string.volume_settings_not_selected),

    PROFILE_FETCH_SUCCESSFUL(R.string.profile_fetch_successful),

    PROFILE_FETCH_UNSUCCESSFUL(R.string.profile_fetch_un_successful),

    CUSTOM_MESSAGE(-1);

    private final int id;
    private String custom;

    STATUS(int id) {
        this.id = id;
    }

    public STATUS setCustomMessage(String message) {
        this.custom = message;
        return this;
    }

    public String getMessage(Context context) {
        if (custom == null)
            return context.getString(id);
        else
            return custom;
    }
}
