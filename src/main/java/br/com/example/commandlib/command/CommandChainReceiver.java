package br.com.example.commandlib.command;

public interface CommandChainReceiver extends Receiver<Object, Object> {

    <T, V> V invoke(Class<? extends Command<T, V>> commandClass, T parameter);

    @Override
    default Object execute(Object parameter) throws IllegalStateException{
        return null;
    }
}
