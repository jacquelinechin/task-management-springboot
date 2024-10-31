angular.module('taskApp', [])
    .service('TaskService', function ($http) {
        const apiUrl = 'http://localhost:8080/tasks';

        this.getAllTasks = function () {
            return $http.get(apiUrl);
        };

        this.getTaskById = function (id) {
            return $http.get(`${apiUrl}/${id}`);
        };

        this.createTask = function (task) {
            return $http.post(apiUrl, task);
        };

        this.updateTask = function (id, updatedTask) {
            return $http.put(`${apiUrl}/${id}`, updatedTask);
        };

        this.deleteTask = function (id) {
            return $http.delete(`${apiUrl}/${id}`);
        };
    })
    .controller('TaskController', function ($scope, TaskService) {
        $scope.tasks = [];
        $scope.newTask = {};
        $scope.editMode = false;

        // Load all tasks
        $scope.loadTasks = function () {
            TaskService.getAllTasks()
                .then(function (response) {
                    $scope.tasks = response.data.sort((a, b) => a.status - b.status);
                })
                .catch(function (error) {
                    console.error("Error loading tasks:", error);
                });
        };

        // Add a new task
        $scope.addTask = function () {
            if ($scope.editMode) {
                TaskService.updateTask($scope.newTask.id, $scope.newTask)
                    .then(function (response) {
                        $scope.loadTasks();
                        $scope.newTask = {};
                        $scope.editMode = false;
                    })
                    .catch(function (error) {
                        console.error("Error updating task:", error);
                    });
            } else {
                $scope.newTask.status = false
                TaskService.createTask($scope.newTask)
                    .then(function (response) {
                        $scope.tasks.push(response.data);
                        $scope.loadTasks();
                        $scope.newTask = {};
                    })
                    .catch(function (error) {
                        console.error("Error creating task:", error);
                    });
            }
        };

        // Edit task (populate form for editing)
        $scope.editTask = function (task) {
            $scope.newTask = angular.copy(task);
            $scope.editMode = true;
        };

        // Clear form
        $scope.clearForm = function () {
            $scope.newTask = {};
            $scope.editMode = false;
        };

        // Delete a task
        $scope.deleteTask = function (id) {
            TaskService.deleteTask(id)
                .then(function (response) {
                    $scope.loadTasks();
                })
                .catch(function (error) {
                    console.error("Error deleting task:", error);
                });
        };

        // Update task status
        $scope.statusChanged = function (task) {
            TaskService.updateTask(task.id, task)
                .then(function (response) {
                    $scope.loadTasks();
                })
                .catch(function (error) {
                    console.error("Error updating task status:", error);
                });
        };

        $scope.loadTasks();
    });
