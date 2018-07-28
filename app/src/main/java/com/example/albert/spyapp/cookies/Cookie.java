package com.example.albert.spyapp.cookies;

import android.util.Log;

import java.io.Serializable;
import java.util.HashMap;

public final class Cookie implements Serializable {
    private String cookieValue;

    private String expires = null;

    private String path = null;

    private String domain = null;


    public String getExpires() {
        return expires;
    }

    public String getPath() {
        return path;
    }

    public String getDomain() {
        return domain;
    }

    public boolean isSecure() {
        return secure;
    }

    public HashMap<String, String> getKeyValue() {
        return keyValue;
    }

    HashMap<String,String> keyValue;
    private boolean secure = false;

   public Cookie(String header){

        String[] fields = header.split(";\\s*");

        keyValue=new HashMap<>();

        for(int i=0;i<fields.length;i++){


            cookieValue = fields[0];
            String[] valuesplit=cookieValue.split("=");
            keyValue.put(valuesplit[0],valuesplit[1]);
            Log.d("putting key value",valuesplit[0]+" "+valuesplit[1]);

            if ("secure".equalsIgnoreCase(fields[i])) {
                secure = true;
            }
            if (fields[i].indexOf('=') > 0) {
                String[] f = fields[i].split("=");
                if ("expires".equalsIgnoreCase(f[0])) {
                    expires = f[1];
                }
                if ("domain".equalsIgnoreCase(f[0])) {
                    domain = f[1];
                }
                if ("path".equalsIgnoreCase(f[0])) {
                    path = f[1];
                }
            }

        }
    }
}
