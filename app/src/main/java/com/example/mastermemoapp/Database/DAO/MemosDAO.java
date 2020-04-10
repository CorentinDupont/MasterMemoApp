package com.example.mastermemoapp.Database.DAO;

import com.example.mastermemoapp.Database.Schemas.MemoDTO;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public abstract class MemosDAO {

    @Query("SELECT * FROM memos")
    public abstract List<MemoDTO> getMemoList();

    @Insert
    public abstract void insert(MemoDTO ...memos);

    @Delete
    public abstract void delete(MemoDTO... memos);
}
