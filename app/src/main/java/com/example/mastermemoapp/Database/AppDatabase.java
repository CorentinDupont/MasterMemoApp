package com.example.mastermemoapp.Database;

import com.example.mastermemoapp.Database.DAO.MemosDAO;
import com.example.mastermemoapp.Database.Schemas.MemoDTO;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {MemoDTO.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract MemosDAO memosDAO();
}
