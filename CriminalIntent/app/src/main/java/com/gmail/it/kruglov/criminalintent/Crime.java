package com.gmail.it.kruglov.criminalintent;

import java.util.Date;
import java.util.UUID;

/**
 * Created by kaa-i on 10.11.2017.
 */

public class Crime {

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    public Crime() {
        mId = UUID.randomUUID();
        mDate = new Date();
    }
}
