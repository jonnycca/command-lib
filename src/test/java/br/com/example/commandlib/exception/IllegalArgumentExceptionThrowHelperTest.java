package br.com.example.commandlib.exception;

import br.com.example.commandlib.command.exception.IllegalArgumentExceptionThrowHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class IllegalArgumentExceptionThrowHelperTest {

  @Test
  void shouldFailWhenArgumentIsNotProvided() {
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          Object nullArgument = null;
          IllegalArgumentExceptionThrowHelper.throwIfMissingRequiredArgument("teste", nullArgument);
        });
  }
}
