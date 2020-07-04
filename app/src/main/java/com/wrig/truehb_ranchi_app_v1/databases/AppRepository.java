package com.wrig.truehb_ranchi_app_v1.databases;

import android.content.Context;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppRepository {
    AppDatabase mAppDatabase;

    private static AppRepository ourInstance;

    private Executor mExecutor = Executors.newSingleThreadExecutor();

    public static AppRepository getInstance(Context context) {

        return ourInstance = new AppRepository(context);
    }

    private AppRepository(Context context) {
        mAppDatabase = AppDatabase.getInstance(context);
    }
}
