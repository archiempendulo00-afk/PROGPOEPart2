/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.userregistration;

/**
 *
 * @author archi
 */
public class Registration {// Building the class for the main method PoePartOne
    String firstName;
    String lastName;
    String userName;
    String passWord;
    String cellPhone;
    
    public boolean CheckUserName(String username){
        if (username != null && username.contains("_") && username.length() <= 5){
            System.out.println("Username successfully captured");
            return true;
        }else{
            System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length");
            return false;
        }
    }
    public boolean checkPasswordComplexity(String password){
        if (password != null && password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*\\d.*") &&
                password.matches(".*[^a-zA-Z0-9].*")){
            System.out.println("Password successfully captured");
            return true;
        }else{
            System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character");
            return false;
        }
    }//end of password method
    public boolean checkCellPhoneNumber(String cellphone){
        if (cellphone != null && cellphone.matches("\\+27[0-9]{9}")){
            System.out.println("Cell number successfully captured");
            return true;
        }else{
            System.out.println("Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.");
            return false;
        }
    }//end of cellphone method
    public String registrationUser(String username, 
            String password,
            String cellphone){
        return registrationUser("", "", username, password, cellphone);
        }

        public String registrationUser(String firstName,
            String lastName,
            String username,
            String password,
            String cellphone){
        if (CheckUserName(username)&&
                checkPasswordComplexity(password)&&
                checkCellPhoneNumber(cellphone)){
            this.firstName = firstName;
            this.lastName = lastName;
            this.userName = username;
            this.passWord = password;
            this.cellPhone = cellphone;
            
            return"Register was successful";
        }else{
            return"Register was unsuccessful";
        }
    }//end of registerUser method
    
    public boolean registerStatus(String username, String password, String cellphone){
        return CheckUserName(username) && 
                checkPasswordComplexity(password) && 
                checkCellPhoneNumber(cellphone);
    }    
    
}//end of class
