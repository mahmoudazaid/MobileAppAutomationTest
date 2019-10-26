package com.freenow.utilities;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.ViewFinder;
import android.support.test.espresso.ViewInteraction;
import android.view.View;

import org.hamcrest.Matcher;

import java.lang.reflect.Field;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;

public class WaitResourceLoaded implements IdlingResource {


    private static final String TAG = WaitResourceLoaded.class.getSimpleName();

    private final Matcher<View> viewMatcher;
    private ResourceCallback resourceCallback;

    public WaitResourceLoaded(final Matcher<View> viewMatcher) {
        this.viewMatcher = viewMatcher;
    }

    @Override
    public boolean isIdleNow() {
        View view = getView(viewMatcher);
        boolean idle = view == null || view.isShown();

        if (idle && resourceCallback != null) {
            resourceCallback.onTransitionToIdle();
        }

        return idle;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
        this.resourceCallback = resourceCallback;
    }

    @Override
    public String getName() {
        return this + viewMatcher.toString();
    }

    private static View getView(Matcher<View> viewMatcher) {
        try {
            ViewInteraction viewInteraction = onView(viewMatcher);
            Field finderField = viewInteraction.getClass().getDeclaredField("viewFinder");
            finderField.setAccessible(true);
            ViewFinder finder = (ViewFinder) finderField.get(viewInteraction);
            return finder.getView();
        } catch (Exception e) {
            return null;
        }
    }

    public void waitViewShown(Matcher<View> matcher) {
        IdlingResource idlingResource = new WaitResourceLoaded(matcher);///
        try {
            IdlingRegistry.getInstance().register(idlingResource);
            onView(matcher).check(matches(isDisplayed()));
        } finally {
            IdlingRegistry.getInstance().unregister(idlingResource);
        }
    }
}