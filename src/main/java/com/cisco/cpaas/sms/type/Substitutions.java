package com.cisco.cpaas.sms.type;

import com.cisco.cpaas.core.annotation.Nullable;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Domain specific version of a hashmap that represents the substitution key value pairs that are
 * optionally used with some request objects.
 */
public final class Substitutions {

  @JsonValue private final Map<String, String> substitutions;

  public Substitutions(Map<String, String> substitutions) {
    this.substitutions = Collections.unmodifiableMap(substitutions);
  }

  public Optional<String> getSubstitutionVal(String key) {
    return Optional.ofNullable(substitutions.get(key));
  }

  public @Nullable Substitution getSubstitution(String key) {
    if (substitutions.containsKey(key)) {
      return Substitution.of(key, substitutions.get(key));
    }
    return null;
  }

  public List<Substitution> getAll() {
    return substitutions.entrySet().stream()
        .map(e -> Substitution.of(e.getKey(), e.getValue()))
        .collect(Collectors.toList());
  }
}
