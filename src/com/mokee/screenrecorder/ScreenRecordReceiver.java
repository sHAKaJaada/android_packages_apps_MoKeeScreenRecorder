/**
 * Copyright (c) 2014, The Mokee OpenSource Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mokee.screenrecorder;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

import static com.mokee.screenrecorder.ScreenRecorderService.ACTION_NOTIFY_DELETE_SCREENRECORD;
import static com.mokee.screenrecorder.ScreenRecorderService.ACTION_NOTIFY_RECORD_SERVICE;
import static com.mokee.screenrecorder.ScreenRecorderService.NOTIFICATION_ID;
import static com.mokee.screenrecorder.ScreenRecorderService.SCREENRECORD_PATH;

public class ScreenRecordReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION_NOTIFY_RECORD_SERVICE.equals(intent.getAction())) {
            Intent serviceIntent = new Intent(context, ScreenRecorderService.class);
            serviceIntent.setAction(ACTION_NOTIFY_RECORD_SERVICE);
            context.startService(serviceIntent);
        } else if (ACTION_NOTIFY_DELETE_SCREENRECORD.equals(intent.getAction())) {
            File screenRecord = new File(intent.getExtras().getString(SCREENRECORD_PATH));
            if (screenRecord.exists()) screenRecord.delete();
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.fromFile(screenRecord)));
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(NOTIFICATION_ID);
        }
    }
}
