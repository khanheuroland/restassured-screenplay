package restassuredscreenplay.models;

public class User {
    public int id;
    public String email;
    public String password;
    public String first_name;
    public String last_name;
    public String avatar;

    public User(String email, String password)
    {
        this.email = email;
        this.password = password;
    }

    public static UserBuilder withEmail(String email)
    {
        return new UserBuilder(email);
    }

    public static class UserBuilder
    {
        private String email;
        private String password;

        public UserBuilder(String email)
        {
            this.email = email;
        }

        public UserBuilder andPassword(String password)
        {
            this.password = password;
            return this;
        }

        public User build()
        {
            return new User(this.email, this.password);
        }
    }
}
