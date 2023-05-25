package service.load.data;

public class LoadDataService {
    public static void main(String[] args) {
        LoadDataConsumer loadDataConsumer = new LoadDataConsumer();
        loadDataConsumer.start();
        LoadDataProducer loadDataProducer = new LoadDataProducer();
        loadDataProducer.start();
    }
}
