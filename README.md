#SDKDemo
SDK項目基于Replugin框架二次修改开发
SDK宿主项目，主要负责加载插件和上报以及提供接口给CP方

1、插件支持分为两种格式，jar和apk，内置插件只能是jar格式
内置插件目录为src/main/assets/plugins

2、项目支持动态下发插件，只需要将插件项目打包成APK上传到后台指定地方即可

3、每次替换内置插件需要同步修改src/main/assets/plugins-builtin.json文件里ver字段的值，
使之与插件项目AndroidManifest.xml文件中的com.qihoo360.plugin.version.ver版本对应

4、打包SDK执行AndroidStudio右侧的Gradle==》GameSDK==》Tasks==》upload==》uploadArchives
