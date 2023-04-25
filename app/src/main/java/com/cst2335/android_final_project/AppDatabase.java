package com.cst2335.android_final_project;


import android.content.Context;

    import androidx.room.Database;
    import androidx.room.Room;
    import androidx.room.RoomDatabase;
    import androidx.room.migration.Migration;
    import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = {FavouriteDrink.class}, version = 2)
    public abstract class AppDatabase extends RoomDatabase {
        public abstract DrinkDAO favoriteDrinkDao();

        private static volatile AppDatabase INSTANCE;

        public static AppDatabase getInstance(Context context) {
            if (INSTANCE == null) {
                synchronized (AppDatabase.class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                        AppDatabase.class, "drink_database")
                                .addMigrations(MIGRATION_1_2) // Add this line
                                .build();
                    }
                }
            }
            return INSTANCE;
        }

        static final Migration MIGRATION_1_2 = new Migration(1, 2) {
            @Override
            public void migrate(SupportSQLiteDatabase database) {
                database.execSQL("DROP TABLE IF EXISTS favorite_drinks");
                database.execSQL("CREATE TABLE favorite_drinks (drinkId TEXT NOT NULL PRIMARY KEY, drinkName TEXT, drinkImageUrl TEXT)");
            }
        };

    }



