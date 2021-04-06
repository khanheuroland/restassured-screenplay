package restassuredscreenplay.tasks;


import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;

public class UserTasks {
    public static Task listAllUsers(){
        return Task.where("{0} list all users",
                Get.resource("api/users")
        );
    }
}
