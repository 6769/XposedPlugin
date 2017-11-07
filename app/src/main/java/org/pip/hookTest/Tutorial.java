package org.pip.hookTest;

import android.graphics.Color;
import android.widget.TextView;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XposedBridge;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class Tutorial implements IXposedHookLoadPackage {
    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {

        XposedBridge.log("Loaded app:" + lpparam.packageName);
        if (!lpparam.packageName.equals("com.android.systemui"))
            return;


        findAndHookMethod("com.android.systemui.statusbar.policy.Clock",
                lpparam.classLoader,
                "updateClock",
                new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                // this will be called before the clock was updated by the original method
            }
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                // this will be called after the clock was updated by the original method
                TextView tv = (TextView) param.thisObject;
                String text = tv.getText().toString();
                tv.setText(text);
                tv.setTextColor(Color.GREEN);
            }
        });
    }
}