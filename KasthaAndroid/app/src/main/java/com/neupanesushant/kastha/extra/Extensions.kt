package com.neupanesushant.kastha.extra

import android.content.Context
import android.widget.Toast


fun Context.show(message : String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
