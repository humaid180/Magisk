package com.topjohnwu.magisk.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.topjohnwu.magisk.Data;
import com.topjohnwu.magisk.SuRequestActivity;
import com.topjohnwu.magisk.services.OnBootService;
import com.topjohnwu.magisk.utils.SuConnector;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (TextUtils.equals(intent.getAction(), Intent.ACTION_BOOT_COMPLETED)) {
            switch (intent.getExtras().getString("action", "boot")) {
                case "request":
                    Intent i = new Intent(context, Data.classMap.get(SuRequestActivity.class))
                            .putExtra("socket", intent.getStringExtra("socket"))
                            .putExtra("version", 2)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                    break;
                case "log":
                    SuConnector.handleLogs(intent, 2);
                    break;
                case "boot":
                    OnBootService.enqueueWork(context);
                    break;
            }
        }
    }
}
