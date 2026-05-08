<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">
        得点管理システム
    </c:param>

    <c:param name="scripts"></c:param>

    <c:param name="content">
<section>
    <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">
        学生情報変更
    </h2>
    

		
    
	
    <form action="StudentUpdateExecute.action" method="post">


     <div>
		    <label for="entYear">入学年度</label>
		    <select class="form-select" id="entYear" name="entYear">
		        <option value="0">--------</option>
		        <c:forEach var="year" items="${ent_year_set}">
		            <option value="${year}"
		                <c:if test="${year == student.entYear}">selected</c:if>>
		                ${year}
		            </option>
		        </c:forEach>
		    </select>
		</div>
		
        <!-- 学生番号 -->
        <div>
            <label>学生番号</label><br>
            ${student.studentNo}
            <input type="hidden" name="studentNo" value="${student.studentNo}">
        </div>

        <!-- 入学年度 -->
        
		  

        <!-- 氏名 -->
        <div>
            <label for="studentName">氏名</label>
            <input class="form-control"
                   type="text"
                   id="studentName"
                   name="studentName"
                   value="${student.studentName}" required>
        </div>

        
			
			<div class="mx-auto py-2">
			    <label for="classNum">クラス</label>
			    <select class="form-select" id="classNum" name="classNum">
			        <c:forEach var="num" items="${class_num_set}">
			            <option value="${num}"
			                <c:if test="${num == student.classNum}">selected</c:if>>
			                ${num}
			            </option>
			        </c:forEach>
			    </select>
			</div>
			

        <!-- 在学中 -->
        <div class="form-check">
            <input class="form-check-input"
                   type="checkbox"
                   id="isAttend"
                   name="isAttend"
                   value="true"
                   <c:if test="${student.attend}">checked</c:if>>
            <label class="form-check-label" for="isAttend">
                在学中
            </label>
        </div>

        <div class="mx-auto py-2">
            <button type="submit" class="btn btn-secondary">更新</button>
        </div>

    </form>

    <a href="StudentList.action">戻る</a>
</section>
</c:param>
</c:import>