package br.com.example.commandlib.command.exception;

public class CommandNotSetException extends IllegalStateException {
  public CommandNotSetException(String s) {
    super(s);
  }
}
