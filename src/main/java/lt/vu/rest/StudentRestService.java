package lt.vu.rest;

import lombok.Getter;
import lt.vu.entities.Student;
import lt.vu.usecases.cdi.dao.StudentDAO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/student")
@Produces(MediaType.APPLICATION_JSON)
public class StudentRestService {

    @Inject private EntityManager em;

    @Inject
    private StudentDAO studentDAO;

    @Getter
    private Student student = new Student();

    @GET
    @Path("/{studentId}")
    public Response find(@PathParam("studentId") Integer studentId) {
        return Response.status(200).entity(em.find(Student.class, studentId)).build();
    }

    @POST
    @Path("/{firstName}/{lastName}/{regNo}")
    @Transactional
    public Response add(@PathParam("firstName") String firstName,
                       @PathParam("lastName") String lastName,
                       @PathParam("regNo") String regNo) {
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setRegistrationNo(regNo);
        studentDAO.create(student);
        return Response.status(201).build();
    }

    @PUT
    @Path("/{studentId}/{firstName}/{lastName}/{regNo}")
    @Transactional
    public Response update(@PathParam("studentId") Integer studentId,
                          @PathParam("firstName") String firstName,
                          @PathParam("lastName") String lastName,
                          @PathParam("regNo") String regNo) {
        student = em.find(Student.class, studentId);
        if (student == null)
            return Response.status(204).build();
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setRegistrationNo(regNo);
        studentDAO.updateAndFlush(student);
        return Response.status(200).build();
    }
}
