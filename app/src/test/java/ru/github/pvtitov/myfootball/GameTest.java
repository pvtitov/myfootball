package ru.github.pvtitov.myfootball;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import ru.github.pvtitov.myfootball.contracts.Game;
import ru.github.pvtitov.myfootball.contracts.User;

import static org.junit.Assert.assertEquals;

public class GameTest {

    private Game game = new Game();
    private User user = new User();
    private List<Integer> attendees = new ArrayList<>();

    @Test public void addAttendee_DifferentLogin_Adds() {
        user.setLogin("login");
        game.setAttendees(attendees);

        game.addAttendee(user);

        assertEquals(game.getAttendees().size(), 1);
    }

    @Test public void addAttendee_SameLogin_DoesntAdd() {
        user.setLogin("login");
        attendees.add("login".hashCode());
        game.setAttendees(attendees);

        game.addAttendee(user);

        assertEquals(game.getAttendees().size(), 1);
    }
}
