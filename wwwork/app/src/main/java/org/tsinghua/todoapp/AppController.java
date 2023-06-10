package org.tsinghua.todoapp;

import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;

public class AppController extends Application {
    private static AppController instance;
    private List<Activity> activityList = new LinkedList<>();

    public static AppController getInstance() {
        if (instance == null) {
            instance = new AppController();
        }
        return instance;
    }

    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public void removeActivity(Activity activity) {
        activityList.remove(activity);
    }
    public void finishAllActivities() {
        for (Activity activity : activityList) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        activityList.clear();
    }

}
