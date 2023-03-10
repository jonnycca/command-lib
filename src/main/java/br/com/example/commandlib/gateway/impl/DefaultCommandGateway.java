package br.com.example.commandlib.gateway.impl;


import br.com.example.commandlib.command.*;
import br.com.example.commandlib.command.impl.DefaultInvoker;
import br.com.example.commandlib.gateway.CommandGateway;
import br.com.example.commandlib.gateway.ReceiverResolver;
import org.springframework.beans.BeanUtils;

public class DefaultCommandGateway implements CommandGateway, CommandChainReceiver {

  private final ReceiverResolver receiverResolver;
  private CommandListener commandListener;

  public DefaultCommandGateway(ReceiverResolver receiverResolver) {
    this(receiverResolver, true);
  }

  public DefaultCommandGateway(ReceiverResolver receiverResolver, boolean bindAsChainReceiver) {
    this.receiverResolver = receiverResolver;
    if (bindAsChainReceiver) {
      this.receiverResolver.bind(CommandChain.class, this);
    }
  }

  @Override
  public <P, R> R invoke(Class<? extends Command<P, R>> commandClass, P parameter) {
    Command<P, R> command = BeanUtils.instantiateClass(commandClass);
    command.setReceiver(receiverResolver.resolve(commandClass));
    Invoker<P, R> invoker = new DefaultInvoker<>();
    invoker
            .setCommand(command)
            .setParameter(parameter)
            .setCommandListener(commandListener)
            .invoke();
    return invoker.getResult();
  }

  public void setCommandListener(CommandListener commandListener) {
    this.commandListener = commandListener;
  }
}
