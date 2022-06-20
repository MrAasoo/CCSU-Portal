package com.college.portal;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public class LocalManager {
    private final Context context;

    public LocalManager(Context context) {
        this.context = context;
    }

    public void updateResources(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);

        Resources resources = context.getResources();
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration,
                resources.getDisplayMetrics());
    }
}
