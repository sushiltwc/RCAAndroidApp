package com.twc.rca.utils;

import com.twc.rca.R;

/**
 * Created by Sushil on 08-02-2018.
 */

public class InitUtils {

    public static final int TUTORIAL_1 = 1, TUTORIAL_2 = 2, TUTORIAL_3 = 3;

    public static final int PAGES[] = new int[]{TUTORIAL_1, TUTORIAL_2, TUTORIAL_3};

    public static int getTutorialIconId(int page) {
        switch (page) {
            case TUTORIAL_1:
                return R.drawable.ic_menu_camera;
            case TUTORIAL_2:
                return R.drawable.ic_menu_camera;
            case TUTORIAL_3:
                return R.drawable.ic_menu_camera;
            default:
                return R.drawable.ic_menu_camera;
        }
    }

    public static int getTitleId(int page) {
        switch (page) {
            case TUTORIAL_1:
                return R.string.permission_continue;
            case TUTORIAL_2:
                return R.string.permission_continue;
            case TUTORIAL_3:
                return R.string.permission_continue;
            default:
                return R.string.permission_continue;
        }
    }

    public static final String TUTORIAL_TYPE = "tutorial";

    // Initial pager walkthrough
    public static final int INITIAL = 0x111;

    public static String getPreferenceKey(int type) {
        String key = "";

        switch (type) {
            case INITIAL:
                key = "initial";
                break;
        }
        return TUTORIAL_TYPE + key;
    }

}
