<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title>Selections</title>
</head>

<body>

<h1>Selections</h1>

<p>You have selected the following foods:</p>
<!-- food list -->
<table>
    <tr>
        <th>Food Chosen</th>
        <th>Portions Eaten</th>
        <th>Total Calories</th>
    </tr>
    <c:forEach items="${userPortionList}" var="portion">
        <tr>
            <td><c:out value="${portion.getFood().getName()}" /></td>
            <td><c:out value="${portion.getPortionSize()}" /> </td>
            <td><c:out value="${portion.getCalories()}" /> </td>
        </tr>
    </c:forEach>
</table>

<p>Please select the correct meal in which to store these foods</p>
    <form name = "mealSelect" action = "select" method="POST">
<table>
    <tr>
        <th>Meals</th>
    </tr>
    <c:forEach items="${meals}" var="meal" varStatus="mealCount">
        <tr>
            <td><input type="radio" name = "mealID" value=<c:out value="${mealCount.count}" /> /> </td>
            <td><c:out value="${meal.getName()}" /> </td>
        </tr>
    </c:forEach>
    </table>
    <input type = "submit" />
    </form>

<p> <span class ="error">${error}</span></p>
<p>  <span class="submitted">${submitted}</span> </p>


<c:choose>
    <c:when test="${posted}">
        <p>Here what you chose for <c:out value="${userMeal.getName()}" /></p>
        <c:forEach items="${userPortionList}" var="portion">
            <p><c:out value="${portion.getFood().getName()}"/>, <c:out value="${portion.getCalories()}" /> calories</p>
        </c:forEach>
        <p>Click <a href = "search"> here </a> to search for food for another meal.</p>
        <p>Click <a href = "welcome"> here </a> to go back to the welcome page.</p>
    </c:when>
</c:choose>





</body>
</html>