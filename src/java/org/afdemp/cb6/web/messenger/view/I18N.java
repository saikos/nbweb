package org.afdemp.cb6.web.messenger.view;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class I18N {
    
    private static final List<String> LANGS = Arrays.asList("el", "en");
    
    private final Map texts;
    
    public I18N(Map texts) {
        this.texts = texts;
    }
    
    public String translate(String key, String lang) {
        if (key == null) {
            return "";
        }
        
        try {
            Map m = (Map) texts.get(key);            
            String s = (String) m.get(lang);
            return (s == null ? key : s);
        }
        catch(NullPointerException | ClassCastException e) {
            //log the exception
            return key;
        }        
    }
    
    public static boolean isSupported(String lang) {
        return LANGS.contains(lang);
    }
}
