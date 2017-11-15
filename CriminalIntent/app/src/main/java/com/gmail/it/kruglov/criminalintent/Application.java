package com.gmail.it.kruglov.criminalintent;

/**
 * Created by kaa-i on 14.11.2017.
 */

public class Application extends android.app.Application {

    private static CrimeLab sCrimeLab;

    @Override
    public void onCreate() {
        super.onCreate();
        sCrimeLab = new CrimeLab(this);
    }

    public static CrimeLab getCrimeLab() {
        return sCrimeLab;
    }
}
