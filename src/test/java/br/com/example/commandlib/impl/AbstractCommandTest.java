package br.com.example.commandlib.impl;

import br.com.example.commandlib.command.exception.ReceiverNotSetException;
import br.com.example.commandlib.command.impl.AbstractCommand;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class AbstractCommandTest {

  @Test
  void shouldFailWhenReceiverIsNotSet() {
    AbstractCommand<String, String> command =
        mock(AbstractCommand.class, Mockito.CALLS_REAL_METHODS);
    assertThrows(ReceiverNotSetException.class, command::execute);
  }
}
