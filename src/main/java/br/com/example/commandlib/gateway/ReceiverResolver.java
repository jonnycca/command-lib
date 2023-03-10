package br.com.example.commandlib.gateway;

import br.com.example.commandlib.command.Command;
import br.com.example.commandlib.command.Receiver;

public interface ReceiverResolver {

    <P, R> Receiver<P, R> resolve(Class<? extends Command<P, R>> commandClass);

    <P, R> void bind(Class<? extends Command> commandClass, Receiver<P, R> receiver);
}
