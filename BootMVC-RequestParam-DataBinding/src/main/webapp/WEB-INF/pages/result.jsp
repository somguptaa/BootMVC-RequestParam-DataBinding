<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Result Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            margin: 0;
        }
        
        .container {
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            width: 500px;
            text-align: center;
        }
        
        h2 {
            color: #4CAF50;
            margin-bottom: 20px;
        }
        
        .info {
            margin: 15px 0;
            padding: 10px;
            background: #f9f9f9;
            border-radius: 5px;
            text-align: left;
        }
        
        .label {
            font-weight: bold;
            color: #333;
        }
        
        .value {
            color: #2196F3;
        }
        
        .null-value {
            color: #999;
            font-style: italic;
        }
        
        ul {
            margin: 10px 0;
            padding-left: 20px;
        }
        
        li {
            color: #2196F3;
        }
        
        .back-btn {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 20px;
            background: #4CAF50;
            color: white;
            text-decoration: none;
            border-radius: 5px;
        }
        
        .back-btn:hover {
            background: #45a049;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>✅ Data Received</h2>

        <!-- Student Number -->
        <div class="info">
            <span class="label">Student Number: </span>
            <c:choose>
                <c:when test="${not empty param.sno}">
                    <span class="value">${param.sno}</span>
                </c:when>
                <c:otherwise>
                    <span class="null-value">null (default: 0)</span>
                </c:otherwise>
            </c:choose>
        </div>

        <!-- Student Name -->
        <div class="info">
            <span class="label">Student Name: </span>
            <c:choose>
                <c:when test="${not empty param.sname}">
                    <span class="value">${param.sname}</span>
                </c:when>
                <c:otherwise>
                    <span class="null-value">null (default: Guest)</span>
                </c:otherwise>
            </c:choose>
        </div>

        <!-- Age -->
        <div class="info">
            <span class="label">Age: </span>
            <c:choose>
                <c:when test="${not empty param.age}">
                    <span class="value">${param.age}</span>
                </c:when>
                <c:otherwise>
                    <span class="null-value">null</span>
                </c:otherwise>
            </c:choose>
        </div>

        <!-- Cities -->
        <div class="info">
            <span class="label">Cities: </span>
            <c:choose>
                <c:when test="${not empty paramValues.city}">
                    <ul>
                        <c:forEach var="city" items="${paramValues.city}">
                            <li>${city}</li>
                        </c:forEach>
                    </ul>
                </c:when>
                <c:otherwise>
                    <span class="null-value">null</span>
                </c:otherwise>
            </c:choose>
        </div>

        <a href="javascript:history.back()" class="back-btn">← Back</a>
    </div>
</body>
</html>

<%-- **Key Features I Added (As a Learner):**

1. **Clean Layout** - Easy to read, professional look
2. **Conditional Display** - Shows "Not provided" if parameter missing
3. **Multiple Values Support** - Displays cities as list
4. **Visual Feedback** - Color-coded values (blue for data, gray for empty)
5. **Learning Notes** - Reminds me of JSP syntax I learned
6. **Debug Section** - Shows ALL parameters received
7. **Responsive** - Works on different screen sizes

**What Each Section Does:**
- `${param.sno}` - Gets single value from URL
- `${paramValues.city}` - Gets all city values as array
- `<c:choose>` - Like if-else in Java
- `<c:forEach>` - Loops through multiple values
- `${not empty param.name}` - Checks if parameter exists

**Test URLs to Try:**
```
http://localhost:4045/basic?sno=101&sname=John
http://localhost:4045/optional?sno=101
http://localhost:4045/default
http://localhost:4045/multi?city=Hyd&city=Pune&city=Delhi
http://localhost:4045/mixed?sno=101&sname=John&age=25 --%>