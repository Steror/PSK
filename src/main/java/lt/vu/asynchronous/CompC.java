package lt.vu.asynchronous;

import lt.vu.usecases.cdi.RescueOrAsync;
import org.apache.deltaspike.core.api.future.Futureable;

import javax.ejb.AsyncResult;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.concurrent.Future;

@Alternative
@ApplicationScoped
public class CompC implements Serializable, Sleeper {

    @Inject
    @RescueOrAsync            // Asinchroninis komponentas negali naudoti @RequestScoped konteksto
    private EntityManager em; // Jei šis komponentas turi dirbti su DB per JPA

    @Futureable
    @Transactional(Transactional.TxType.REQUIRES_NEW) // be šios anotacijos negalėsime gauti @RescueOrAsync EntityManager
    public Future<String> asyncMethod() {
        System.out.println("CompC starts working on a medium task...");
        try {
            Thread.sleep(5000); // sleep for 5 second
        } catch (InterruptedException e) {  //https://docs.oracle.com/javase/7/docs/api/java/lang/InterruptedException.html
        }
        System.out.println("CompC: medium task completed.");
        return new AsyncResult<>("MEDIUM result from component C :)");
    }

}
