package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;
import bean.TestListSubject;

public class TestListSubjectDao extends Dao {

    /**
     * ResultSetからBeanのリストへの変換（科目検索用）
     */
    private List<TestListSubject> postFilter(ResultSet res) throws SQLException {
        List<TestListSubject> list = new ArrayList<>();
        while (res.next()) {
            TestListSubject ts = new TestListSubject();
            ts.setEntYear(res.getInt("ENT_YEAR"));
            ts.setStudentNo(res.getString("STUDENT_NO"));
            ts.setStudentName(res.getString("STUDENT_NAME"));
            ts.setClassNum(res.getString("CLASS_NUM"));

            int point1 = res.getInt("POINT1");
            ts.setPoint1(res.wasNull() ? -1 : point1);

            int point2 = res.getInt("POINT2");
            ts.setPoint2(res.wasNull() ? -1 : point2);

            list.add(ts);
        }
        return list;
    }

    /**
     * 科目情報を条件に成績一覧を取得する（横並び表示用）
     * 1回目・2回目の点数を1行にまとめて取得する
     */
    public List<TestListSubject> filter(int entYear, String classNum, Subject subject, School school) throws Exception {
        List<TestListSubject> list = new ArrayList<>();

        // STUDENTをベースにTESTを回数別にLEFT JOINして1行に横並びで取得する
        String sql = "SELECT S.ENT_YEAR, S.STUDENT_NO, S.STUDENT_NAME, S.CLASS_NUM, " +
                     "T1.POINT AS POINT1, T2.POINT AS POINT2 " +
                     "FROM STUDENT S " +
                     "LEFT JOIN TEST T1 ON S.STUDENT_NO = T1.STUDENT_NO " +
                     "  AND S.SCHOOL_CD = T1.SCHOOL_CD " +
                     "  AND T1.SUBJECT_CD = ? AND T1.NO = 1 " +
                     "LEFT JOIN TEST T2 ON S.STUDENT_NO = T2.STUDENT_NO " +
                     "  AND S.SCHOOL_CD = T2.SCHOOL_CD " +
                     "  AND T2.SUBJECT_CD = ? AND T2.NO = 2 " +
                     "WHERE S.SCHOOL_CD = ? AND S.ENT_YEAR = ? AND S.CLASS_NUM = ? " +
                     "ORDER BY S.STUDENT_NO ASC";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, subject.getCd());
            statement.setString(2, subject.getCd());
            statement.setString(3, school.getSchoolCd());
            statement.setInt(4, entYear);
            statement.setString(5, classNum);

            try (ResultSet resultSet = statement.executeQuery()) {
                list = postFilter(resultSet);
            }
        }
        return list;
    }
}
