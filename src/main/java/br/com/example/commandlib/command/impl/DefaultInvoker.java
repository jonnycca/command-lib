package br.com.example.commandlib.command.impl;

import br.com.example.commandlib.command.Command;
import br.com.example.commandlib.command.CommandListener;
import br.com.example.commandlib.command.Invoker;
import br.com.example.commandlib.command.exception.CommandNotSetException;
import br.com.example.commandlib.command.exception.IllegalArgumentExceptionThrowHelper;

import java.util.Optional;

public class DefaultInvoker<P, R> implements Invoker<P, R> {

    private Command<P, R> command;
    private P parameter;
    private CommandListener commandListener;

    @Override
    public Invoker<P, R> setCommand(Command<P, R> command) {
        IllegalArgumentExceptionThrowHelper.throwIfMissingRequiredArgument("command", command);

        this.command = command;
        return this;
    }

    @Override
    public Invoker<P, R> setParameter(P parameter) {
        IllegalArgumentExceptionThrowHelper.throwIfMissingRequiredArgument("parameter", parameter);

        this.parameter = parameter;
        return this;
    }

    @Override
    public Invoker<P, R> setCommandListener(CommandListener commandListener) {
        this.commandListener = commandListener;
        return this;
    }

    @Override
    public R getResult() throws IllegalStateException {
        assertCommand();
        return command.getResult();
    }

    @Override
    public Invoker<P, R> invoke() throws IllegalStateException {
        assertCommand();
        if (parameter != null) {
            command.setParameter(parameter);
        }
        Optional.ofNullable(commandListener)
                .ifPresent(listener -> listener.beforeExecute().accept(command));
        command.execute();
        Optional.ofNullable(commandListener)
                .ifPresent(listener -> listener.afterExecute().accept(command));

        return this;
    }

    protected void assertCommand() {
        if (command == null) {
            throw new CommandNotSetException("Command was not set.");
        }
    }
}
