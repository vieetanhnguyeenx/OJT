package Core;

import java.util.Properties;

public class KafkaListenerThread extends Thread{
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            try {
                sleep(7000);
                System.out.println("share" + i);
                SharedData.data.put(i, "doiden");
            } catch (InterruptedException e) {
                System.out.println("Error");
            }
        }
    }
}
