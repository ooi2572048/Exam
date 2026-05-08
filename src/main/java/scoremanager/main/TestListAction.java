package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Subject;
import bean.Teacher;
import bean.TestListSubject;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestListSubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        String entYearStr = request.getParameter("f1");
        String classNum = request.getParameter("f2");
        String subjectCd = request.getParameter("f3");
        String studentNo = request.getParameter("f4");

        TestListSubjectDao tDao = new TestListSubjectDao();
        SubjectDao sDao = new SubjectDao();
        ClassNumDao cDao = new ClassNumDao();

        LocalDate todaysDate = LocalDate.now();
        int year = todaysDate.getYear();
        List<Integer> entYearSet = new ArrayList<>();
        for (int i = year - 10; i <= year + 1; i++) {
            entYearSet.add(i);
        }
        request.setAttribute("class_num_set", cDao.filter(school));
        request.setAttribute("subjects", sDao.filter(school));
        request.setAttribute("ent_year_set", entYearSet);

        List<TestListSubject> rawTests = null;

        if (entYearStr != null && !entYearStr.equals("0") && !classNum.equals("0") && !subjectCd.equals("0")) {
            int entYear = Integer.parseInt(entYearStr);
            Subject subject = new Subject();
            subject.setCd(subjectCd);
            rawTests = tDao.filter(school, entYear, classNum, subject);
            request.setAttribute("title_type", "subject"); // 科目検索フラグ
        } else if (studentNo != null && !studentNo.isEmpty()) {
            rawTests = tDao.filter(studentNo, school);
            request.setAttribute("title_type", "student"); // 学生検索フラグ
        }

        // --- データ集約ロジック (1学生1行にまとめる) ---
        if (rawTests != null) {
            Map<String, TestListSubject> map = new LinkedHashMap<>();
            for (TestListSubject t : rawTests) {
                String key = t.getStudentNo();
                if (!map.containsKey(key)) {
                    map.put(key, t);
                }
                // 回数に応じて点数をセット
                if (t.getNo() == 1) {
                    map.get(key).setPoint1(t.getPoint());
                } else if (t.getNo() == 2) {
                    map.get(key).setPoint2(t.getPoint());
                }
            }
            request.setAttribute("tests", new ArrayList<>(map.values()));
        }

        request.setAttribute("f1", entYearStr);
        request.setAttribute("f2", classNum);
        request.setAttribute("f3", subjectCd);
        request.setAttribute("f4", studentNo);

        request.getRequestDispatcher("test_list.jsp").forward(request, response);
    }
}