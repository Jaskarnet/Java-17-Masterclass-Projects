package net.jaskar.game;

import java.util.function.Predicate;

// Predicate<Integer> action - is used to continue or end the play.
// The type parameter Integer is the player's index in the player list.
// A predicate always returns a boolean result.
public record GameAction(char key, String prompt, Predicate<Integer> action) {
}
