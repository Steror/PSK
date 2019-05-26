package lt.vu.asynchronous;

import lt.vu.interceptor.Logged;
import lt.vu.usecases.cdi.RescueOrAsync;
import org.apache.deltaspike.core.api.future.Futureable;

import javax.ejb.AsyncResult;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Specializes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.concurrent.Future;

@Specializes
@Alternative
@Logged
@ApplicationScoped
public class FastCompC extends CompC {

    @Inject
    @RescueOrAsync            // Asinchroninis komponentas negali naudoti @RequestScoped konteksto
    private EntityManager em; // Jei šis komponentas turi dirbti su DB per JPA

    @Futureable
    @Transactional(Transactional.TxType.REQUIRES_NEW) // be šios anotacijos negalėsime gauti @RescueOrAsync EntityManager
    public Future<String> asyncMethod() {
        System.out.println("FastCompC starts working on a small task...");
        try {
            Thread.sleep(1000); // sleep for 1 second
        } catch (InterruptedException e) {  //https://docs.oracle.com/javase/7/docs/api/java/lang/InterruptedException.html
        }
        System.out.println("FastCompC: small task completed.");
        return new AsyncResult<>("SMALL result from fast component C :)");
    }

}
