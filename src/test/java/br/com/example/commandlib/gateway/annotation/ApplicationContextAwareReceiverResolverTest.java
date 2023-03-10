package br.com.example.commandlib.gateway.annotation;

import br.com.example.commandlib.command.Receiver;
import br.com.example.commandlib.command.impl.AbstractCommand;
import br.com.example.commandlib.command.impl.AbstractCommandChain;
import br.com.example.commandlib.command.impl.AbstractReceiver;
import br.com.example.commandlib.gateway.CommandGateway;
import br.com.example.commandlib.gateway.ReceiverResolver;
import br.com.example.commandlib.gateway.exception.ReceiverNotFoundException;
import br.com.example.commandlib.gateway.impl.DefaultCommandGateway;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Import(ApplicationContextAwareReceiverResolverTest.MyTestConfiguration.class)
@SpringBootTest
class ApplicationContextAwareReceiverResolverTest {

  static class CommandWithoutAReceiver extends AbstractCommand<String, String> {}

  static class EchoCommand extends AbstractCommand<String, String> {}

  static class ConcatCommand extends AbstractCommand<ConcatCommand.Request, String> {
    @Getter
    @Setter
    @AllArgsConstructor
    static class Request {
      private String valor1;
      private String valor2;
    }
  }

  static class HelloWorld extends AbstractCommandChain<String, String> {
    @Override
    public void execute() throws IllegalStateException {
      super.execute();
      String message =
          invoke(ConcatCommand.class, new ConcatCommand.Request("Hello ", getParameter()));
      setResult(message);
    }
  }

  @CommandReceiver(ConcatCommand.class)
  static class ConcatCommandReceiver extends AbstractReceiver<ConcatCommand.Request, String> {

    @Override
    protected String doExecute(ConcatCommand.Request parameter) {
      return parameter.valor1.concat(parameter.valor2);
    }
  }

  @CommandReceiver(EchoCommand.class)
  static class EchoCommandReceiver extends AbstractReceiver<String, String> {

    @Override
    protected String doExecute(String parameter) {
      return parameter;
    }
  }

  @Configuration
  static class MyTestConfiguration {
    @Bean
    public ReceiverResolver receiverResolver() {
      return new ApplicationContextAwareReceiverResolver();
    }

    @Bean
    EchoCommandReceiver echoCommandReceiver() {
      return new EchoCommandReceiver();
    }

    @Bean
    ConcatCommandReceiver concatCommandReceiver() {
      return new ConcatCommandReceiver();
    }

    @Bean
    CommandGateway gateway() {
      return new DefaultCommandGateway(receiverResolver());
    }
  }

  @Autowired
  ReceiverResolver receiverResolver;

  @Autowired EchoCommandReceiver echoCommandReceiver;

  @Autowired
  CommandGateway commandGateway;

  @Test
  void shouldFailWhenNoReceiverIsFound() {
    assertThrows(
        ReceiverNotFoundException.class,
        () -> receiverResolver.resolve(CommandWithoutAReceiver.class));
  }

  @Test
  void shouldResolveTheProperReceiver() {
    Receiver<String, String> actual = receiverResolver.resolve(EchoCommand.class);
    assertEquals(echoCommandReceiver, actual);
  }

  @Test
  void shouldExecuteACommandChain() {
    String expected = "Hello Teste";
    String actual = commandGateway.invoke(HelloWorld.class, "Teste");
    assertEquals(expected, actual);
  }
}
