package courierPackage;

public class InputDataPassword {
    private String password;

    public InputDataPassword(String password) {
        this.password = password;
    }
    public static InputDataPassword fromPassword(Courier courier){
        return  new InputDataPassword(courier.getPassword());
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}