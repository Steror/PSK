package lt.vu.asynchronous;

import java.util.concurrent.Future;

public interface Sleeper {

    Future<String> asyncMethod();
}
