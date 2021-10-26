package com.imiconnect.connect.voice.callback.action;

/** Defines a type of action that is used as a reply to a callback event. */
public interface Action {

  public enum Type {
    ANSWER,
    HANGUP,
    PATCH,
    PLAY,
    RECORD
  }

  /**
   * Gets the name of the action.
   *
   * @return The name of the action.
   */
  public Type getAction();
}
