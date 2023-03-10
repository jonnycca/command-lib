package br.com.example.commandlib.impl;

import br.com.example.commandlib.command.Command;
import br.com.example.commandlib.command.CommandListener;
import br.com.example.commandlib.command.Invoker;
import br.com.example.commandlib.command.exception.CommandNotSetException;
import br.com.example.commandlib.command.impl.AbstractCommand;
import br.com.example.commandlib.command.impl.AbstractReceiver;
import br.com.example.commandlib.command.impl.DefaultInvoker;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DefaultInvokerTest {

  static class ConcatCommand extends AbstractCommand<ConcatCommand.Request, String> {
    @Getter
    @Setter
    @AllArgsConstructor
    static class Request {
      private String valor1;
      private String valor2;
    }
  }

  static class ConcatCommandReceiver extends AbstractReceiver<ConcatCommand.Request, String> {

    @Override
    protected String doExecute(ConcatCommand.Request parameter) {
      return parameter.valor1.concat(parameter.valor2);
    }
  }

  @Test
  void shouldFaildWhenCommandIsNotSet() {
    Invoker<String, String> invoker = new DefaultInvoker<>();
    assertThrows(CommandNotSetException.class, invoker::invoke);
  }

  @Test
  void shoudlInvokeACommand() {
    String expected = "InvokeTest";
    ConcatCommand command = new ConcatCommand();
    command.setReceiver(new ConcatCommandReceiver());

    String actual =
        new DefaultInvoker<ConcatCommand.Request, String>()
            .setParameter(new ConcatCommand.Request("Invoke", "Test"))
            .setCommand(command)
            .invoke()
            .getResult();

    assertEquals(expected, actual);
  }

  @Test
  void shoudlCallCommandListenerIfPresent() {
    ConcatCommand command = new ConcatCommand();
    command.setReceiver(new ConcatCommandReceiver());

    Consumer<Command<?, ?>> beforeExecute = mock(Consumer.class);
    Consumer<Command<?, ?>> afterExecute = mock(Consumer.class);

    new DefaultInvoker<ConcatCommand.Request, String>()
        .setParameter(new ConcatCommand.Request("Invoke", "Test"))
        .setCommand(command)
        .setCommandListener(
            new CommandListener() {
              @Override
              public Consumer<Command<?, ?>> beforeExecute() {
                return beforeExecute;
              }

              @Override
              public Consumer<Command<?, ?>> afterExecute() {
                return afterExecute;
              }
            })
        .invoke();

    verify(beforeExecute, times(1)).accept(command);
    verify(afterExecute, times(1)).accept(command);
  }
}
