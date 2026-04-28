package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestRegistAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        if (teacher == null) { res.sendRedirect("Login.action"); return; }

        // パラメータ取得
        String entYearStr = req.getParameter("f1");
        String classNum = req.getParameter("f2");
        String subjectCd = req.getParameter("f3");
        String numStr = req.getParameter("f4");

        // プルダウン準備
        List<Integer> entYearSet = new ArrayList<>();
        int year = LocalDate.now().getYear();
        for (int i = year - 10; i <= year; i++) { entYearSet.add(i); }
        
        ClassNumDao cDao = new ClassNumDao();
        req.setAttribute("class_nums", cDao.filter(teacher.getSchool()));
        
        SubjectDao sDao = new SubjectDao();
        req.setAttribute("subjects", sDao.filter(teacher.getSchool()));
        req.setAttribute("ent_year_set", entYearSet);

        // 【重要】検索実行：条件が揃っている場合のみ
        if (entYearStr != null && !entYearStr.equals("0") && subjectCd != null && !subjectCd.equals("0")) {
            int entYear = Integer.parseInt(entYearStr);
            int num = Integer.parseInt(numStr);
            
            Subject subject = new Subject();
            subject.setCd(subjectCd);

            TestDao tDao = new TestDao();
            List<Test> tests = tDao.filter(teacher.getSchool(), entYear, classNum, subject, num);
            req.setAttribute("tests", tests); // これで赤枠が出る！
        }

        // 検索値の保持
        req.setAttribute("f1", entYearStr);
        req.setAttribute("f2", classNum);
        req.setAttribute("f3", subjectCd);
        req.setAttribute("f4", numStr);

        req.getRequestDispatcher("test_regist.jsp").forward(req, res);
    }
}