package br.com.example.commandlib.command.exception;

public class ReceiverNotSetException extends IllegalStateException {
  public ReceiverNotSetException(String s) {
    super(s);
  }
}
