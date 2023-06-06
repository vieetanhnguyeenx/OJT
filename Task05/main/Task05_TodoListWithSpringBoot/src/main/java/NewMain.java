import com.example.Task05_TodoListWithSpringBoot.Task05TodoListWithSpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class NewMain {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Task05TodoListWithSpringBootApplication.class, args);
    }
}
