<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Todo List</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.2/css/bootstrap.min.css">

</head>
<body class="bg-gradient-to-br from-teal-50 via-white to-teal-100 min-h-screen">
<div class="container mx-auto px-4 py-8">
    <h1 class="text-3xl font-bold text-center text-gray-800 mb-6">Task Manager</h1>
    <div class="max-w-2xl mx-auto bg-white shadow-md rounded-lg overflow-hidden">
        <!-- Add Task Form -->
        <form method="post" action="/addTask" class="p-6 bg-gray-50 border-b">
            <div class="flex space-x-2">
                <input type="text" name="name" required
                       class="flex-grow p-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                       placeholder="Enter new task">
                <select name="status"
                        class="p-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500">
                    <c:forEach var="status" items="${statusList}">
                        <option value="${status}">${status}</option>
                    </c:forEach>
                </select>
                <select name="priority"
                        class="p-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500">
                    <c:forEach var="priority" items="${priorityList}">
                        <option value="${priority}">${priority}</option>
                    </c:forEach>
                </select>

                <button type="submit"
                        class="bg-blue-500 text-white px-4 py-2 rounded-md hover:bg-blue-600 transition">
                    Add Task
                </button>
            </div>
        </form>

        <!-- Filter Tasks by Status -->
        <form method="get" action="/index" class="p-6 bg-gray-50 border-b">
            <div class="flex items-center space-x-4">
                <label class="text-gray-700 font-medium mr-2">Filter by Status:</label>
                <select
                        name="status"
                        class="flex-grow p-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 max-w-xs"
                >
                    <option value="">All Status</option>
                    <c:forEach var="status" items="${statusList}">
                        <option value="${status}">${status}</option>
                    </c:forEach>
                </select>
                <button
                        type="submit"
                        class="btn btn-secondary w-40"
                >
                    Apply Filter
                </button>
            </div>
        </form>

        <!-- Tasks List -->
        <table class="w-full" id="taskTable">
            <thead class="bg-gray-100">
            <tr>
                <th class="p-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Task</th>
                <th class="p-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Status</th>
                <th class="p-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Priority</th>
                <th class="p-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Actions</th>
            </tr>
            </thead>
            <tbody class="divide-y divide-gray-200">
            <c:forEach var="task" items="${tasks}">
                <tr class="hover:bg-gray-50 transition-colors" data-task-id="${task.id}">
                    <!-- View Mode -->
                    <td class="p-4 whitespace-nowrap task-view">
                            ${task.name}
                    </td>
                    <td class="p-4 whitespace-nowrap task-view">
                        <c:choose>
                            <c:when test="${task.status == 'Completed'}">
                                <i class="fas fa-check-circle text-green-500"></i> Completed
                            </c:when>
                            <c:when test="${task.status == 'InProgress'}">
                                <i class="fas fa-spinner text-blue-500"></i> In Progress
                            </c:when>
                            <c:when test="${task.status == 'Pending'}">
                                <i class="fas fa-clock text-yellow-500"></i> Pending
                            </c:when>
                            <c:when test="${task.status == 'Cancelled'}">
                                <i class="fas fa-times-circle text-red-500"></i> Canceled
                            </c:when>
                            <c:otherwise>
                                <i class="fas fa-question-circle text-gray-500"></i> Unknown
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td class="p-4 whitespace-nowrap task-view">
                        <c:choose>
                            <c:when test="${task.priority == 'HIGH'}">
                                <i class="fas fa-exclamation-circle text-red-500"></i> High
                            </c:when>
                            <c:when test="${task.priority == 'MEDIUM'}">
                                <i class="fas fa-exclamation-triangle text-yellow-500"></i> Medium
                            </c:when>
                            <c:when test="${task.priority == 'LOW'}">
                                <i class="fas fa-check-circle text-green-500"></i> Low
                            </c:when>
                            <c:otherwise>${task.priority}</c:otherwise>
                        </c:choose>
                    </td>
                    <td class="p-4 whitespace-nowrap task-view">
                        <button onclick="toggleEdit(this)"
                                class="text-blue-500 hover:text-blue-700 transition edit-btn">
                            <i class="fas fa-edit"></i>
                        </button>
                        <form action="/deleteTask" method="post" class="inline">
                            <input type="hidden" name="id" value="${task.id}">
                            <button type="submit"
                                    class="text-red-500 hover:text-red-700 transition">
                                <i class="fas fa-trash"></i>
                            </button>
                        </form>
                    </td>

                    <!-- Edit Mode (Hidden by default) -->
                    <td colspan="4" class="p-4 task-edit hidden">
                        <form action="/updateTask" method="post" class="space-y-4">
                            <input type="hidden" name="id" value="${task.id}">

                            <div class="flex space-x-4">
                                <!-- Task Name Input -->
                                <div class="flex-grow">
                                    <label class="block text-sm font-medium text-gray-700 mb-1">Task Name</label>
                                    <input type="text" name="name" value="${task.name}"
                                           class="w-full p-2 border rounded-md focus:ring-2 focus:ring-blue-500"
                                           required>
                                </div>

                                <!-- Status Dropdown -->
                                <div>
                                    <label class="block text-sm font-medium text-gray-700 mb-1">Status</label>
                                    <select name="status"
                                            class="w-full p-2 border rounded-md focus:ring-2 focus:ring-blue-500">
                                        <option value="Pending" ${task.status == 'Pending' ? 'selected' : ''}>Pending</option>
                                        <option value="InProgress" ${task.status == 'InProgress' ? 'selected' : ''}>In Progress</option>
                                        <option value="Completed" ${task.status == 'Completed' ? 'selected' : ''}>Completed</option>
                                        <option value="Cancelled" ${task.status == 'Cancelled' ? 'selected' : ''}>Cancelled</option>
                                    </select>
                                </div>

                                <!-- Priority Dropdown -->
                                <div>
                                    <label class="block text-sm font-medium text-gray-700 mb-1">Priority</label>
                                    <select name="priority"
                                            class="w-full p-2 border rounded-md focus:ring-2 focus:ring-blue-500">
                                        <option value="LOW" ${task.priority == 'LOW' ? 'selected' : ''}>Low</option>
                                        <option value="MEDIUM" ${task.priority == 'MEDIUM' ? 'selected' : ''}>Medium</option>
                                        <option value="HIGH" ${task.priority == 'HIGH' ? 'selected' : ''}>High</option>
                                    </select>
                                </div>

                                <!-- Action Buttons -->
                                <div class="flex items-end space-x-2">
                                    <button type="submit"
                                            class="bg-green-500 text-white px-4 py-2 rounded-md hover:bg-green-600 transition">
                                        Save
                                    </button>
                                    <button type="button" onclick="toggleEdit(this)"
                                            class="bg-gray-300 text-gray-700 px-4 py-2 rounded-md hover:bg-gray-400 transition">
                                        Cancel
                                    </button>
                                </div>
                            </div>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <script>
            function toggleEdit(btn) {
                const row = btn.closest('tr');
                const viewCells = row.querySelectorAll('.task-view');
                const editCell = row.querySelector('.task-edit');

                // Toggle visibility
                viewCells.forEach(cell => cell.classList.toggle('hidden'));
                editCell.classList.toggle('hidden');
            }
        </script>

        <!-- Empty State -->
        <div class="text-center p-8 text-gray-500">
            <c:if test="${tasks.isEmpty()}">
                No tasks available. Add a new task to get started!
            </c:if>
        </div>
    </div>
</div>
</body>
</html>
