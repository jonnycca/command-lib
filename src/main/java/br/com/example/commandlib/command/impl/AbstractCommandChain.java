package br.com.example.commandlib.command.impl;

import br.com.example.commandlib.command.Command;
import br.com.example.commandlib.command.CommandChain;
import br.com.example.commandlib.command.CommandChainReceiver;

public abstract class AbstractCommandChain<P, R> extends AbstractCommand<P, R> implements CommandChain<P, R> {

    @Override
    public <T, V> V invoke(Class<? extends Command<T, V>> commandClass, T parameters) {
        return ((CommandChainReceiver) receiver).invoke(commandClass, parameters);
    }
}
