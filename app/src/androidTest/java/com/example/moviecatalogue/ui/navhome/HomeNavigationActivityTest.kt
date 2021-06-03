package com.example.moviecatalogue.ui.navhome

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeUp
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.moviecatalogue.R
import com.example.moviecatalogue.utils.DummyFilm
import com.example.moviecatalogue.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Test

class HomeNavigationActivityTest {
    private val dummyMovies = DummyFilm.getMovies()
    private val dummyTvShow = DummyFilm.getTvShows()

    @Before
    fun setUp() {
        ActivityScenario.launch(HomeNavigationActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun loadMovies() {
        onView(withId(R.id.rv_movies))
            .check(matches(isDisplayed()))
        onView(withId(R.id.rv_movies))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyMovies.size))
    }

    @Test
    fun loadMoviesDetail() {
        onView(withId(R.id.rv_movies)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
                click()
            ))
        onView(withId(R.id.iv_detail))
            .check(matches(isDisplayed()))
        onView(withId(R.id.collapsing))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tv_detail_genre))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tv_detail_genre))
            .check(matches(withText(dummyMovies[0].genre)))
        onView(withId(R.id.tv_detail_duration))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tv_detail_duration))
            .check(matches(withText(dummyMovies[0].duration)))
        onView(withId(R.id.tv_detail_overview))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tv_detail_overview))
            .check(matches(withText(dummyMovies[0].overview)))
    }

    @Test
    fun loadTvShow() {
        onView(withText("TV SHOWS")).perform(click())
        onView(withId(R.id.rv_tv_shows))
            .check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv_shows))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyTvShow.size))
    }

    @Test
    fun loadTvShowDetail() {
        onView(withText("TV SHOWS")).perform(click())
        onView(withId(R.id.rv_tv_shows)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
                click()
            ))
        onView(withId(R.id.iv_detail))
            .check(matches(isDisplayed()))
        onView(withId(R.id.collapsing))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tv_detail_genre))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tv_detail_genre))
            .check(matches(withText(dummyTvShow[0].genre)))
        onView(withId(R.id.tv_detail_duration))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tv_detail_duration))
            .check(matches(withText(dummyTvShow[0].duration)))
        onView(withId(R.id.tv_detail_overview))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tv_detail_overview))
            .check(matches(withText(dummyTvShow[0].overview)))
    }

    @Test
    fun loadFavMovies() {
        onView(withId(R.id.navigation_favorite)).perform(click())
        onView(withId(R.id.rv_movies_fav)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movies_fav)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                dummyMovies.size
            )
        )
    }

    @Test
    fun loadDetailFavMovie() {
        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movies)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.fab_add_to_fav)).perform(click())
        onView(isRoot()).perform(ViewActions.pressBack())

        onView(withId(R.id.navigation_favorite)).perform(click())
        onView(withId(R.id.rv_movies_fav)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.iv_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_add_to_fav)).perform(click())
        onView(withId(R.id.coordinator_layout)).perform(swipeUp())
        onView(withId(R.id.collapsing)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_detail_duration)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_detail_overview)).check(matches(isDisplayed()))
    }

    @Test
    fun loadFavTvShows() {
        onView(withId(R.id.navigation_favorite)).perform(click())
        onView(withText("TV SHOWS")).perform(click())
        onView(withId(R.id.rv_tv_shows_fav)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv_shows_fav)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                dummyTvShow.size
            )
        )
    }

    @Test
    fun loadDetailFavTvShow() {
        onView(withId(R.id.navigation_home)).perform(click())
        onView(withText("TV SHOWS")).perform(click())
        onView(withId(R.id.rv_tv_shows)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv_shows)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.fab_add_to_fav)).perform(click())
        onView(isRoot()).perform(ViewActions.pressBack())

        onView(withId(R.id.navigation_favorite)).perform(click())
        onView(withText("TV SHOWS")).perform(click())
        onView(withId(R.id.rv_tv_shows_fav)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.iv_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_add_to_fav)).perform(click())
        onView(withId(R.id.coordinator_layout)).perform(swipeUp())
        onView(withId(R.id.collapsing)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_detail_duration)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_detail_overview)).check(matches(isDisplayed()))
    }

}