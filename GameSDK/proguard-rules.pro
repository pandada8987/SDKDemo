# 设置混淆的压缩比率 0 ~ 7
-optimizationpasses 5
# 混淆时不使用大小写混合，混淆后的类名为小写
-dontusemixedcaseclassnames
# 指定不去忽略非公共库的类
-dontskipnonpubliclibraryclasses
# 指定不去忽略非公共库的成员
-dontskipnonpubliclibraryclassmembers
# 混淆时不做预校验
-dontpreverify
# 混淆时不记录日志
-verbose
# 忽略警告
-ignorewarnings
# 代码优化
-dontshrink
# 不优化输入的类文件
-dontoptimize
#这句话很重要
-renamesourcefileattribute
-keepattributes SourceFile, LineNumberTable,Signature,Exceptions,InnerClasses,EnclosingMethod

# 混淆采用的算法
-optimizations !code/simplification/cast,!field/*,!class/merging/*

# seeds.txt文件列出未混淆的类和成员
-printseeds seeds.txt
# usage.txt文件列出从apk中删除的代码
-printusage unused.txt
# mapping.txt文件列出混淆前后的映射
-printmapping mapping.txt

-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.preference.Preference
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.annotation.**
-keep public class * extends android.support.v7.**
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep class android.support.** {*;}

-dontwarn android.support.v7.**
-keep class android.support.v7.internal.** { *; }
-keep interface android.support.v7.internal.** { *; }
-keep class android.support.v7.** { *; }

-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.**{*; }
-keep class com.tencent.mm.**{*; }
-keep class com.qq.gdt.action.**{*;}
-keep class com.tencent.turingfd.sdk.ams.ad.**{*;}

-keep class com.huotun.sdk.entity.**{*;}
-keep class com.huotun.sdk.plugin.**{*;}
-keep class com.huotun.sdk.config.**{*;}
-keep class com.huotun.sdk.report.**{public <methods>;}
-keep class com.huotun.sdk.open.**{public <methods>;}
-keep class com.huotun.sdk.util.**{public <methods>;}

-keep class com.hjq.permissions.** {*;}

-dontwarn com.cmic.sso.sdk.**
-dontwarn com.sdk.**
-keep class com.cmic.sso.sdk.**{*;}
-keep class com.sdk.** { *;}
-keep class cn.com.chinatelecom.account.api.**{*;}


-keep class com.qihoo360.replugin.RePlugin {public protected *;}
-keep public class android.support.v4.content.LocalBroadcastManager {public *;}
-keep class com.qihoo360.replugin.model.PluginInfo {public protected *;}
-keep class com.qihoo360.replugin.IBinderGetter {public protected *;}
-keep class com.qihoo360.replugin.component.ComponentList {public protected *;}
-keep class com.qihoo360.framework.** {public protected *;}
-keep class com.qihoo360.i.** {public protected *;}
-keep class com.qihoo360.plugins.** {public protected *;}
-keep class com.qihoo360.plugin.** {public protected *;}
-keep class com.qihoo360.replugin.component.dummy.** {public protected *;}
-keep class com.qihoo360.replugin.component.provider.PluginProviderClient {public protected *;}
-keep class com.qihoo360.replugin.component.provider.PluginProviderClient2 {public protected *;}
-keep class com.qihoo360.replugin.component.service.PluginServiceClient {public protected *;}
-keep class com.qihoo360.replugin.component.provider.PluginPitProviderP0 { public protected *; }
-keep class com.qihoo360.replugin.component.provider.PluginPitProviderP1 { public protected *; }
-keep class com.qihoo360.replugin.component.provider.PluginPitProviderP2 { public protected *; }
-keep class com.qihoo360.replugin.component.process.ProcessPitProviderP0 { public protected *; }
-keep class com.qihoo360.replugin.component.process.ProcessPitProviderP1 { public protected *; }
-keep class com.qihoo360.replugin.component.process.ProcessPitProviderP2 { public protected *; }
-keep public class com.qihoo360.mobilesafe.api.Pref { public *;}
-keep public class com.qihoo360.mobilesafe.api.IPC {public *;}
-keep public class com.qihoo360.mobilesafe.svcmanager.QihooServiceManager {public *;}
-keep class com.qihoo360.loader2.mgr.PluginProviderClient {public protected *;}
-keep class com.qihoo360.loader2.mgr.PluginServiceClient {public protected *;}
-keep class com.qihoo360.replugin.component.activity.ActivityInjector { *;}


-dontwarn com.tencent.smtt.sdk.WebView
-dontwarn com.tencent.smtt.sdk.WebChromeClient

-dontwarn androidx.annotation.Nullable
-dontwarn androidx.annotation.NonNull
-dontwarn com.google.android.gms.ads.identifier.AdvertisingIdClient
-dontwarn com.google.android.gms.ads.identifier.AdvertisingIdClient$Info
-dontwarn androidx.appcompat.app.AlertDialog
-dontwarn androidx.appcompat.view.menu.ListMenuItemView
-dontwarn androidx.recyclerview.widget.RecyclerView
-dontwarn androidx.swiperefreshlayout.widget.SwipeRefreshLayout
-dontwarn androidx.viewpager.widget.ViewPager
-dontwarn androidx.recyclerview.widget.RecyclerView
-dontwarn androidx.annotation.RequiresApi
-dontwarn androidx.fragment.app.FragmentActivity
-dontwarn androidx.fragment.app.Fragment
-dontwarn androidx.annotation.AnyThread
-dontwarn androidx.annotation.WorkerThread

-keep class com.bytedance.applog.picker.DomSender { public *; }
-keep class com.bytedance.dr.VivoIdentifier {*;}
-keep class com.bytedance.dr.VivoIdentifier$* {*;}

-dontwarn com.baidu.mobads.action.**
-keep class com.baidu.mobads.action.** {*;}