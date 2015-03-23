<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Welcome to the Diet Tracker</title>
</head>
<body>
<h1>Welcome to the Diet Tracker</h1>
You're looking at: <c:out value = "${day.getDate().toString()}" /> </br>
Here's the day:
<table>
<c:forEach items="${mealList}" var="meal">
<tr>
    <td><c:out value="${meal.getName()}" /></td>
    <c:forEach items="${meal.getPortions()}" var="portions" >
    <td><c:out value="${portions.getFood().getName()}" /> </td>
    <td><c:out value="${portions.getCalories()}" /> </td>
    </c:forEach>
    <td>Total Calories (meal): <c:out value="${meal.getCalories()}" /> </td>
</tr>
</c:forEach>
<!-- <tr><td>Total Calories (Day): <%-- <c:out value="${day.getCalories()}" /> </td></tr> --%> -->
</table>

<form name = "daySelect" action = "welcome" method = "GET">
<input type = "submit" name="newDay" value = "previous" />
<input type = "submit" name="newDay" value="next" />
</form>

<p> Click <a href="search"> here </a> to search for a food </p>
</body>
</html>