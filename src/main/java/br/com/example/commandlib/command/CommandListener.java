package br.com.example.commandlib.command;

import java.util.function.BiConsumer;
import java.util.function.Consumer;


public interface CommandListener {

  default Consumer<Command<?, ?>> beforeExecute() {
    return command -> {};
  }

  default Consumer<Command<?, ?>> afterExecute() {
    return command -> {};
  }

  default BiConsumer<Command<?, ?>, Throwable> onError() {
    return (command, throwable) -> {};
  }
}
