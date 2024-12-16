package androidx.fragment.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import org.jetbrains.annotations.NotNull;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Collection;

/**
 * Base class for Common Activity that want to use the support-based
 * {@link Fragment Fragments}.
 *
 * Tailored functions based on {@link FragmentActivity}. FragmentActivity includes all functions.
 */
public class FragmentManagerHost implements LifecycleOwner {

    protected Activity mActivity;

    FragmentController mFragments;

    /**
     * A {@link Lifecycle} that is exactly nested outside of when the FragmentController
     * has its state changed, providing the proper nesting of Lifecycle callbacks
     * <p>
     * TODO(b/127528777) Drive Fragment Lifecycle with LifecycleObserver
     */
    final LifecycleRegistry mFragmentLifecycleRegistry = new LifecycleRegistry(this);

    boolean mCreated;
    boolean mResumed;
    boolean mStopped = true;

    public void attach(@NotNull Activity activity) {
        this.mActivity = activity;
        init();
    }

    private void init() {
        mFragments = FragmentController.createController(new HostCallbacks());
        mFragments.attachHost(null /*parent*/);
    }


    /**
     * {@inheritDoc}
     *
     * Perform initialization of all fragments.
     */
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mFragmentLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
        mFragments.dispatchCreate();
    }

    /**
     * {@inheritDoc}
     *
     * Destroy all fragments.
     */
    public void onDestroy() {
        mFragments.dispatchDestroy();
        mFragmentLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
    }

    /**
     * {@inheritDoc}
     *
     * Dispatch onLowMemory() to all fragments.
     */
    public void onLowMemory() {
        mFragments.dispatchLowMemory();
    }

    /**
     * {@inheritDoc}
     *
     * Dispatch onPause() to fragments.
     */
    public void onPause() {
        mResumed = false;
        mFragments.dispatchPause();
        mFragmentLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
    }

    /**
     * {@inheritDoc}
     *
     * Handle onNewIntent() to inform the fragment manager that the
     * state is not saved.  If you are handling new intents and may be
     * making changes to the fragment state, you want to be sure to call
     * through to the super-class here first.  Otherwise, if your state
     * is saved but the activity is not stopped, you could get an
     * onNewIntent() call which happens before onResume() and trying to
     * perform fragment operations at that point will throw IllegalStateException
     * because the fragment manager thinks the state is still saved.
     */
    @CallSuper
    public void onNewIntent(@SuppressLint("UnknownNullness") Intent intent) {
        mFragments.noteStateNotSaved();
    }

    /**
     * {@inheritDoc}
     *
     * Dispatch onResume() to fragments.
     */
    public void onResume() {
        mFragments.noteStateNotSaved();
        mResumed = true;
        mFragments.execPendingActions();

        onResumeFragments();
    }

    private void onResumeFragments() {
        mFragmentLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
        mFragments.dispatchResume();
    }

    /**
     * {@inheritDoc}
     *
     * Dispatch onStart() to all fragments.
     */
    public void onStart() {
        mFragments.noteStateNotSaved();

        mStopped = false;

        if (!mCreated) {
            mCreated = true;
            mFragments.dispatchActivityCreated();
        }

        mFragments.execPendingActions();

        // NOTE: HC onStart goes here.

        mFragmentLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
        mFragments.dispatchStart();
    }

    /**
     * {@inheritDoc}
     *
     * Dispatch onStop() to all fragments.
     */
    public void onStop() {
        mStopped = true;
        markFragmentsCreated();

        mFragments.dispatchStop();
        mFragmentLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
    }

    /**
     * Return the FragmentManager for interacting with fragments associated
     * with this activity.
     */
    public FragmentManager getSupportFragmentManager() {
        return mFragments.getSupportFragmentManager();
    }

    class HostCallbacks extends FragmentHostCallback<Activity> implements
            LifecycleOwner {
        public HostCallbacks() {
            super(mActivity);
        }

        @NonNull
        @Override
        public Lifecycle getLifecycle() {
            // Instead of directly using the Activity's Lifecycle, we
            // use a LifecycleRegistry that is nested exactly outside of
            // when Fragments get their lifecycle changed
            // TODO(b/127528777) Drive Fragment Lifecycle with LifecycleObserver
            return mFragmentLifecycleRegistry;
        }

        @Override
        public void onDump(@NonNull String prefix, @Nullable FileDescriptor fd,
                           @NonNull PrintWriter writer, @Nullable String[] args) {
            // Ignore
        }

        @Override
        public boolean onShouldSaveFragmentState(@NonNull Fragment fragment) {
            return !FragmentManagerHost.this.mActivity.isFinishing();
        }

        @Override
        @NonNull
        public LayoutInflater onGetLayoutInflater() {
            return FragmentManagerHost.this.mActivity.getLayoutInflater().cloneInContext(FragmentManagerHost.this.mActivity);
        }

        @Override
        public Activity onGetHost() {
            return FragmentManagerHost.this.mActivity;
        }

        @Override
        public void onSupportInvalidateOptionsMenu() {
            // Ignore
        }

        @Override
        public boolean onShouldShowRequestPermissionRationale(@NonNull String permission) {
            // Ignore
            return false;
        }

        @Override
        public boolean onHasWindowAnimations() {
            return FragmentManagerHost.this.mActivity.getWindow() != null;
        }

        @Override
        public int onGetWindowAnimations() {
            final Window w = FragmentManagerHost.this.mActivity.getWindow();
            return (w == null) ? 0 : w.getAttributes().windowAnimations;
        }

        @Nullable
        @Override
        public View onFindViewById(int id) {
            return FragmentManagerHost.this.mActivity.findViewById(id);
        }

        @Override
        public boolean onHasView() {
            final Window w = FragmentManagerHost.this.mActivity.getWindow();
            return (w != null && w.peekDecorView() != null);
        }

    }

    void markFragmentsCreated() {
        boolean reiterate;
        do {
            reiterate = markState(getSupportFragmentManager(), Lifecycle.State.CREATED);
        } while (reiterate);
    }

    private static boolean markState(FragmentManager manager, Lifecycle.State state) {
        boolean hadNotMarked = false;
        Collection<Fragment> fragments = manager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment == null) {
                continue;
            }
            if (fragment.getHost() != null) {
                FragmentManager childFragmentManager = fragment.getChildFragmentManager();
                hadNotMarked |= markState(childFragmentManager, state);
            }
            if (fragment.mViewLifecycleOwner != null && fragment.mViewLifecycleOwner
                    .getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                fragment.mViewLifecycleOwner.setCurrentState(state);
                hadNotMarked = true;
            }
            if (fragment.mLifecycleRegistry.getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                fragment.mLifecycleRegistry.setCurrentState(state);
                hadNotMarked = true;
            }
        }
        return hadNotMarked;
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return mFragmentLifecycleRegistry;
    }

    public boolean isResumed() {
        return mResumed;
    }
}