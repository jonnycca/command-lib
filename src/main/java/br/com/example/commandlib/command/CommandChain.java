package br.com.example.commandlib.command;

public interface CommandChain<P, R> extends Command<P, R> {
  <T, V> V invoke(Class<? extends Command<T, V>> commandClass, T parameters);
}
