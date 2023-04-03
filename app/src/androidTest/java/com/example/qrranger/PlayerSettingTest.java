package com.example.qrranger;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
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
/**
 * Tests if changing player settings can be done
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class PlayerSettingTest {

    @Rule
    public ActivityScenarioRule<MainActivityController> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivityController.class);

    @Test
    public void playerSettingTest() {
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
allOf(withId(R.id.SettingUsernameInput),
childAtPosition(
allOf(withId(R.id.SettingLL3),
childAtPosition(
withId(R.id.SettingLL2),
1)),
0),
isDisplayed()));
        appCompatEditText.perform(replaceText("apples"), closeSoftKeyboard());
        
        ViewInteraction appCompatEditText2 = onView(
allOf(withId(R.id.SettingPhoneNumInput),
childAtPosition(
allOf(withId(R.id.SettingLL5),
childAtPosition(
withId(R.id.SettingLL4),
1)),
0),
isDisplayed()));
        appCompatEditText2.perform(replaceText("1234567890"), closeSoftKeyboard());
        
        ViewInteraction appCompatEditText3 = onView(
allOf(withId(R.id.SettingEmailInput),
childAtPosition(
allOf(withId(R.id.SettingLL7),
childAtPosition(
withId(R.id.SettingLL6),
1)),
0),
isDisplayed()));
        appCompatEditText3.perform(replaceText("apples@tree.com"), closeSoftKeyboard());
        
        ViewInteraction editText = onView(
allOf(withId(R.id.SettingUsernameInput), withText("apples"),
withParent(allOf(withId(R.id.SettingLL3),
withParent(withId(R.id.SettingLL2)))),
isDisplayed()));
        editText.check(matches(withText("apples")));
        
        ViewInteraction editText2 = onView(
allOf(withId(R.id.SettingPhoneNumInput), withText("1234567890"),
withParent(allOf(withId(R.id.SettingLL5),
withParent(withId(R.id.SettingLL4)))),
isDisplayed()));
        editText2.check(matches(withText("1234567890")));
        
        ViewInteraction editText3 = onView(
allOf(withId(R.id.SettingEmailInput), withText("apples@tree.com"),
withParent(allOf(withId(R.id.SettingLL7),
withParent(withId(R.id.SettingLL6)))),
isDisplayed()));
        editText3.check(matches(withText("apples@tree.com")));
        

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
                        && view.equals(((ViewGroup)parent).getChildAt(position));
            }
        };
    }
    }
