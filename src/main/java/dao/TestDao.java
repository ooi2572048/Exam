package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;

public class TestDao extends Dao {

    /**
     * 指定された条件（入学年度、クラス、科目、回数）に一致する成績リストを取得する
     * 赤枠のテーブルを表示するために使用します。
     */
    public List<Test> filter(School school, int entYear, String classNum, Subject subject, int num) throws Exception {
        List<Test> list = new ArrayList<>();
        Connection con = getConnection();
        PreparedStatement st = null;
        
        // 学生(STUDENT)を主軸に、成績(TEST)を外部結合(LEFT OUTER JOIN)します。
        // これにより、点数が未登録の学生も一覧に表示されます。
        String sql = "SELECT S.NO, S.NAME, S.ENT_YEAR, S.CLASS_NUM, T.POINT " +
                     "FROM STUDENT S " +
                     "LEFT OUTER JOIN TEST T ON S.NO = T.STUDENT_NO AND T.SUBJECT_CD = ? AND T.NO = ? " +
                     "WHERE S.SCHOOL_CD = ? AND S.ENT_YEAR = ? AND S.CLASS_NUM = ? " +
                     "ORDER BY S.NO ASC";

        try {
            st = con.prepareStatement(sql);
            st.setString(1, subject.getCd());
            st.setInt(2, num);
            st.setString(3, school.getSchoolCd());
            st.setInt(4, entYear);
            st.setString(5, classNum);
            
            ResultSet rSet = st.executeQuery();

            while (rSet.next()) {
                Test test = new Test();
                Student student = new Student();
                
                // Beanのメソッド名(setStudentNo, setStudentName等)はご自身の環境に合わせてください
                student.setStudentNo(rSet.getString("NO"));
                student.setStudentName(rSet.getString("NAME"));
                student.setEntYear(rSet.getInt("ENT_YEAR"));
                
                test.setStudent(student);
                test.setClassNum(rSet.getString("CLASS_NUM"));
                test.setSubject(subject);
                test.setNo(num);
                test.setSchool(school);
                // 点数が登録されていない場合は NULL ですが、getInt は 0 を返します
                test.setPoint(rSet.getInt("POINT"));
                
                list.add(test);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (st != null) st.close();
            if (con != null) con.close();
        }
        return list;
    }

    /**
     * 成績リストを一括保存（新規登録または更新）する
     */
    public void save(List<Test> tests) throws Exception {
        Connection con = getConnection();
        try {
            // トランザクション処理を行う場合はここで con.setAutoCommit(false) 等を入れる
            for (Test test : tests) {
                saveOne(test, con);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) con.close();
        }
    }

    /**
     * 1件の成績データを保存する（H2のMERGE文を使用）
     */
    private void saveOne(Test test, Connection con) throws Exception {
        PreparedStatement st = null;
        try {
            // 主キーが重複していれば更新、なければ挿入を行う便利なSQLです
            st = con.prepareStatement(
                "MERGE INTO TEST (STUDENT_NO, SUBJECT_CD, SCHOOL_CD, NO, POINT, CLASS_NUM) " +
                "KEY (STUDENT_NO, SUBJECT_CD, NO) " +
                "VALUES (?, ?, ?, ?, ?, ?)");
            
            st.setString(1, test.getStudent().getStudentNo());
            st.setString(2, test.getSubject().getCd());
            st.setString(3, test.getSchool().getSchoolCd());
            st.setInt(4, test.getNo());
            st.setInt(5, test.getPoint());
            st.setString(6, test.getClassNum());
            
            st.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            if (st != null) st.close();
        }
    }
}