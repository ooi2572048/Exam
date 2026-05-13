package scoremanager.main;

import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestDeleteExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // セッションからログイン情報取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();  // ★ここ追加

        String studentNo = req.getParameter("studentNo");
        String subjectCd = req.getParameter("subjectCd");
        String numStr = req.getParameter("num");

        TestDao dao = new TestDao();

        if (studentNo != null && subjectCd != null && numStr != null) {
            int num = Integer.parseInt(numStr);

            Test test = new Test();

            Student student = new Student();
            student.setStudentNo(studentNo);

            Subject subject = new Subject();
            subject.setCd(subjectCd);

            test.setStudent(student);
            test.setSubject(subject);
            test.setNo(num);
            test.setSchool(school); // ★これが超重要！！

            List<Test> deleteList = new ArrayList<>();
            deleteList.add(test);

            dao.delete(deleteList);
        }

        // 完了画面へ
        req.setAttribute("studentNo", studentNo);
        req.getRequestDispatcher("test_delete_done.jsp").forward(req, res);
    }
}