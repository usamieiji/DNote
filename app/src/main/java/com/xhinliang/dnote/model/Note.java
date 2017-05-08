package com.xhinliang.dnote.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Class Note
 *
 * @author XhinLiang
 */
public class Note implements Serializable{

    private static final long serialVersionUID = 2L;

    private String title;
    private String content;
    private Calendar calendar;
    private static final DateFormat dateFormat = SimpleDateFormat.getDateTimeInstance();


    public Note(String title, String content) {
        this.title = title;
        this.content = content;
        calendar = Calendar.getInstance();
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedAt() {
        return dateFormat.format(calendar.getTime());
    }
}
