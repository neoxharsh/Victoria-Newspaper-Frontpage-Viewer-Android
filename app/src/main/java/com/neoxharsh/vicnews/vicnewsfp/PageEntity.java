package com.neoxharsh.vicnews.vicnewsfp;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity( indices = {@Index("Date")})
public class PageEntity {
    @PrimaryKey(autoGenerate = true)
    public int index;

    String Date;
    String ImageURL;
    String PageURL;

    PageEntity(){}
    PageEntity(String Date, String Imageurl, String pageURL){
        this.Date = Date;
        this.ImageURL = Imageurl;
        this.PageURL = pageURL;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setPageURL(String pageURL) {
        PageURL = pageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }
}
