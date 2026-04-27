<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>科目情報登録 | 得点管理システム</title>
    <link rel="stylesheet" href="../../css/style.css">
</head>
<body>
    <header>
        <h1>得点管理システム</h1>
        <div class="user-info">
            <c:out value="${user.name}" /> 様
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
            <h2>科目情報登録</h2>

            <c:if test="${not empty errors}">
                <div class="error-messages">
                    <c:forEach var="error" items="${errors}">
                        <p style="color: red;"><c:out value="${error}" /></p>
                    </c:forEach>
                </div>
            </c:if>

            <form action="SubjectCreateExecute.action" method="post">
                <div class="form-group">
                    <label for="cd">科目コード</label>
                    <input type="text" id="cd" name="cd" value="${cd}" 
                           placeholder="科目コードを入力してください" maxlength="3" required>
                </div>

                <div class="form-group">
                    <label for="name">科目名</label>
                    <input type="text" id="name" name="name" value="${name}" 
                           placeholder="科目名を入力してください" maxlength="20" required>
                </div>

                <div class="form-actions">
                    <input type="submit" value="登録" class="btn btn-primary">
                    <a href="SubjectList.action" class="btn-link">戻る</a>
                </div>
            </form>
        </main>
    </div>

    <footer>
        <p>&copy; 2023 TIC 大原学園</p>
    </footer>
</body>
</html>