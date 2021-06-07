package com.alejandromartinezremis.airquailitygijon.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * The application's database
 */
@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}