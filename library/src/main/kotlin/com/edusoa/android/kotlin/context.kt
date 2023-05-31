@file: JvmName("-context")

package com.edusoa.android.kotlin

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 * Description:
 * @author Junerver
 * date: 2023/5/12-13:54
 * Email: junerver@gmail.com
 * Version: v1.0
 */
//region context扩展
fun Context.getDrawableRes(@DrawableRes id: Int): Drawable {
    return AppCompatResources.getDrawable(this, id)!!
}

fun Context.getColorRes(@ColorRes id: Int): Int {
    return ContextCompat.getColor(this, id)
}

fun Context.getStringRes(@StringRes id: Int): String {
    return this.resources.getString(id)
}

fun Context.getDimenRes(@DimenRes id: Int): Int {
    return this.resources.getDimensionPixelSize(id)
}

fun Context.inflater(resource: Int): View {
    return LayoutInflater.from(this).inflate(resource, null)
}


fun Context.inflater(resource: Int, root: ViewGroup, attachToRoot: Boolean): View {
    return LayoutInflater.from(this).inflate(resource, root, attachToRoot)
}

/**
 * 获取屏幕宽度
 *
 * @return 屏幕宽度
 */
fun Context.screenWidth(): Int {
    return resources.displayMetrics.widthPixels
}

/**
 * 获取屏幕高度
 *
 * @return 屏幕高度
 */
fun Context.screenHeight(): Int {
    return resources.displayMetrics.heightPixels
}

/**
 * 获取状态栏高度
 *
 * @return 状态栏高度（px）
 */
fun Context.statusBarHeight(): Int {
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")

    var statusBarHeight = 0
    if (resourceId > 0) {
        statusBarHeight = resources.getDimensionPixelSize(resourceId)
    }

    return statusBarHeight
}

/**
 * 获取Version code
 *
 * @return version code
 */
fun Context.versionCode(): Int {
    return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
        packageManager.getPackageInfo(packageName, 0).versionCode
    } else {
        packageManager.getPackageInfo(packageName, 0).longVersionCode.toInt()
    }
}

/**
 * 获取Version name
 *
 * @return version name
 */
fun Context.versionName(): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        packageManager.getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(0)).versionName
    } else {
        packageManager.getPackageInfo(packageName, 0).versionName
    }
}

/**
 * 获取像素密集度参数density
 *
 * @return density
 */
fun Context.density(): Float {
    return resources.displayMetrics.density
}

/**
 * 检查设备是否有虚拟键盘
 */
fun Context.checkDeviceHasNavigationBar(): Boolean {
    var hasNavigationBar = false
    val rs = this.resources
    val id = rs
        .getIdentifier("config_showNavigationBar", "bool", "android")
    if (id > 0) {
        hasNavigationBar = rs.getBoolean(id)
    }
    try {
        val systemPropertiesClass = Class.forName("android.os.SystemProperties")
        val m = systemPropertiesClass.getMethod("get", String::class.java)
        val navBarOverride = m.invoke(systemPropertiesClass, "qemu.hw.mainkeys") as String
        if ("1" == navBarOverride) {
            hasNavigationBar = false
        } else if ("0" == navBarOverride) {
            hasNavigationBar = true
        }
    } catch (e: Exception) {
        Log.e("ContextExtend", "检查虚拟键盘：")
    }
    return hasNavigationBar
}

/**
 * @Description 适配安卓7.0中Uri，需要注意在AndroidManifest文件中注册——provider
 * @Author Junerver
 * Created at 2018/12/22 09:35
 * @param file 需要获取 uri 的 file
 * @return
 */
fun Context.getUriForFile(file: File): Uri {
    return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
        FileProvider.getUriForFile(
            this.applicationContext,
            "${this.packageName}.FileProvider",
            file
        )
    } else {
        Uri.fromFile(file)
    }
}

//fun Context.showDialog(content: String, contentColor: Int = Color.parseColor("#333333"), positiveColor: Int = Color.parseColor("#3F51B5")) {
//    MaterialDialog.Builder(this)
//            .content(content)
//            .contentColor(contentColor)
//            .positiveText("确定")
//            .positiveColor(positiveColor)
//            .show()
//}

//fun Context.showDialog(str: String, r: () -> Unit) {
//    MaterialDialog.Builder(this)
//            .content(str)
//            .contentColorRes(R.color.dialog)
//            .positiveText("确定")
//            .onPositive { _, _ -> kotlin.run(r) }
//            .positiveColorRes(R.color.dialogPrimary)
//            .negativeText("取消")
//            .negativeColorRes(R.color.dialogPrimary)
//            .show()
//}


/**
 * Description: 获取manifest文件中的metadata对象数据
 * @author Junerver
 * @date: 2021/2/8-10:17
 * @Email: junerver@gmail.com
 * @Version: v1.0
 * @param key  meta-data 中的android:name
 * @param def 当没有取到该字段值时的缺省值
 * @return
 */
inline fun <reified T> Context.getMetaData(key: String, def: T): T {
    val applicationInfo =
        this.packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
    val data = applicationInfo.metaData.get(key)
    return data?.let {
        when (T::class) {
            Int::class, String::class, Float::class, Boolean::class -> it
            Long::class -> (data as Float).toLong() //存储时以float类型保存
            else -> throw IllegalArgumentException("META-DATA 类型错误")
        } as T
    } ?: kotlin.run { def }
}

/**
 * Description: 拷贝Assets目录下的文件
 * @author Junerver
 * @date: 2021/11/23-11:32
 * @Email: junerver@gmail.com
 * @Version: v1.0
 * @param assetName assets目录下对应的文件名
 * @param savePath 目标目录
 * @param saveName 目标文件名称
 * @throws IOException
 */
@Throws(IOException::class)
fun Context.copyAssetFile(assetName: String, savePath: String, saveName: String) {
    val dir = File(savePath)
    if (!dir.exists()) {
        dir.mkdirs()
    }
    val dbf = File(savePath + saveName)
    if (dbf.exists()) {
        dbf.delete()
    }
    val outFileName = savePath + saveName
    val myOutput = FileOutputStream(outFileName)
    val myInput = this.assets.open(assetName)
    val buffer = ByteArray(1024)
    var length: Int
    while (myInput.read(buffer).also { length = it } > 0) {
        myOutput.write(buffer, 0, length)
    }
    myOutput.flush()
    myInput.close()
    myOutput.close()
}
//endregion