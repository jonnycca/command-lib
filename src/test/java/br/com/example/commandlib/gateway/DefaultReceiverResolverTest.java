package br.com.example.commandlib.gateway;

import br.com.example.commandlib.command.Receiver;
import br.com.example.commandlib.command.impl.AbstractCommand;
import br.com.example.commandlib.command.impl.AbstractReceiver;
import br.com.example.commandlib.gateway.exception.ReceiverNotFoundException;
import br.com.example.commandlib.gateway.impl.DefaultReceiverResolver;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DefaultReceiverResolverTest {

  static class EchoCommand extends AbstractCommand<String, String> {}

  static class EchoCommandReceiver extends AbstractReceiver<String, String> {

    @Override
    protected String doExecute(String parameter) {
      return parameter;
    }
  }

  @Test
  void shouldResolveTheProperReceiver() {
    ReceiverResolver receiverResolver = new DefaultReceiverResolver();
    EchoCommandReceiver expected = new EchoCommandReceiver();
    receiverResolver.bind(EchoCommand.class, expected);

    Receiver<String, String> actual = receiverResolver.resolve(EchoCommand.class);
    assertEquals(expected, actual);
  }

  @Test
  void shouldFailWhenNoReceiverIsFound() {
    ReceiverResolver receiverResolver = new DefaultReceiverResolver();

    assertThrows(
        ReceiverNotFoundException.class, () -> receiverResolver.resolve(EchoCommand.class));
  }
}
