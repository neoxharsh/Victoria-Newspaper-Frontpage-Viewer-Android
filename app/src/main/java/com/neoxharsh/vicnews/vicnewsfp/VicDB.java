package com.neoxharsh.vicnews.vicnewsfp;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {PageEntity.class},version = 1,exportSchema = false)
public abstract class VicDB extends RoomDatabase {
    public abstract VicDbDaoAccess daoAccess();
}
