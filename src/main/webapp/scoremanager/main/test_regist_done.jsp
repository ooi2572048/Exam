<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
    <c:param name="title">登録完了</c:param>
    <c:param name="content">
        <div class="container mt-5 text-center">
            <h2 class="mb-4">登録が完了しました</h2>
            <p class="mb-4">成績データが正しく保存されました。</p>
            <a href="scoremanager.main.TestRegist.action" class="btn btn-primary">続けて登録する</a>
            <a href="scoremanager.main.MenuAction.action" class="btn btn-secondary">メニューへ戻る</a>
        </div>
    </c:param>
</c:import>