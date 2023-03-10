package br.com.example.commandlib.command.impl;

import br.com.example.commandlib.command.Receiver;

public abstract class AbstractReceiver<P, R>  implements Receiver<P, R> {

    @Override
    public R execute(P parameter) throws IllegalStateException {
        return doExecute(parameter);
    }

    protected abstract R doExecute(P parameter);
}