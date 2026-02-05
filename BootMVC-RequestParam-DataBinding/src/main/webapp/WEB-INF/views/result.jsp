<%@ page isELIgnored="false""%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student Data Result</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 600px;
            margin: 50px auto;
            padding: 20px;
            background-color: #f5f5f5;
        }
        
        .container {
            background-color: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        
        h2 {
            color: #333;
            border-bottom: 2px solid #4CAF50;
            padding-bottom: 10px;
        }
        
        .data-row {
            margin: 15px 0;
            padding: 10px;
            background-color: #f9f9f9;
            border-left: 3px solid #4CAF50;
        }
        
        .label {
            font-weight: bold;
            color: #555;
        }
        
        .value {
            color: #333;
            margin-left: 10px;
        }
        
        .address-list {
            list-style-type: none;
            padding-left: 0;
        }
        
        .address-list li {
            padding: 5px 0;
            margin-left: 20px;
        }
        
        .btn {
            display: inline-block;
            padding: 10px 20px;
            margin-top: 20px;
            background-color: #4CAF50;
            color: white;
            text-decoration: none;
            border-radius: 4px;
        }
        
        .btn:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Student Data - Result</h2>
        
        <!-- Display Student Number -->
        <c:if test="${not empty param.sno}">
            <div class="data-row">
                <span class="label">Student Number:</span>
                <span class="value">${param.sno}</span>
            </div>
        </c:if>
        
        <!-- Display Student Name -->
        <c:if test="${not empty param.sname}">
            <div class="data-row">
                <span class="label">Student Name:</span>
                <span class="value">${param.sname}</span>
            </div>
        </c:if>
        
        <!-- Display Multiple Addresses -->
        <c:if test="${not empty paramValues.sadd}">
            <div class="data-row">
                <span class="label">Addresses:</span>
                <ul class="address-list">
                    <c:forEach var="address" items="${paramValues.sadd}">
                        <li>• ${address}</li>
                    </c:forEach>
                </ul>
            </div>
        </c:if>
        
        <a href="javascript:history.back()" class="btn">Go Back</a>
    </div>
</body>
</html>
