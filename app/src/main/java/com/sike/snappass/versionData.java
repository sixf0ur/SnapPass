package com.sike.snappass;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.widget.Toast;
import java.util.HashMap;

public class versionData {

    public static String snapchatVersion;
    public static Boolean canHook = false;

    static public HashMap<String, HashMap> versions = new HashMap<String, HashMap>() {{
        //add new entries for other snapchat versions
        put("11.61.0.52", new HashMap<String, String>() {{
            put("className", "c19");
            put("methodName", "b");
            put("parameterTypesAndCallback", "b19");
        }});

        put("11.62.1.35", new HashMap<String, String>() {{
            put("className", "A29");
            put("methodName", "b");
            put("parameterTypesAndCallback", "z29");
        }});

        
        put("12.18.0.33", new HashMap<String, String>() {{
            put("className", "OMe");
            put("methodName", "d");
            put("parameterTypesAndCallback", "Vc8");
        }});
    }};

    public static boolean verifyVersion(Context snapContext) {
        try {
            PackageInfo packageInfo = snapContext.getPackageManager().getPackageInfo(snapContext.getPackageName(), 0);
            snapchatVersion = packageInfo.versionName;

            if (versionData.versions.get(snapchatVersion) != null) {
                canHook = true;
                Toast.makeText(snapContext, "Hook loaded!", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(snapContext, "Incompatible Snapchat version!", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (Exception e) {
            Toast.makeText(snapContext, "ERROR Incompatible version, aborting hooks.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}
