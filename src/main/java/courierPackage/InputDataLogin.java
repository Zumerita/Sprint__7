package courierPackage;

public class InputDataLogin {
    private String login;
    private String password;

    public InputDataLogin(String login,String password) {
        this.password = password;
        this.login = login;
    }
    public static InputDataLogin fromLogin(Courier courier){
        return  new InputDataLogin(courier.getLogin(), "");
    }
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}