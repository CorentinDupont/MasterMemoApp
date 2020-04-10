package com.example.mastermemoapp.Database.Schemas;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "memos")
public class MemoDTO {
    @PrimaryKey(autoGenerate = true)
    public long memoId = 0;

    public String text;

    public MemoDTO() {}

    public MemoDTO(String text) {
        this.text = text;
    }
}
