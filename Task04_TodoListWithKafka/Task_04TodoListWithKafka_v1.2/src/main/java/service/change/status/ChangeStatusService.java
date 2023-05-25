package service.change.status;

public class ChangeStatusService {
    public static void main(String[] args) {
        ChangeStatusConsumer changeStatusConsumer = new ChangeStatusConsumer();
        changeStatusConsumer.start();
        ChangeStatusProvider changeStatusProvider = new ChangeStatusProvider();
        changeStatusProvider.start();
    }
}
