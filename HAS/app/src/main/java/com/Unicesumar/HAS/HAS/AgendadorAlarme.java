package com.Unicesumar.HAS.HAS;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.net.Uri;
import android.os.Build;


public class AgendadorAlarme {

    /**
     * Agende um alarme de lembrete na hora especificada para a tarefa dada.
     *
     * @param context Aplicação local ou contexto de atividade

     * @param reminderTask Uri referenciando a tarefa no provedor de conteúdo
     */

    public void setAlarme(Context context, long alarmTime, Uri reminderTask) {
        AlarmManager manager = GerenciadorDeAlarme.getAlarmManager(context);

        PendingIntent operation =
                HAS_AlarmService.getLembretePendingIntent(context, reminderTask);


        if (Build.VERSION.SDK_INT >= 23) {

            manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, alarmTime, operation);

        } else if (Build.VERSION.SDK_INT >= 19) {

            manager.setExact(AlarmManager.RTC_WAKEUP, alarmTime, operation);

        } else {

            manager.set(AlarmManager.RTC_WAKEUP, alarmTime, operation);

        }
    }

    public void setRepetirAlarme(Context context, long alarmTime, Uri reminderTask, long RepeatTime) {
        AlarmManager manager = GerenciadorDeAlarme.getAlarmManager(context);

        PendingIntent operation =
                HAS_AlarmService.getLembretePendingIntent(context, reminderTask);

        manager.setRepeating(AlarmManager.RTC_WAKEUP, alarmTime, RepeatTime, operation);


    }

    public void cancelarAlarme(Context context, Uri reminderTask) {
        AlarmManager manager = GerenciadorDeAlarme.getAlarmManager(context);

        PendingIntent operation =
                HAS_AlarmService.getLembretePendingIntent(context, reminderTask);

        manager.cancel(operation);

    }

}