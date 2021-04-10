package restassuredscreenplay.features;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import restassuredscreenplay.tasks.Login;

import static com.sun.org.apache.xerces.internal.util.FeatureState.is;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SerenityRunner.class)
public class LoginStory {
    private Actor anna;

    @Before
    public void setup()
    {
        //Define ability for actor
        anna = Actor.named("Anna").whoCan(CallAnApi.at("https://reqres.in/"));
    }

    @Test
    public void should_show_token_returned_from_server_with_valid_username_and_password()
    {
        //Task for anna
        anna.attemptsTo(
                Login.withUserName("eve.holt@reqres.in").andPassword("cityslicka")
        );

        //Question
        anna.should(
                seeThatResponse("The token returned from server",
                        response->response.statusCode(200)
                                            .body("token", equalTo("QpwL5tke4Pnpja7X4"))
                )
        );
    }
}
