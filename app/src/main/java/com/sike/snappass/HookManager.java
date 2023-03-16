package com.sike.snappass;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import android.content.Context;
import android.widget.Toast;

public class HookManager implements IXposedHookLoadPackage{
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals("com.snapchat.android"))
            return;

        findAndHookMethod("android.app.Application", lpparam.classLoader, "attach",Context.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                Context snapContext = (Context) param.args[0];

                try{
                    //todo: implement storing values instead of searching before each hook
                    XposedBridge.hookMethod(versionData.getMethod(lpparam), XC_MethodReplacement.returnConstant(null));
                    Toast.makeText(snapContext, "Hook loaded!", Toast.LENGTH_SHORT).show();
                }
                catch(Exception e){
                    Toast.makeText(snapContext, "ERROR Incompatible Snapchat version!", Toast.LENGTH_LONG).show();
                    XposedBridge.log(e);
                }

            }
        });

    }
}
