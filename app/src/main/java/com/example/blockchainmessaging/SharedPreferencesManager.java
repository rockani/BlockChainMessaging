package com.example.blockchainmessaging;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {
    private SharedPreferences msharedPreferences;
    public SharedPreferences.Editor editor;
    private Context mContext;
    private static final String PREFERENCES_DATA ="com.example.blockchain";
    private static final String  ENCRYPTION_STATUS="encryption_status";
    private static final String  DARK_THEME ="dark theme";
    private static final String PROOF_OF_WORK= "proof of work";
    public static final int DEFAULT_PROOF_OF_WORK=2;
    public SharedPreferencesManager(Context context){
        this.mContext = context;
        msharedPreferences = mContext.getSharedPreferences(PREFERENCES_DATA,mContext.MODE_PRIVATE);
        editor=msharedPreferences.edit();
        editor.apply();
    }
    public void setDarkTheme(boolean isDarkActivated){
        editor.putBoolean(DARK_THEME,isDarkActivated);
        editor.commit();
    }
    public void setPowValue(int pow){
        editor.putInt(PROOF_OF_WORK,pow);
        editor.commit();
    }
    public boolean getDarkTheme(){
        return msharedPreferences.getBoolean(DARK_THEME,false);
    }
    public int getPowValue(){
        return msharedPreferences.getInt(PROOF_OF_WORK,2);
    }

}
