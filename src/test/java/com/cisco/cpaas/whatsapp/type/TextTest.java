package com.cisco.cpaas.whatsapp.type;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Text} class to ensure it can be constructed properly and applies proper
 * content validation.
 */
class TextTest {

  @Test
  public void shouldNotAllowTextWithoutUrlWhenPreviewIsTrue() {
    assertThrows(IllegalArgumentException.class, () ->
      Text.builder().content("text msg").previewUrl(true).build());
  }

  @Test
  public void shouldAcceptTextWithUrlWhenPreviewIsTrue() {
    Text text = Text.builder().content("text http://www.example.com msg").previewUrl(true).build();
    assertThat(text.getContent(), equalTo("text http://www.example.com msg"));
    assertThat(text.getPreviewUrl(), equalTo(true));
  }

  @ParameterizedTest
  @ValueSource(strings = {"plain message", "message with url http://www.example.com msg"})
  public void shouldAcceptAnyMessageWhenPreviewFalse(String msg) {
    Text.builder().content(msg).previewUrl(false).build();
    //no exception
  }
}
