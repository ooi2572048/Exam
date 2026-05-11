package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListStudent;

public class TestListStudentDao extends Dao {

    /**
     * 学生情報から成績リストを取得する
     */
    public List<TestListStudent> filter(Student student) throws Exception {
        List<TestListStudent> list = new ArrayList<>();
        Connection con = getConnection();
        PreparedStatement st = null;

        // TESTテーブルとSUBJECTテーブルを結合して科目名を取得
        String sql = "SELECT SUB.SUBJECT_NAME, T.SUBJECT_CD, T.NO, T.POINT " +
                     "FROM TEST T " +
                     "JOIN SUBJECT SUB ON T.SUBJECT_CD = SUB.SUBJECT_CD AND T.SCHOOL_CD = SUB.SCHOOL_CD " +
                     "WHERE T.STUDENT_NO = ? AND T.SCHOOL_CD = ? " +
                     "ORDER BY T.SUBJECT_CD ASC, T.NO ASC";

        try {
            st = con.prepareStatement(sql);
            st.setString(1, student.getStudentNo());
            st.setString(2, student.getSchool().getSchoolCd());
            ResultSet res = st.executeQuery();

            while (res.next()) {
                TestListStudent ts = new TestListStudent();
                ts.setSubjectName(res.getString("SUBJECT_NAME"));
                ts.setSubjectCd(res.getString("SUBJECT_CD"));
                ts.setNum(res.getInt("NO"));
                ts.setPoint(res.getInt("POINT"));
                list.add(ts);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (st != null) st.close();
            if (con != null) con.close();
        }
        return list;
    }
}