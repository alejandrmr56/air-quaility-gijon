package com.alejandromartinezremis.airquailitygijon.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {
    @Query("SELECT password FROM user WHERE username = :username")
    String getPassword(String username);

    @Insert
    void insertAll(User... users);

    @Query("DELETE FROM user WHERE username = :username")
    void deleteUser(String username);

    @Query("DELETE FROM user")
    void deleteAllUsers();
}
