package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.TestListStudent;
import bean.TestListSubject;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestListStudentDao;
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

        // プルダウン準備
        ClassNumDao cDao = new ClassNumDao();
        SubjectDao sDao = new SubjectDao();
        LocalDate todaysDate = LocalDate.now();
        int year = todaysDate.getYear();
        List<Integer> entYearSet = new ArrayList<>();
        for (int i = year - 10; i <= year; i++) { entYearSet.add(i); }
        
        request.setAttribute("ent_year_set", entYearSet);
        request.setAttribute("class_num_set", cDao.filter(school));
        request.setAttribute("subjects", sDao.filter(school));

        // ボタンが押されたかどうかの判定用
        boolean isSearch = (entYearStr != null || studentNo != null);

        if (isSearch) {
            // --- 科目検索のロジック ---
            if (studentNo == null || studentNo.isEmpty()) {
                request.setAttribute("title_type", "subject");
                // 入力チェック（どれか一つでも0なら入力不足）
                if (entYearStr.equals("0") || classNum.equals("0") || subjectCd.equals("0")) {
                    request.setAttribute("error_type", "incomplete"); // 入力不足エラー
                } else {
                    int entYear = Integer.parseInt(entYearStr);
                    Subject subject = new Subject();
                    subject.setCd(subjectCd);
                    TestListSubjectDao subDao = new TestListSubjectDao();
                    List<TestListSubject> rawTests = subDao.filter(school, entYear, classNum, subject);

                    if (rawTests != null && !rawTests.isEmpty()) {
                        Map<String, TestListSubject> map = new LinkedHashMap<>();
                        for (TestListSubject t : rawTests) {
                            String key = t.getStudentNo();
                            if (!map.containsKey(key)) {
                                t.setPoint1(-1); t.setPoint2(-1);
                                map.put(key, t);
                            }
                            if (t.getNo() == 1) map.get(key).setPoint1(t.getPoint());
                            if (t.getNo() == 2) map.get(key).setPoint2(t.getPoint());
                        }
                        request.setAttribute("tests_subject", new ArrayList<>(map.values()));
                    } else {
                        request.setAttribute("error_type", "empty"); // 情報なしエラー
                    }
                }
            } 
            // --- 学生検索のロジック ---
            else {
                request.setAttribute("title_type", "student");
                StudentDao stuDao = new StudentDao();
                Student student = stuDao.get(studentNo);
                if (student != null) {
                    TestListStudentDao tlsDao = new TestListStudentDao();
                    List<TestListStudent> tests = tlsDao.filter(student);
                    request.setAttribute("student_info", student);
                    if (tests != null && !tests.isEmpty()) {
                        request.setAttribute("tests_student", tests);
                    } else {
                        request.setAttribute("error_type", "empty"); // 情報なしエラー
                    }
                } else {
                    request.setAttribute("error_type", "empty"); // 学生自体が見つからない場合
                }
            }
        }

        request.setAttribute("f1", entYearStr);
        request.setAttribute("f2", classNum);
        request.setAttribute("f3", subjectCd);
        request.setAttribute("f4", studentNo);

        request.getRequestDispatcher("test_list.jsp").forward(request, response);
    }
}