/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.userregistration;

/**
 *
 * @author archi
 */
public class Login extends Registration{

    @Override // Uses the inherited username validation rules.
    public boolean CheckUserName(String username){
        return super.CheckUserName(username); // Super calls the parent class method to perform the registration.
    }

    @Override // Uses the inherited password validation rules.
    public boolean checkPasswordComplexity(String password){
        return super.checkPasswordComplexity(password);
    }

    @Override // Uses the inherited cellphone validation rules.
    public boolean checkCellPhoneNumber(String cellphone){
        return super.checkCellPhoneNumber(cellphone);
    }

    public String registrationUser(String username, String password, String cellphone){
        
        return super.registrationUser(username, password, cellphone);
    }

    public String registerUser(String firstName, String lastName,
            String username, String password, String cellphone){
        return registrationUser(firstName, lastName, username, password, cellphone);
    }
    
    public boolean LoginUser(String name,String password){
        return name != null && password != null // Null checks to ensure login values exist before comparing them.
                && name.equals(userName) && password.equals(passWord);
    }

    public boolean loginUser(String name, String password){
        return LoginUser(name, password);
    }
    public String returnLoginStatus(boolean LoginStatus){
        if(LoginStatus){
            return "Welcome " + firstName + " " + lastName
                    + " it is great to see you again.";
        }else{
            return "Username or password incorrect, please try again.";
        }
    }
}
    

