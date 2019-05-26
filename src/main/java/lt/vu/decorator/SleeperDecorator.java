package lt.vu.decorator;

import lt.vu.asynchronous.Sleeper;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import java.util.concurrent.Future;

@Decorator
public abstract class SleeperDecorator implements Sleeper {

    @Inject
    @Delegate
    @Any
    Sleeper sleeper;

    @Override
    public Future<String> asyncMethod() {

        System.out.println("Decorator: I do something on top of "+this.sleeper);
        return sleeper.asyncMethod();
    }
}
