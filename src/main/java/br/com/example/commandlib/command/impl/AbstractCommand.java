package br.com.example.commandlib.command.impl;

import br.com.example.commandlib.command.Command;
import br.com.example.commandlib.command.Receiver;
import br.com.example.commandlib.command.exception.IllegalArgumentExceptionThrowHelper;
import br.com.example.commandlib.command.exception.ReceiverNotSetException;

public class AbstractCommand <P, R> implements Command<P, R> {

    protected Receiver<P, R> receiver;
    private P parameter;
    private R result;


    @Override
    public void execute() throws IllegalStateException {
        if (receiver == null) {
            throw new ReceiverNotSetException(
                    "Receiver was not set for command[" + this.getClass().getSimpleName() + ".");
        }

        this.result = receiver.execute(parameter);
    }

    @Override
    public void setParameter(P parameter) throws IllegalArgumentException {
        this.parameter = parameter;
    }

    @Override
    public P getParameter() {
        return parameter;
    }

    @Override
    public void setReceiver(Receiver<P, R> receiver) throws IllegalArgumentException {
        IllegalArgumentExceptionThrowHelper.throwIfMissingRequiredArgument("receiver", receiver);

        this.receiver = receiver;
    }

    @Override
    public void setResult(R result) {
        this.result = result;
    }

    @Override
    public R getResult() throws IllegalStateException {
        return result;
    }
}
