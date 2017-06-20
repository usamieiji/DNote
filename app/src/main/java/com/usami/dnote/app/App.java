package com.usami.dnote.app;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

/**
 * Created by XhinLiang on 2017/5/13.
 * Modified by UsamiEiji on 2017/6/17
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AVOSCloud.initialize(this, "RtLEkSBG3lzDTpT8rWPt32sI-gzGzoHsz", "agpIe7HwGy3If2ENGa6IW6er");
    }
}
