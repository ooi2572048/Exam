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
     * 学校に紐づく入学年度の一覧を取得する（重複なし、昇順）
     */
    public List<Integer> filterEntYear(School school) throws Exception {
        List<Integer> list = new ArrayList<>();
        Connection con = getConnection();
        PreparedStatement st = null;

        String sql = "SELECT DISTINCT S.ENT_YEAR FROM STUDENT S " +
                     "WHERE S.SCHOOL_CD = ? " +
                     "ORDER BY S.ENT_YEAR ASC";
        try {
            st = con.prepareStatement(sql);
            st.setString(1, school.getSchoolCd());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(rs.getInt("ENT_YEAR"));
            }
        } finally {
            if (st != null) st.close();
            if (con != null) con.close();
        }
        return list;
    }

    /**
     * 学校に紐づくクラス番号の一覧を取得する（重複なし、昇順）
     */
    public List<String> filterClassNum(School school) throws Exception {
        List<String> list = new ArrayList<>();
        Connection con = getConnection();
        PreparedStatement st = null;

        String sql = "SELECT DISTINCT S.CLASS_NUM FROM STUDENT S " +
                     "WHERE S.SCHOOL_CD = ? " +
                     "ORDER BY S.CLASS_NUM ASC";
        try {
            st = con.prepareStatement(sql);
            st.setString(1, school.getSchoolCd());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("CLASS_NUM"));
            }
        } finally {
            if (st != null) st.close();
            if (con != null) con.close();
        }
        return list;
    }

    /**
     * 指定された条件（入学年度、クラス、科目、回数）に一致する成績リストを取得する
     */
    public List<Test> filter(School school, int entYear, String classNum, Subject subject, int num) throws Exception {
        List<Test> list = new ArrayList<>();
        Connection con = getConnection();
        PreparedStatement st = null;
        
        // 学生(STUDENT)を主軸に、成績(TEST)を外部結合(LEFT OUTER JOIN)
        String sql = "SELECT S.STUDENT_NO, S.STUDENT_NAME, S.ENT_YEAR, S.CLASS_NUM, T.POINT " +
                     "FROM STUDENT S " +
                     "LEFT OUTER JOIN TEST T " +
                     " ON S.STUDENT_NO = T.STUDENT_NO " +
                     " AND T.SUBJECT_CD = ? " +
                     " AND T.NO = ? " +
                     "WHERE S.SCHOOL_CD = ? " +
                     " AND S.ENT_YEAR = ? " +
                     " AND S.CLASS_NUM = ? " +
                     "ORDER BY S.STUDENT_NO ASC";

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
                
                student.setStudentNo(rSet.getString("STUDENT_NO"));
                student.setStudentName(rSet.getString("STUDENT_NAME"));
                student.setEntYear(rSet.getInt("ENT_YEAR"));
                
                test.setStudent(student);
                test.setClassNum(rSet.getString("CLASS_NUM"));
                test.setSubject(subject);
                test.setNo(num);
                test.setSchool(school);
                
                // pointはInteger型であることを想定。nullの場合は-1をセットするなどの工夫も可能
                test.setPoint(rSet.getInt("POINT"));
                // getIntは値がNULLの場合0を返すため、もしNULLと0を区別したい場合は res.wasNull() を使用します
                if (rSet.wasNull()) {
                    test.setPoint(-1); // 未登録状態として-1をセット（参照画面の棒線表示と整合性を取るため）
                }
                
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

    /**
     * 成績リストを一括削除する（空欄登録時の対応）
     */
    public void delete(List<Test> tests) throws Exception {
        Connection con = getConnection();
        try {
            for (Test test : tests) {
                deleteOne(test, con);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) con.close();
        }
    }

    /**
     * 1件の成績データを削除する
     */
    private void deleteOne(Test test, Connection con) throws Exception {
        PreparedStatement st = null;
        try {
            String sql = "DELETE FROM TEST WHERE STUDENT_NO = ? AND SUBJECT_CD = ? AND NO = ? AND SCHOOL_CD = ?";
            st = con.prepareStatement(sql);
            st.setString(1, test.getStudent().getStudentNo());
            st.setString(2, test.getSubject().getCd());
            st.setInt(3, test.getNo());
            st.setString(4, test.getSchool().getSchoolCd());
            
            st.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            if (st != null) st.close();
        }
    }
}