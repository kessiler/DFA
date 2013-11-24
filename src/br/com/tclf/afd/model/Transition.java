package br.com.tclf.afd.model;

/**
 * Created with IntelliJ IDEA.
 * User: KESSILER
 * Date: 09/11/13
 * Time: 22:24
 * To change this template use File | Settings | File Templates.
 */
public class Transition {
    private State stateSource;
    private String Character;
    private State stateDestination;

    Transition(State stateSource, String character, State stateDestination) {
        this.stateSource = stateSource;
        this.Character = character;
        this.stateDestination = stateDestination;
    }

    public State getStateDestination() {
        return stateDestination;
    }

    public State getStateSource() {
        return stateSource;
    }

    public String getCharacter() {
        return Character;
    }
}
