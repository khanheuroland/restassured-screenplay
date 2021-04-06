package restassuredscreenplay.tasks;

import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;
import restassuredscreenplay.models.User;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class RegisterAUser implements Task {
    private User user;

    public RegisterAUser(User user)
    {
        this.user = user;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            Post.to("api/register")
                    .with(request->request.header("content-Type", "application/json")
                    .body(this.user))
        );
    }

    public static RegisterAUser with(User user)
    {
        return instrumented(RegisterAUser.class, user);
    }
}
