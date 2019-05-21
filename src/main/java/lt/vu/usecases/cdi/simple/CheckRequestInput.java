package lt.vu.usecases.cdi.simple;

import lt.vu.entities.Student;
import lt.vu.entities.University;
import lt.vu.usecases.cdi.dao.UniversityDAO;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class CheckRequestInput {

    @Inject
    private UniversityDAO universityDAO;

    public Student CheckStudentRegistrationNo (Student student) {
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
        return student;
    }
}
