package com.college.portal;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import com.college.portal.sharedpreferences.LanguagePrefManager;

import java.util.Locale;

public class LocalManager {

    public static Context setLocale(Context context) {
        return setNewLocale(context, new LanguagePrefManager(context).getLanguage());
    }


    // the method is used to set the language at runtime
    public static Context setNewLocale(Context context, String language) {
        persistLanguage(context, language);
        return updateResourcesLegacy(context, language);
    }

    private static void persistLanguage(Context context, String language) {
        new LanguagePrefManager(context).SetLanguage(language);
    }


    public static Context updateResourcesLegacy(Context context, String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);

        Resources resources = context.getResources();
        Configuration configuration = new Configuration(resources.getConfiguration());


        configuration.setLocale(locale);
        configuration.setLayoutDirection(locale);
        context = context.createConfigurationContext(configuration);


        configuration.locale = locale;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());


        return context;

    }
}
