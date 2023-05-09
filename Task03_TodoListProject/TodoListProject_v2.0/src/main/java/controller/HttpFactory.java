package controller;

public class HttpFactory {

    public HttpBaseController getController(String url) {
        if (url == null) {
            return null;
        }
        if (url.toLowerCase().equals("/login") || url.toLowerCase().equals("/")) {
            return new LoginController();
        }
        if(url.toLowerCase().equals("/todolist")) {
            return new TodoListController();
        }
        if (url.toLowerCase().equals("/loaddata")) {
            return new LoadDataController();
        }
        if (url.toLowerCase().equals("/changestatus")) {
            return new ChangeTodoStatusController();
        }
        if (url.toLowerCase().equals("/deleteitem")){
            return new DeleteItemController();
        }
        if (url.toLowerCase().equals("/edittitle")) {
            return new EditTitleController();
        }
        if (url.toLowerCase().equals("/addtodo")) {
            return new AddTodoController();
        } if (url.toLowerCase().equals("/logout")) {
            return new LogoutController();
        }
        return new NotFoundController();
    }
}
