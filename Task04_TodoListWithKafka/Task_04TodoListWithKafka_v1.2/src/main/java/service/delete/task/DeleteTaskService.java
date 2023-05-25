package service.delete.task;

public class DeleteTaskService {
    public static void main(String[] args) {
        DeleTaskConsumer deleTaskConsumer = new DeleTaskConsumer();
        deleTaskConsumer.start();

        DeleteTaskProducer deleteTaskProducer = new DeleteTaskProducer();
        deleteTaskProducer.start();
    }
}
