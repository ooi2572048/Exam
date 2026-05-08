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

        List<Test> saveList = new ArrayList<>();   // 保存（更新）用
        List<Test> deleteList = new ArrayList<>(); // 削除用

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

                // 点数が空欄かどうかで振り分ける
                if (points[i] == null || points[i].trim().isEmpty()) {
                    deleteList.add(test); // 空欄なら削除リストへ
                } else {
                    test.setPoint(Integer.parseInt(points[i]));
                    saveList.add(test);   // 入力ありなら保存リストへ
                }
            }
        }

        TestDao tDao = new TestDao();
        
        // 保存処理
        if (!saveList.isEmpty()) {
            tDao.save(saveList);
        }
        
        // 削除処理（空欄のデータをDBから消す）
        if (!deleteList.isEmpty()) {
            tDao.delete(deleteList);
        }

        req.getRequestDispatcher("test_regist_done.jsp").forward(req, res);
    }
}