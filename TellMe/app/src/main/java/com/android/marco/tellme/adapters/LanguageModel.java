package com.android.marco.tellme.adapters;

import java.util.Locale;

/**
 * Created by gen on 14.04.15.
 */
public class LanguageModel {

    private String speechLocal;
    private  String Country="";
    private  String Image="";
    private  String Language="";
    private  String languageCode="";

    public LanguageModel(String s) {
//        this.Image = s;
////        this.myLocale = stringToLocale();
//        this.Country = myLocale.getDisplayCountry();
//        this.Language = myLocale.getDisplayLanguage();
//        this.languageCode =	myLocale.getLanguage();
//        this.myLocale = null;
    }
//    private  Locale myLocale;

    public LanguageModel (Locale my) {
        this.Country = my.getDisplayCountry();
        this.Language = my.getDisplayLanguage();
        this.Image = my.getCountry().toLowerCase(my);    // .toLowerCase(); // my.getLanguage() + "_" +
        this.languageCode =	my.getLanguage();
        this.speechLocal = localeToString(my, 0);


    }

    public String getSpeechLocal() {
        return speechLocal;
    }

    public String localeToString(Locale l, int mode) {	//
        if (mode==0) { return l.getLanguage() + "_" + l.getCountry();}
        return l.getLanguage();
    }

    public Locale stringToLocale() {
        String[] p = Image.split("_");
        return new Locale(p[0],p[1]);
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public String getImage() {
        return this.Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }
}
