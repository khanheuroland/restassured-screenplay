package restassuredscreenplay.features.search;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.util.EnvironmentVariables;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import restassuredscreenplay.models.User;
import restassuredscreenplay.tasks.FindAUser;
import restassuredscreenplay.tasks.RegisterAUser;
import restassuredscreenplay.tasks.UserTasks;

import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;

@RunWith(SerenityRunner.class)
public class UserStory {
    private String theRestApiBaseUrl;
    private EnvironmentVariables environmentVariables;
    private Actor anna;
    @Before
    public void configureBaseUrl() {
        theRestApiBaseUrl=environmentVariables.optionalProperty("restapi.baseurl")
                .orElse("https://reqres.in/");
        anna = Actor.named("Anna the supervisor").whoCan(CallAnApi.at(theRestApiBaseUrl));
    }

    @Test
    public void find_an_individual_user() {
        anna.attemptsTo(
                FindAUser.withId(2)
        );

        anna.should(
                seeThatResponse("User detail should be correct",
                        response->response.statusCode(200)
                                .body("data.first_name", equalTo("Janet"))
                                .body("data.last_name", equalTo("Weaver"))
                )
        );
        /*
        User user = SerenityRest.lastResponse()
                .jsonPath()
                .getObject("data", User.class);

        assertThat(user.first_name).isEqualTo("Janet");
        assertThat(user.last_name).isEqualTo("Weaver");
        */
    }

    @Test
    public void retrieve_all_user()
    {
        anna.attemptsTo(
                UserTasks.listAllUsers()
        );

        anna.should(
                seeThatResponse("all the expected users should be returned",
                        response->response.statusCode(200)
                        .body("data.first_name", hasItems("George", "Janet", "Emma"))
                )
        );
    }

    @Test
    public void retrieve_an_token_after_register_a_new_user()
    {
        User newUser = User.withEmail("eve.holt@reqres.in").andPassword("pistol").build();
        anna.attemptsTo(RegisterAUser.with(newUser));

        anna.should(
                seeThatResponse("The created ID and token returned from server",
                        response->response.statusCode(200)
                        .body("id", is(4))
                        .body("token", not(isEmptyString()))
                )
        );
    }
}