package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentUpdateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws Exception {

        // ① パラメータ取得
        String no = req.getParameter("no");

		HttpSession session = req.getSession();
			Teacher teacher = (Teacher) session.getAttribute("user");


        // パラメータチェック
        if (no == null || no.isEmpty()) {
            // noが無い場合は一覧へ（安全策）
            res.sendRedirect("StudentList.action");
            return;
        }

        // ② DBから学生情報を取得
        StudentDao studentDao = new StudentDao();
        Student student = studentDao.get(no);

        // 学生が存在しない場合
        if (student == null) {
            res.sendRedirect("StudentList.action");
            return;
        }
        //入学年度表示させるやつ
     // ログインユーザーの学校コードをもとにクラス番号の一覧を取得
        ClassNumDao classNumDao = new ClassNumDao(); // クラス番号Daoを初期化
		LocalDate todaysDate = LocalDate.now(); // LocalDateインスタンスを取得
		int year = todaysDate.getYear(); // 現在の年を取得

		// リクエストパラメーターの取得 2
		// なし

		// DBからデータ取得 3
		// ログインユーザーの学校コードをもとにクラス番号の一覧を取得
		List<String> list = classNumDao.filter(teacher.getSchool());

		// ビジネスロジック 4
		// リストを初期化
		List<Integer> entYearSet = new ArrayList<>();
		// 10年前から10年後まで年をリストに追加
		for (int i = year - 10; i < year + 11; i++) {
			entYearSet.add(i);
		}
		// レスポンス値をセット 6
				// リクエストにデータをセット
				req.setAttribute("class_num_set", list);
				req.setAttribute("ent_year_set", entYearSet);


        // ③ JSPへ渡す
        req.setAttribute("student", student);

        // ④ フォワード
        req.getRequestDispatcher("student_update.jsp")
           .forward(req, res);
    }
}