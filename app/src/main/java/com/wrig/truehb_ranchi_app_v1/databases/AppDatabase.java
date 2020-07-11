package com.wrig.truehb_ranchi_app_v1.databases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.wrig.truehb_ranchi_app_v1.interfaces.HealthCenterDao;
import com.wrig.truehb_ranchi_app_v1.interfaces.TestDetailsDao;
import com.wrig.truehb_ranchi_app_v1.models.health_center_data.HeathCenterDataModel;
import com.wrig.truehb_ranchi_app_v1.models.test_details_database_model.TestDetailsDatabaseModel;

@Database(entities = {TestDetailsDatabaseModel.class, HeathCenterDataModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "Truehb_ranchi.db";

    public static volatile AppDatabase instance;

    public static final Object LOCK = new Object();

    public abstract TestDetailsDao TestDetailsDao();

    public  abstract HealthCenterDao HealthCenterDao();

    public static AppDatabase getInstance(Context context) {

        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {

                    instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                            .allowMainThreadQueries()
                            .build();

                }
            }
        }

        return instance;
    }
}
