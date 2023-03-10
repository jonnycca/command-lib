package br.com.example.commandlib.command;

public interface Command <P, R> {

    void execute() throws IllegalStateException;
    void setParameter(P parameter) throws IllegalArgumentException;
    P getParameter();

    void setReceiver(Receiver<P, R> receiver) throws IllegalArgumentException;

    void setResult(R result);
    R getResult() throws IllegalStateException;
}
