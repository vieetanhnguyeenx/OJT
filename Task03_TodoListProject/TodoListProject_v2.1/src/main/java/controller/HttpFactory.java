package controller;

public class HttpFactory {
    public HttpBaseController getController(String url) {
        if (url.equalsIgnoreCase("/login") || url.equalsIgnoreCase("/")) {
            return new LoginController();
        }
        if (url.equalsIgnoreCase("/todolist")) {
            return new TodoListController();
        }
        if (url.equalsIgnoreCase("/loaddata")) {
            return new LoadDataController();
        }
        if (url.equalsIgnoreCase("/changeStatus")) {
            return new ChangeStatusController();
        }
        if (url.equalsIgnoreCase("/editTask")) {
            return new EditTaskController();
        }
        if (url.equalsIgnoreCase("/deleteTask")) {
            return new DeleteTaskController();
        }
        if (url.equalsIgnoreCase("/ex")) {
            return new AddNewTaskController();
        }
        if (url.equalsIgnoreCase("/404NotFound")) {
            return new NotFoundController();
        }
        return new NotFoundController();
    }
}
