package ru.github.pvtitov.myfootball;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        ApplyPresenterTest.class,
        GamesConsumerPresenterTest.class,
        GameTest.class,
        LoginPresenterTest.class})

public class AllTestSuite {
}
