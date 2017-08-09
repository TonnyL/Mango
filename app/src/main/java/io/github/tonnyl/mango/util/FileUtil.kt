package io.github.tonnyl.mango.util

import java.io.File

/**
 * Created by lizhaotailang on 2017/8/5.
 *
 * A collection of some usable functions.
 */

class FileUtil {

    companion object {

        // To get the size of glide cache files.
        @JvmStatic
        fun dirSize(dir: File?): Long {
            dir?.let {
                var result: Long = 0L
                if (dir.exists()) {
                    val fileArray = dir.listFiles()
                    for (file in fileArray) {
                        if (file.isDirectory) {
                            result += dirSize(file)
                        } else {
                            result += file.length()
                        }
                    }
                    return result / 1024 / 1024
                }
                return 0
            } ?: run {
                return 0L
            }
        }
    }

}