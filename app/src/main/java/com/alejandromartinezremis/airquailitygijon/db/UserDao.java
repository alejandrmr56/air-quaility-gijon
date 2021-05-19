package com.alejandromartinezremis.airquailitygijon.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT password FROM user WHERE username = :username")
    String getPassword(String username);

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);

    @Query("DELETE FROM user WHERE username = :username")
    void deleteUser(String username);

    @Query("DELETE FROM user")
    void deleteAllUsers();
}
