package br.com.example.commandlib.gateway.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GatewayExceptionTest {

  static class TestClass {
    public void throwException() {
      throw new GatewayException("Test Msg", "Test Code", 100);
    }
  }

  @Test
  void shouldCreate() {
    TestClass testClass = new TestClass();
    GatewayException exception = assertThrows(GatewayException.class, testClass::throwException);
    assertEquals("Test Code", exception.getCode());
    assertEquals(100, exception.getGroup());
    assertEquals("Test Msg", exception.getMessage());
  }
}
