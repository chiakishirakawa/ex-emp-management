package com.example.form;

public class LoginForm {
    /**メールアドレス */
    private String mailAString;
    /**パスワード */
    private String password;
    public String getMailAString() {
        return mailAString;
    }
    public void setMailAString(String mailAString) {
        this.mailAString = mailAString;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public String toString() {
        return "LoginForm [mailAString=" + mailAString + ", password=" + "****" + "]";
    }
    


}
