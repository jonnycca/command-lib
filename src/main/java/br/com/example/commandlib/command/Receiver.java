package br.com.example.commandlib.command;

public interface Receiver<P, R>{

    R execute(P parameter) throws IllegalStateException;
}
