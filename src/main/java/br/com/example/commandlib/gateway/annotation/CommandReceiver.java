package br.com.example.commandlib.gateway.annotation;

import br.com.example.commandlib.command.Command;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface CommandReceiver {

    Class<? extends Command<?, ?>> value();
}
