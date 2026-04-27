package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDao extends Dao {

    /**
     * 科目コードと学校を指定して科目情報を1件取得する
     */
    public Subject get(String cd, School school) throws Exception {
        Subject subject = null;
        Connection con = getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM SUBJECT WHERE CD = ? AND SCHOOL_CD = ?";
            st = con.prepareStatement(sql);
            st.setString(1, cd);
            // 修正箇所： getSchoolCd() に変更
            st.setString(2, school.getSchoolCd());
            rs = st.executeQuery();

            if (rs.next()) {
                subject = new Subject();
                subject.setCd(rs.getString("CD"));
                subject.setName(rs.getString("NAME"));
                subject.setSchool(school);
            }
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            con.close();
        }
        return subject;
    }

    /**
     * 学校を指定して科目の一覧を取得する
     */
    public List<Subject> filter(School school) throws Exception {
        List<Subject> list = new ArrayList<>();
        Connection con = getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM SUBJECT WHERE SCHOOL_CD = ?";
            st = con.prepareStatement(sql);
            // 修正箇所： getSchoolCd() に変更
            st.setString(1, school.getSchoolCd());
            rs = st.executeQuery();

            while (rs.next()) {
                Subject subject = new Subject();
                subject.setCd(rs.getString("CD"));
                subject.setName(rs.getString("NAME"));
                subject.setSchool(school);
                list.add(subject);
            }
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            con.close();
        }
        return list;
    }

    /**
     * 科目情報を保存（または更新）する
     */
    public boolean save(Subject subject) throws Exception {
        Connection con = getConnection();
        PreparedStatement st = null;
        boolean result = false;

        try {
            // すでに存在するか確認
            Subject existing = get(subject.getCd(), subject.getSchool());

            if (existing == null) {
                // 存在しない場合は INSERT
                String sql = "INSERT INTO SUBJECT (SCHOOL_CD, CD, NAME) VALUES (?, ?, ?)";
                st = con.prepareStatement(sql);
                // 修正箇所： getSchoolCd() に変更
                st.setString(1, subject.getSchool().getSchoolCd());
                st.setString(2, subject.getCd());
                st.setString(3, subject.getName());
            } else {
                // 存在する場合は UPDATE
                String sql = "UPDATE SUBJECT SET NAME = ? WHERE CD = ? AND SCHOOL_CD = ?";
                st = con.prepareStatement(sql);
                st.setString(1, subject.getName());
                st.setString(2, subject.getCd());
                // 修正箇所： getSchoolCd() に変更
                st.setString(3, subject.getSchool().getSchoolCd());
            }

            int line = st.executeUpdate();
            if (line > 0) {
                result = true;
            }
        } finally {
            if (st != null) st.close();
            con.close();
        }
        return result;
    }

    /**
     * 科目情報を削除する
     */
    public boolean delete(Subject subject) throws Exception {
        Connection con = getConnection();
        PreparedStatement st = null;
        boolean result = false;

        try {
            String sql = "DELETE FROM SUBJECT WHERE CD = ? AND SCHOOL_CD = ?";
            st = con.prepareStatement(sql);
            st.setString(1, subject.getCd());
            // 修正箇所： getSchoolCd() に変更
            st.setString(2, subject.getSchool().getSchoolCd());

            int line = st.executeUpdate();
            if (line > 0) {
                result = true;
            }
        } finally {
            if (st != null) st.close();
            con.close();
        }
        return result;
    }
}