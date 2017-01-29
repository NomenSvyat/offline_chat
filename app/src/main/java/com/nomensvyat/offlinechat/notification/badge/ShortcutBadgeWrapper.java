package com.nomensvyat.offlinechat.notification.badge;

import android.content.Context;

import me.leolin.shortcutbadger.ShortcutBadger;
import timber.log.Timber;

public class ShortcutBadgeWrapper implements BadgeShower {
    private final Context context;

    public ShortcutBadgeWrapper(Context context) {
        this.context = context;
    }

    @Override
    public void show(int count) {
        if (count == 0) {
            remove();
            return;
        }
        Timber.d("Showing badge with %d for %s", count, context.getPackageName());
        ShortcutBadger.applyCount(context, count);
    }

    @Override
    public void remove() {
        ShortcutBadger.removeCount(context);
    }
}
