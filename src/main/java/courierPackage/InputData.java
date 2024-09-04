package courierPackage;

public class InputData {
    private String login;
    public String password;

    public InputData(String login,String password) {
        this.login = login;
        this.password = password;
    }

    public static InputData from(Courier courier) {
        return new InputData(courier.getLogin(),courier.getPassword());
    }
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}