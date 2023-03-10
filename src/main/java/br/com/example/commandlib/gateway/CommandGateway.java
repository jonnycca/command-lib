package br.com.example.commandlib.gateway;

import br.com.example.commandlib.command.Command;
import br.com.example.commandlib.gateway.exception.GatewayException;

public interface CommandGateway {
  <P, R> R invoke(Class<? extends Command<P, R>> commandClass, P parameter)
      throws GatewayException; // TODO: Implementar tratamento de erros
}
