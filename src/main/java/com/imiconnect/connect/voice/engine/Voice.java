package com.imiconnect.connect.voice.engine;

import com.imiconnect.connect.voice.engine.azure.AzureVoice;
import com.imiconnect.connect.voice.type.TtsAudio;

/** Definition for a type of voice to be played with a {@link TtsAudio} message. */
public interface Voice {

  public enum Engine {
    AZURE
  }

  /** Starts the creation of a new Azure based voice. */
  public static AzureVoice.Builder azure() {
    return AzureVoice.builder();
  }

  /** Gets the name of the voice. */
  public String getVoice();

  /**
   * Gets the language of the voice specified as a locale such as <b>en_US</b> for United States
   * (English).
   */
  public String getLanguage();

  /** Gets the voice engine that will handle the TTS content */
  public Engine getEngine();
}
