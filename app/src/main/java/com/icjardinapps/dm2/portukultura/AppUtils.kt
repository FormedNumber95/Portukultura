package com.icjardinapps.dm2.portukultura

import android.app.ActivityManager
import android.app.ActivityManager.RunningAppProcessInfo
import android.content.Context

/**
 * Clase con un metodo estatico de ayuda
 *
 * @author Aketza
 * @version 1.0
 */
object AppUtils {
    /**
     * Metodo que controla si la aplicacion esta en primer plano o no
     *
     * @author Aketza
     */
    fun isAppInForeground(context: Context): Boolean {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        if (activityManager != null) {
            val runningProcesses = activityManager.runningAppProcesses
            for (processInfo in runningProcesses) {
                if (processInfo.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    // Verifica si el proceso en primer plano es el tuyo
                    if (processInfo.processName == context.packageName) {
                        return true // La aplicación está en primer plano
                    }
                }
            }
        }
        return false // La aplicación está en segundo plano
    }
}
