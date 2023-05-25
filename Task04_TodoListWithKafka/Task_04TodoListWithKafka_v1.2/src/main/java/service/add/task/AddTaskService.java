package service.add.task;

public class AddTaskService {
    public static void main(String[] args) {
        AddTaskConsumer addTaskConsumer = new AddTaskConsumer();
        addTaskConsumer.start();
        AddTaskProducer addTaskProducer = new AddTaskProducer();
        addTaskProducer.start();
    }
}
