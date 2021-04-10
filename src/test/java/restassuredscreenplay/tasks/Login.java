package restassuredscreenplay.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;
import net.thucydides.core.annotations.Step;
import restassuredscreenplay.models.User;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class Login implements Task {
    public static LoginBuilder withUserName(String username) {
        return new LoginBuilder(username);
    }

    private String username;
    private String password;

    public Login(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    @Override
    @Step("{0} attempt to login with #username and #password")
    public <T extends Actor> void performAs(T actor) {
        //Do actions
        actor.attemptsTo(
                Post.to("api/login").with(request->request.contentType("application/json")
                .body(User.withEmail(this.username).andPassword(this.password).build()))
        );
    }

    public static class LoginBuilder
    {
        private String username;
        public LoginBuilder(String username)
        {
            this.username = username;
        }

        public Performable andPassword(String password) {
            return instrumented(Login.class, this.username, password);
        }
    }
}
