package com.nomensvyat.offlinechat.utils.rx;

import android.util.SparseArray;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class LifecycleCompositeSubscriptionFabric {
    private LifecycleCompositeSubscriptionFabric() {
    }

    public static LifecycleCompositeSubscription create() {
        return new LifecycleCompositeSubscriptionImpl();
    }

    private static class LifecycleCompositeSubscriptionImpl
            implements LifecycleCompositeSubscription {

        private int state = States.NONE;
        //3 is the unique states count
        private SparseArray<CompositeSubscription> compositeSubscriptions = new SparseArray<>(3);

        private LifecycleCompositeSubscriptionImpl() {
        }

        @Override
        public void onCreate() {
            state = States.CREATED;
        }

        @Override
        public void onDestroy() {
            state = States.DESTROYED;
            unsubscribeComposite();
        }

        private void unsubscribeComposite() {
            CompositeSubscription compositeSubscription = compositeSubscriptions.get(state);
            if (compositeSubscription != null && compositeSubscription.hasSubscriptions()) {
                compositeSubscription.unsubscribe();
                compositeSubscriptions.delete(state);
            }
        }

        @Override
        public void onStart() {
            state = States.STARTED;
        }

        @Override
        public void onStop() {
            state = States.STOPPED;
            unsubscribeComposite();
        }

        @Override
        public void onResume() {
            state = States.RESUMED;
        }

        @Override
        public void onPause() {
            state = States.PAUSED;
            unsubscribeComposite();
        }

        @Override
        public void addSubscription(Subscription subscription) {
            CompositeSubscription compositeSubscription = compositeSubscriptions.get(state);
            if (compositeSubscription == null || compositeSubscription.isUnsubscribed()) {
                compositeSubscription = new CompositeSubscription();
                compositeSubscriptions.put(state, compositeSubscription);
            }
            compositeSubscription.add(subscription);
        }

        private static class States {
            final static int NONE = 0;
            final static int CREATED = 1;
            final static int STARTED = 2;
            final static int RESUMED = 3;
            final static int PAUSED = 3;
            final static int STOPPED = 2;
            final static int DESTROYED = 1;
        }
    }
}
