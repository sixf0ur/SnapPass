package com.sike.snappass;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Base64;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import io.github.neonorbit.dexplore.DexFactory;
import io.github.neonorbit.dexplore.Dexplore;
import io.github.neonorbit.dexplore.filter.ClassFilter;
import io.github.neonorbit.dexplore.filter.DexFilter;
import io.github.neonorbit.dexplore.filter.MethodFilter;
import io.github.neonorbit.dexplore.filter.ReferenceTypes;
import io.github.neonorbit.dexplore.result.MethodData;

public class versionData
{
    static String aCLASS = "U2NyZWVuc2hvdERldGVjdG9y";
    static String aMETHOD = "V2hhdHNBcHA=";
    public static String decode(String encodedString)
    {
        byte[] decodedBytes = Base64.decode(encodedString, Base64.DEFAULT);
        String decodedString = new String(decodedBytes);
        return decodedString;
    }
    //taken from the example
    public static Method getMethod(XC_LoadPackage.LoadPackageParam lpparam)
    {
        ClassFilter classFilter = new ClassFilter.Builder()
                .setReferenceTypes(ReferenceTypes.builder().addString().build())
                .setReferenceFilter(pool ->
                        pool.contains(decode(aCLASS))
                ).build();

        MethodFilter methodFilter = new MethodFilter.Builder()
                .setReferenceTypes(ReferenceTypes.builder().addString().build())
                .setReferenceFilter(pool ->
                        pool.contains(decode(aMETHOD))
                ).setParamSize(1)
                .setModifiers(Modifier.PUBLIC)
                .build();
        Dexplore dexplore = DexFactory.load(lpparam.appInfo.sourceDir);
        MethodData result = dexplore.findMethod(DexFilter.MATCH_ALL, classFilter, methodFilter);
        Method method = result.loadMethod(lpparam.classLoader);
        return method;
    }

}
