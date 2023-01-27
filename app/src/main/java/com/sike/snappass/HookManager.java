package com.sike.snappass;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import android.content.Context;

public class HookManager implements IXposedHookLoadPackage{

    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals("com.snapchat.android"))
            return;

        findAndHookMethod("android.app.Application", lpparam.classLoader, "attach",Context.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                try {
                    Context snapContext = (Context) param.args[0];
                    if (versionData.verifyVersion(snapContext)) {
                        XposedBridge.log("Snapchat Version OK");
                    }
                } catch (Exception e) {
                    XposedBridge.log("ERROR: "+ e.getMessage());
                }

            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                if (!versionData.canHook) {
                    XposedBridge.log("ERROR incompatible Snapchat found.");
                    return;
                }

                Context snapContext = (Context) param.args[0];
                XposedBridge.log("Hooked into Snapchat application.");

                String className = (String) versionData.versions.get(versionData.snapchatVersion).get("className");
                String methodName = (String) versionData.versions.get(versionData.snapchatVersion).get("methodName");
                String typesAndCallback = (String) versionData.versions.get(versionData.snapchatVersion).get("parameterTypesAndCallback");

                findAndHookMethod(className, lpparam.classLoader, methodName, typesAndCallback, new XC_MethodReplacement() {
                    @Override
                    protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                        return null;
                    }

                });


            }
        });

    }
}
