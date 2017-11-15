package com.gmail.it.kruglov.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by kaa-i on 14.11.2017.
 */

public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
