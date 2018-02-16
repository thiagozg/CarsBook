package br.com.carsbook.activity.dialogs

import android.app.Dialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.widget.TextView
import br.com.carsbook.R

/**
 * Created by thiagozg on 09/12/2017.
 */
@Suppress("DEPRECATION")
class AboutDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val versionName = getAppVersionName()
        val htmlBody = Html.fromHtml(getString(R.string.about_dialog_text, versionName))

        val view = LayoutInflater.from(activity)
                                 .inflate(R.layout.dialog_about, null) as TextView
        view.text = htmlBody
        view.movementMethod = LinkMovementMethod()

        return AlertDialog.Builder(activity)
                            .setTitle(R.string.about_dialog_title)
                            .setView(view)
                            .setPositiveButton(R.string.ok) { dialog, _ -> dialog.dismiss() }
                            .create()
    }

    fun getAppVersionName(): String {
        val packageManager = activity.packageManager
        val packageName = activity.packageName
        try {
            val info = packageManager.getPackageInfo(packageName, 0)
            return info.versionName
        } catch (ex: PackageManager.NameNotFoundException) {
            return "N/A"
        }
    }

    companion object {
        private val TAG_DIALOG = "about_dialog"

        fun showAbout(fragmentManager: android.support.v4.app.FragmentManager) {
            val fragmentTransaction = fragmentManager.beginTransaction()
            val previousDialog = fragmentManager.findFragmentByTag(TAG_DIALOG)
            if (previousDialog != null) {
                fragmentTransaction.remove(previousDialog)
            }
            fragmentTransaction.addToBackStack(null)
            AboutDialog().show(fragmentTransaction, TAG_DIALOG)
        }
    }

}