package com.example.qrranger;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ProfileTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void profileTest() {
        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.profile), withContentDescription("Profile"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.navigator),
                                        0),
                                4),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.ProfileSettingButton),
                        childAtPosition(
                                allOf(withId(R.id.ProfileLL2),
                                        childAtPosition(
                                                withId(R.id.ProfileLL1),
                                                0)),
                                3),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.SettingUsernameInput), withText("User14"),
                        childAtPosition(
                                allOf(withId(R.id.SettingLL3),
                                        childAtPosition(
                                                withId(R.id.SettingLL2),
                                                1)),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("abc"));

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.SettingUsernameInput), withText("abc"),
                        childAtPosition(
                                allOf(withId(R.id.SettingLL3),
                                        childAtPosition(
                                                withId(R.id.SettingLL2),
                                                1)),
                                0),
                        isDisplayed()));
        appCompatEditText2.perform(closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.SettingPhoneNumInput), withText("Not Set"),
                        childAtPosition(
                                allOf(withId(R.id.SettingLL5),
                                        childAtPosition(
                                                withId(R.id.SettingLL4),
                                                1)),
                                0),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("1234567890"));

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.SettingPhoneNumInput), withText("1234567890"),
                        childAtPosition(
                                allOf(withId(R.id.SettingLL5),
                                        childAtPosition(
                                                withId(R.id.SettingLL4),
                                                1)),
                                0),
                        isDisplayed()));
        appCompatEditText4.perform(closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.SettingEmailInput), withText("Not Set"),
                        childAtPosition(
                                allOf(withId(R.id.SettingLL7),
                                        childAtPosition(
                                                withId(R.id.SettingLL6),
                                                1)),
                                0),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("test@pain.ca"));

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.SettingEmailInput), withText("test@pain.ca"),
                        childAtPosition(
                                allOf(withId(R.id.SettingLL7),
                                        childAtPosition(
                                                withId(R.id.SettingLL6),
                                                1)),
                                0),
                        isDisplayed()));
        appCompatEditText6.perform(closeSoftKeyboard());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.SettingUsernameSubmitButton), withText("Submit"),
                        childAtPosition(
                                allOf(withId(R.id.SettingLL3),
                                        childAtPosition(
                                                withId(R.id.SettingLL2),
                                                1)),
                                1),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.SettingPhoneNumSubmitButton), withText("Submit"),
                        childAtPosition(
                                allOf(withId(R.id.SettingLL5),
                                        childAtPosition(
                                                withId(R.id.SettingLL4),
                                                1)),
                                1),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.SettingEmailSubmitButton), withText("Submit"),
                        childAtPosition(
                                allOf(withId(R.id.SettingLL7),
                                        childAtPosition(
                                                withId(R.id.SettingLL6),
                                                1)),
                                1),
                        isDisplayed()));
        materialButton3.perform(click());

        ViewInteraction switch_ = onView(
                allOf(withId(R.id.SettingSwitch), withText("Enable Geo Location"),
                        childAtPosition(
                                allOf(withId(R.id.SettingLL1),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        switch_.perform(click());

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.SettingBackButton), withText("Back"),
                        childAtPosition(
                                allOf(withId(R.id.SettingLL1),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                4),
                        isDisplayed()));
        materialButton4.perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
