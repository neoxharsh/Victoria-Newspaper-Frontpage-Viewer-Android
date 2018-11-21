package com.neoxharsh.vicnews.vicnewsfp;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface VicDbDaoAccess {
    @Insert
    void insertOnlySingleNews(PageEntity entity);

    @Insert
    void insertAll(List<PageEntity> entities);

    @Query("SELECT * FROM PageEntity")
    PageEntity[] getDBItems();

    @Query("SELECT * FROM PageEntity WHERE DATE = :date")
    PageEntity[] getItemWithDate(String date);
}
