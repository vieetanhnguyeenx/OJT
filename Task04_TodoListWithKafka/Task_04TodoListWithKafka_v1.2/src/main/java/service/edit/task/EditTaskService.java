package service.edit.task;

public class EditTaskService {
    public static void main(String[] args) {
        EditTaskConsumer editTaskConsumer = new EditTaskConsumer();
        editTaskConsumer.start();

        EditTaskProducer editTaskProducer = new EditTaskProducer();
        editTaskProducer.start();
    }
}
