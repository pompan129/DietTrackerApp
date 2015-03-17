<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Welcome to the Diet Tracker</title>
</head>
<body>
<h1>Welcome to the Diet Tracker</h1>
<p>Here's your day so far:<p>
<table>
<c:forEach items="${mealList}" var="meal">
<tr>
    <td><c:out value="${meal.getName()}" /></td>
    <c:forEach items="${meal.getPortions()}" var="portions" >
        <td><c:out value="${portions.getFood().getName()}" /> </td>
    </c:forEach>
</tr>
</c:forEach>
<p> Click <a href="search"> here </a> to search for a food </p>
</body>
</html>