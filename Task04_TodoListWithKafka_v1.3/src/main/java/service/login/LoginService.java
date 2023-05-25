package service.login;

public class LoginService {
    public static void main(String[] args) {
        LoginServiceConsumer loginServiceConsumer = new LoginServiceConsumer();
        loginServiceConsumer.start();

        LoginServiceProducer loginServiceProducer = new LoginServiceProducer();
        loginServiceProducer.start();
    }
}
