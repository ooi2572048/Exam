package scoremanager.main;

import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestRegistExecuteAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        String[] studentNos = req.getParameterValues("student_no");
        String[] points = req.getParameterValues("point");
        String subjectCd = req.getParameter("subject_cd");
        String noStr = req.getParameter("no");
        String classNum = req.getParameter("class_num");

        List<Test> list = new ArrayList<>(); 
        if (studentNos != null && points != null) {
            int no = Integer.parseInt(noStr);
            Subject subject = new Subject();
            subject.setCd(subjectCd);

            for (int i = 0; i < studentNos.length; i++) {
                Test test = new Test();
                Student student = new Student();
                student.setStudentNo(studentNos[i]);
                test.setStudent(student);
                test.setSubject(subject);
                test.setSchool(teacher.getSchool());
                test.setNo(no);
                test.setClassNum(classNum);
                test.setPoint(Integer.parseInt(points[i]));
                list.add(test);
            }
        }

        TestDao tDao = new TestDao();
        tDao.save(list);

        res.sendRedirect("test_regist_done.jsp");
    }
}