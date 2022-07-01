package com.quangdau.greenhouse.Language;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public class Language {
    private Context ct;
    private SharedPreferences sharedPreferences;

    public Language(Context ctx){
        ct =ctx;
        sharedPreferences = ct.getSharedPreferences("LANG",Context.MODE_PRIVATE);
    }
    public void updateLanguage(String code){
        Locale locale = new Locale(code);
        Locale.setDefault(locale);
        Resources resources = ct.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration,resources.getDisplayMetrics());
        setLang(code);


    }
    public String getLang(){

        return sharedPreferences.getString("lang","vi");
    }
    public void setLang(String code){

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("lang",code);
        editor.commit();
    }
}
