package algonquin.cst2335.rustomlab01;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
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

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    /**
     * First test automatically by phone.
     */
    @Test
    public void mainActivityTest() {
        ViewInteraction appCompatEditText = onView
               (withId(R.id.editText));
        appCompatEditText.perform(replaceText("12345"), closeSoftKeyboard());

        ViewInteraction materialButton = onView
                (withId(R.id.button));
        materialButton.perform(click());

        ViewInteraction textView = onView
                (withId(R.id.textView));
        textView.check(matches(withText("You shall not pass!")));

    }

    /**
     *This method test for miss value of UpperCase
     */
    @Test
    public void testFindMissingUpperCase(){
        ViewInteraction appCompatEditText = onView (withId(R.id.editText));
        appCompatEditText.perform(replaceText("password123#$*"));

        ViewInteraction materialButton = onView (withId(R.id.button));
        materialButton.perform(click());

        ViewInteraction textView = onView (withId(R.id.textView));
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     *This method test for miss value of LowerCase
     */
    @Test
    public void testFindMissingLowerCase(){
        ViewInteraction appCompatEditText = onView (withId(R.id.editText));
        appCompatEditText.perform(replaceText("PASS123#$*"));

        ViewInteraction materialButton = onView (withId(R.id.button));
        materialButton.perform(click());

        ViewInteraction textView = onView (withId(R.id.textView));
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     *This method test for missong digit in password
     */
    @Test
    public void testFindMissingDigit(){
        ViewInteraction appCompatEditText = onView (withId(R.id.editText));
        appCompatEditText.perform(replaceText("password#$*"));

        ViewInteraction materialButton = onView (withId(R.id.button));
        materialButton.perform(click());

        ViewInteraction textView = onView (withId(R.id.textView));
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     *This method test for missing special character
     */
    @Test
    public void testFindMissingSpecialCharacter(){
        ViewInteraction appCompatEditText = onView (withId(R.id.editText));
        appCompatEditText.perform(replaceText("password123"));

        ViewInteraction materialButton = onView (withId(R.id.button));
        materialButton.perform(click());

        ViewInteraction textView = onView (withId(R.id.textView));
        textView.check(matches(withText("You shall not pass!")));
    }


    /**
     * This test meets all the password  requirement
     */
    @Test
    public void passwordMeetsComplexityRequirements() {
        ViewInteraction appCompatEditText = onView (withId(R.id.editText));
        appCompatEditText.perform(replaceText("Password123#$*"));

        ViewInteraction materialButton = onView (withId(R.id.button));
        materialButton.perform(click());

        ViewInteraction textView = onView (withId(R.id.textView));
        textView.check(matches(withText("Your password meets all the requirements.")));
    }

}
