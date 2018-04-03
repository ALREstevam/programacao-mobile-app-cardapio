package br.unicamp.ft.a166348_r176575.appcardapio.utils;

import android.content.Context;
import android.support.v7.app.AlertDialog;

/**
 * Created by andre on 02/04/2018.
 */

public class SimpleAlert {

    public void alertOk(String msgName, String msgContent, Context context) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle(msgName);
        alert.setMessage(msgContent);
        alert.setPositiveButton("OK",null);
        alert.show();
    }
}
