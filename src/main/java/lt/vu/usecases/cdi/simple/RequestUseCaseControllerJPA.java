package lt.vu.usecases.cdi.simple;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import lt.vu.entities.Course;
import lt.vu.entities.Student;
import lt.vu.entities.University;
import lt.vu.usecases.cdi.dao.CourseDAO;
import lt.vu.usecases.cdi.dao.StudentDAO;
import lt.vu.usecases.cdi.dao.UniversityDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model // tas pats kaip: @Named ir @RequestScoped
@Slf4j
public class RequestUseCaseControllerJPA {

    @Getter
    private Course course = new Course();
    @Getter
    private Student student = new Student();
    @Getter
    private List<Student> allStudents;

    @PostConstruct
    public void init() {
        loadAllStudents();
    }

    @Inject
    private CourseDAO courseDAO;
    @Inject
    private StudentDAO studentDAO;
    @Inject
    private UniversityDAO universityDAO;

    @Transactional
    public void createCourseStudent() {
        String studentRegistrationNo = student.getRegistrationNo();
        String VU = "VU";
        String VGTU = "VGTU";

        boolean isVU = studentRegistrationNo.matches("^123.*");
        boolean isVGTU = studentRegistrationNo.matches("^124.*");
        if (isVU) {
                University universityVU = universityDAO.getUniversityByTitle(VU);
                student.setUniversity(universityVU);
        }
        else if (isVGTU){
            University universityVGTU = universityDAO.getUniversityByTitle(VGTU);
            student.setUniversity(universityVGTU);
        }

        student.getCourseList().add(course);
        course.getStudentList().add(student);
        //reiktu idet patikrinima ar toks kursas jau egzistuoja
        courseDAO.create(course);
        studentDAO.create(student);
        log.info("Maybe OK...");
    }

    private void loadAllStudents() {
        allStudents = studentDAO.getAllStudents();
    }
}
