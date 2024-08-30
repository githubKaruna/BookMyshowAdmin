package com.neatroots.bookymyshowadmin.utils

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import com.neatroots.bookymyshowadmin.R

public class Utils {


    companion object {
        fun showMessage(context: Context, errorMessage: String) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()

        }
    }
}