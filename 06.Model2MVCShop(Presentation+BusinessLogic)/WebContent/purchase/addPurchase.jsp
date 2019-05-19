<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<html><head><title>구매 완료</title></head>

<body>
	다음과 같이 구매가 되었습니다.

	<table border=1>
		<tr>
			<td>물품번호</td>
			<td>${purchase.purchaseProd.prodNo}</td>
		</tr>
		<tr>
			<td>구매자아이디</td>
			<td>${purchase.buyer.userId}</td>
		</tr>
		<tr>
			<td>구매방법</td>
			<td>
				<c:choose>
					<c:when test="${purchase.paymentOption == '100'}">
					현금구매
					</c:when>
					<c:otherwise>				
					신용구매
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td>구매자이름</td>
			<td>${purchase.receiverName}</td>
		</tr>
		<tr>
			<td>구매자연락처</td>
			<td>${purchase.receiverPhone}</td>
		</tr>
		<tr>
			<td>구매자주소</td>
			<td>${purchase.divyAddr}</td>
		</tr>
			<tr>
			<td>구매요청사항</td>
			<td>${purchase.divyRequest}</td>
		</tr>
		<tr>
			<td>배송희망일자</td>
			<td>${purchase.divyDate}</td>
		</tr>
	</table>
</body>

</html>