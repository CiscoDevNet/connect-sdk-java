package com.cisco.cpaas.sms.type;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

/** Unit tests for the {@link Substitutions} pojo. */
class SubstitutionsTest {

  private Substitutions substitutions;

  @BeforeEach
  public void init() {
    Map<String, String> subs = new HashMap<>();
    subs.put("key1", "val1");
    subs.put("key2", "val2");
    subs.put("key3", "val3");
    substitutions = new Substitutions(subs);
  }

  @Test
  public void shouldHandleEmptyMap() {
    Substitutions substitutions = new Substitutions(new HashMap<>());
    substitutions.getAll();
    substitutions.getSubstitution("");
    substitutions.getSubstitutionVal("");
  }

  @Test
  public void shouldReturnAllSubstitutions() {
    substitutions.getAll();
    assertThat(
        substitutions.getAll(),
        contains(
            Substitution.of("key1", "val1"),
            Substitution.of("key2", "val2"),
            Substitution.of("key3", "val3")));
  }

  @Test
  public void shouldReturnSubstitutionAsType() {
    assertThat(substitutions.getSubstitution("key2"), is(Substitution.of("key2", "val2")));
  }

  @Test
  public void shouldReturnSubstitutionValue() {
    assertThat(substitutions.getSubstitutionVal("key2"), is(Optional.of("val2")));
  }
}
