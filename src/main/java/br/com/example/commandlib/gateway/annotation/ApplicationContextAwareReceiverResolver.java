package br.com.example.commandlib.gateway.annotation;

import br.com.example.commandlib.command.Command;
import br.com.example.commandlib.command.CommandChain;
import br.com.example.commandlib.command.CommandChainReceiver;
import br.com.example.commandlib.command.Receiver;
import br.com.example.commandlib.gateway.ReceiverResolver;
import br.com.example.commandlib.gateway.exception.ReceiverNotFoundException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ApplicationContextAwareReceiverResolver implements ApplicationContextAware, InitializingBean, ReceiverResolver {

    private ConfigurableApplicationContext applicationContext;

    private CommandChainReceiver commandChainReceiver;

    private Map<Class<? extends Command<?, ?>>, String> receiverMap;

    public void setCommandChainReceiver(CommandChainReceiver commandChainReceiver) {
        this.commandChainReceiver = commandChainReceiver;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <P, R> Receiver<P, R> resolve(Class<? extends Command<P, R>> commandClass) {
        if (!applicationContext.isActive()) {
            this.applicationContext.refresh();
        }
        if (CommandChain.class.isAssignableFrom(commandClass)) {
            return (Receiver<P, R>) commandChainReceiver;
        }
        if (!receiverMap.containsKey(commandClass)) {
            throw new ReceiverNotFoundException("No receiver found for class [" + commandClass + "]");
        }
        return (Receiver<P, R>) applicationContext.getBean(receiverMap.get(commandClass));
    }

    @Override
    @SuppressWarnings("rawtypes")
    public <P, R> void bind(Class<? extends Command> commandClass, Receiver<P, R> receiver) {
        if (CommandChain.class.isAssignableFrom(commandClass)) {
            commandChainReceiver = (CommandChainReceiver) receiver;
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public void afterPropertiesSet() {
        receiverMap =
                Arrays.stream(applicationContext.getBeanNamesForAnnotation(CommandReceiver.class))
                        .collect(
                                Collectors.toMap(
                                        beanName ->
                                                Objects.requireNonNull(
                                                                applicationContext.findAnnotationOnBean(
                                                                        beanName, CommandReceiver.class))
                                                        .value(),
                                        Function.identity()));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = (ConfigurableApplicationContext) applicationContext;
    }
}
