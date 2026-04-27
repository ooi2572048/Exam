<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>科目管理 | 得点管理システム</title>
    <link rel="stylesheet" href="../../css/style.css">
</head>
<body>
    <header>
        <h1>得点管理システム</h1>
        <div class="user-info">
            <c:out value="${user.teacherName}" /> 様
            <a href="../logout.action">ログアウト</a>
        </div>
    </header>

    <div class="container">
        <aside>
            <ul>
                <li><a href="Menu.action">メニュー</a></li>
                <li><a href="StudentList.action">学生管理</a></li>
                <li>成績管理
                    <ul>
                        <li><a href="ScoreCreate.action">成績登録</a></li>
                        <li><a href="ScoreList.action">成績参照</a></li>
                    </ul>
                </li>
                <li><a href="SubjectList.action">科目管理</a></li>
            </ul>
        </aside>

        <main>
            <h2>科目管理</h2>

            <div style="text-align: right; margin-bottom: 10px;">
                <a href="SubjectCreate.action">新規登録</a>
            </div>

            <table border="1" style="width: 100%; border-collapse: collapse;">
                <thead>
                    <tr>
                        <th style="padding: 8px; text-align: left;">科目コード</th>
                        <th style="padding: 8px; text-align: left;">科目名</th>
                        <th style="padding: 8px;"></th>
                        <th style="padding: 8px;"></th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <%-- 科目が1件も登録されていない場合の表示 --%>
                        <c:when test="${empty subjects}">
                            <tr>
                                <td colspan="4" style="text-align: center; padding: 15px;">
                                    科目が存在しません
                                </td>
                            </tr>
                        </c:when>
                        
                        <%-- 科目が登録されている場合はループで表示 --%>
                        <c:otherwise>
                            <c:forEach var="subject" items="${subjects}">
                                <tr>
                                    <td style="padding: 8px;"><c:out value="${subject.cd}" /></td>
                                    <td style="padding: 8px;"><c:out value="${subject.name}" /></td>
                                    
                                    <td style="padding: 8px; text-align: center;">
                                        <a href="SubjectUpdate.action?cd=${subject.cd}">変更</a>
                                    </td>
                                    <td style="padding: 8px; text-align: center;">
                                        <a href="SubjectDelete.action?cd=${subject.cd}">削除</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </main>
    </div>

    <footer>
        <p>&copy; 2023 TIC 大原学園</p>
    </footer>
</body>
</html>