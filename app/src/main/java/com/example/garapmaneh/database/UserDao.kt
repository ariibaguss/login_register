package com.example.garapmaneh.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note: User)

    @Update
    fun update(note: User)

    @Delete
    fun delete(note: User)

    @Query("SELECT * FROM User WHERE email = :email")
    fun getUserByEmail(email: String): LiveData<User?>
    @Query("SELECT * FROM User WHERE username = :username")
    fun getUserByUsername(username: String): LiveData<User>
}