/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.userregistration;

import java.util.Scanner;

/**
 *
 * @author archi
 */
public class UserRegistration {

    public static void main(String[] args) {
        Login register = new Login();
        Scanner scan = new Scanner(System.in);
        
        System.out.println("\n=====Register=====");

        System.out.println("Please enter your first name: ");
        String firstName = scan.nextLine();

        System.out.println("Please enter your last name: ");
        String lastName = scan.nextLine();
        
        System.out.println("Please enter the username: ");
        String name = scan.nextLine();
        
        System.out.println("Enter password");
        String password = scan.nextLine();
        
        System.out.println("Enter cellphone number");
        String cellphone = scan.nextLine();
        
        while(true){
            if (!register.CheckUserName(name)){
                System.out.println("Please try again.");
                System.out.println("Please enter the username: ");
                name = scan.nextLine();
                continue; // Retry username validation.
            }
            if (!register.checkPasswordComplexity(password)){
                System.out.println("Please try again.");
                System.out.println("Enter password");
                password = scan.nextLine();
                continue; // Retry password validation
            }
            if (!register.checkCellPhoneNumber(cellphone)){
                System.out.println("Please try again.");
                System.out.println("Enter cellphone number");
                cellphone = scan.nextLine();
                continue; // Retry cellphone number validation
            }
            System.out.println(register.registrationUser(firstName, lastName, name,
                password, cellphone));
            break; // Exit the loop after successful registration.
        }

        // Ask the user to log in.
        System.out.println("\n=====Login=====");
        System.out.println("Please enter your username: ");
        String loginName = scan.nextLine();
        System.out.println("Please enter your password: ");
        String loginPassword = scan.nextLine();

        boolean loggedIn = register.LoginUser(loginName, loginPassword);
        System.out.println(register.returnLoginStatus(loggedIn));

        if (!loggedIn) {
            scan.close();
            return;
        }

        // Show the menu for the app only after login works.
        System.out.println("Welcome to QuickChat.");
        while (true) {
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.println("Please select an option (1, 2, or 3):");
           String option = scan.nextLine();

            switch (option) {
                case "1":
                    // Option 1 lets the user send a message.
                    handleSendMessages(scan);
                    break;
                case "2":
                    // Option 2 is not ready yet.
                    System.out.println("Coming Soon.");
                    break;
                case "3":
                    // Option 3 closes the app.
                    System.out.println("Goodbye!");
                    scan.close();
                    return;
                default:
                    System.out.println("Invalid option. Please choose 1, 2, or 3.");
                    break;
            }
        }
    }
    // This method lets the user send one or more messages.
    private static void handleSendMessages(Scanner scan) {
        int numberOfMessages;
        while (true) {
            System.out.print("How many messages would you like to send? ");
            try {
                numberOfMessages = Integer.parseInt(scan.nextLine().trim());
                if (numberOfMessages >= 0) {
                    break;
                }
            } catch (NumberFormatException ignored) {
                // Prompt again for invalid input.
            }
            System.out.println("Please enter a non-negative whole number.");
        }

        for (int i = 0; i < numberOfMessages; i++) {
            System.out.println("Enter recipient number:");
            String recipient = scan.nextLine();
            System.out.println("Enter message text:");
            String messageText = scan.nextLine();

            Message message = new Message(recipient, messageText);
            String recipientStatus = message.checkRecipientCell(recipient);
            String messageStatus = message.checkMessageLength(messageText);
            System.out.println(recipientStatus);
            System.out.println(messageStatus);

            if (!recipientStatus.equals("Cell phone number successfully captured.")
                    || messageStatus.startsWith("Message exceeds")) {
                continue;
            }

            System.out.println("1) Send Message");
            System.out.println("2) Disregard Message");
            System.out.println("3) Store Message");
            System.out.print("Choose an option: ");
            String selection = scan.nextLine().trim();
            String actionResult = message.SentMessage(selection);

            if (selection.equals("1") || selection.equalsIgnoreCase("send")
                    || selection.equalsIgnoreCase("send message")) {
                System.out.println(actionResult);
                message.setMessageHash(message.createMessageHash(
                        message.getMessageID(), message.getMessageNumber(), messageText));
                System.out.println("Message ID: " + message.getMessageID());
                System.out.println("Message Hash: " + message.getMessageHash());
                System.out.println("Recipient: " + message.getRecipient());
                System.out.println("Message: " + message.getMessageText());
                System.out.println("Total messages sent: " + Message.returnTotalMessages());
            } else if (selection.equals("2") || selection.equals("0")
                    || selection.equalsIgnoreCase("disregard")
                    || selection.equalsIgnoreCase("disregard message")) {
                System.out.println(actionResult);
                if (scan.nextLine().trim().equals("0")) {
                    System.out.println("Message deleted.");
                }
            } else if (selection.equals("3") || selection.equalsIgnoreCase("store")
                    || selection.equalsIgnoreCase("store message")) {
                System.out.println(message.storeMessage());
            } else {
                System.out.println(actionResult);
            }
        }
    }
}