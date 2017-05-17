package com.xhinliang.dnote.app;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

/**
 * Created by XhinLiang on 2017/5/13.
 * xhinliang@gmail.com
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AVOSCloud.initialize(this, "n2yYcM45cWQpRjDFd4eaBzhU-gzGzoHsz", "rJOtJuDf0dHRXxdGxKw5Fnh9");
    }
}
