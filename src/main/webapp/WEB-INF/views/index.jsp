<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Task Manager</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.2/css/bootstrap.min.css">
</head>
<body>
<div class="container py-5">
    <h1 class="mb-4">Task Manager</h1>

    <!-- Add Task Form -->
    <form method="post" action="/addTask" class="mb-4">
        <div class="row">
            <div class="col-md-6">
                <input type="text" class="form-control" name="name" placeholder="Task Name" required>
            </div>
            <div class="col-md-4">
                <select name="status" class="form-select" required>
                    <c:forEach var="status" items="${statusList}">
                        <option value="${status}">${status}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-md-2">
                <button type="submit" class="btn btn-primary w-100">Add Task</button>
            </div>
        </div>
    </form>

    <!-- Filter Tasks by Status -->
    <form method="get" action="/index" class="mb-4">
        <div class="row">
            <div class="col-md-10">
                <select name="status" class="form-select">
                    <option value="">All</option>
                    <c:forEach var="status" items="${statusList}">
                        <option value="${status}">${status}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-md-2">
                <button type="submit" class="btn btn-secondary w-100">Filter</button>
            </div>
        </div>
    </form>

    <!-- Task List -->
    <h3>Your Tasks</h3>
    <div class="list-group">
        <c:forEach var="task" items="${tasks}">
            <div class="list-group-item d-flex justify-content-between align-items-center">
                <div>
                    <strong>${task.name}</strong>
                    <small class="text-muted">(${task.status})</small>
                </div>
                <form method="post" action="/deleteTask">
                    <input type="hidden" name="taskId" value="${task.id}">
                    <button type="submit" class="btn btn-danger btn-sm">Delete</button>
                </form>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>
