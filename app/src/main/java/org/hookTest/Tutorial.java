package org.hookTest;

import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XposedBridge;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class Tutorial implements IXposedHookLoadPackage {
    private static final String TAGETPKG="com.a.multicheck";
    private static final String TAG="HOOKTEST";


    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {


        if (!lpparam.packageName.equals(TAGETPKG))
            return;
        XposedBridge.log("Loaded app:" + lpparam.packageName);

        findAndHookMethod("com.a.Check",
                lpparam.classLoader,
                "check",
                String.class,
                new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                // this will be called before the clock was updated by the original method
                String input=(String) param.args[0];
                XposedBridge.log( input);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                // this will be called after the clock was updated by the original method
                param.setResult(true);
            }
        });
    }
}