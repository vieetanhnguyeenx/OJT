package controller;

public class ControllerFactory {
    public BaseController getController(String url) {
        if (url.equalsIgnoreCase("/login") || url.equalsIgnoreCase("/")) {
            return new LoginController();
        } else if (url.equalsIgnoreCase("/todolist")) {
            return new TodoListController();
        } else if (url.equalsIgnoreCase("/loaddata")) {
            return new LoadDataController();
        } else if (url.equalsIgnoreCase("/changestatus")) {
            return new ChangeStatusController();
        } else if (url.equalsIgnoreCase("/edittask")) {
            return new EditTaskController();
        } else if (url.equalsIgnoreCase("/addNewTask")) {
            return new AddTaskController();
        } else if (url.equalsIgnoreCase("/deleteTask")) {
            return new DeleteController();
        }
        return new TodoListController();
    }
}
