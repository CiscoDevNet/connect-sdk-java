package com.cisco.cpaas.voice.engine;

import com.cisco.cpaas.voice.engine.azure.AzureBuilder;
import com.cisco.cpaas.voice.type.TtsAudio;

/** Definition for a type of voice to be played with a {@link TtsAudio} message. */
public interface Voice {

  /** Starts the creation of a new Azure based voice. */
  public static AzureBuilder.BaseStep azure() {
    return new AzureBuilder.Builder();
  }

  /** Gets the gender of the voice. */
  public Gender getGender();

  /** Gets the name of the voice. */
  public String getVoice();

  /** Gets the style of the voice. */
  public Style getStyle();

  /** Gets the Language of the voice. */
  public String getLanguage();
}
