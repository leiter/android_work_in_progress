package com.example.sag17;

import java.util.ArrayList;
import java.util.Locale;

public class LanguageModel {
	
	private  String Country="";
	private  String Image="";
    private  String Language="";
    private  String languageCode="";
    private Locale myLocale;
    
    public LanguageModel (String s) {
    		this.Image = s;
    		this.myLocale = stringToLocale();
    		this.Country = myLocale.getDisplayCountry();
    		this.Language = myLocale.getDisplayLanguage();
    		this.languageCode =	myLocale.getLanguage();
    		this.myLocale = null;
    }

    public LanguageModel (Locale my) {
    	this.Country = my.getDisplayCountry();
    	this.Language = my.getDisplayLanguage();
    	this.Image = my.getLanguage() + "_" + my.getCountry().toLowerCase(my);   	
    	this.languageCode =	my.getLanguage();		
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
  
    public void setImage(String Image) {
        this.Image = Image;
    }

    public String getImage() {
        return this.Image;
    }

}
