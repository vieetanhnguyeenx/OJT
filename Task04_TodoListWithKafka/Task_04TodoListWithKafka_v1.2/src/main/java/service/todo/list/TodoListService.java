package service.todo.list;

public class TodoListService {
    public static void main(String[] args) {
        TodoListConsumer todoListConsumer = new TodoListConsumer();
        todoListConsumer.start();
        TodoListProducer todoListProducer = new TodoListProducer();
        todoListProducer.start();
    }
}
