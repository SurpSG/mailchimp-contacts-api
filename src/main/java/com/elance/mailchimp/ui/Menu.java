package com.elance.mailchimp.ui;


import com.elance.mailchimp.io.MessagesPrinter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Menu {
    private static final int[] ACTIONS = {1,2,3,4};

    public UserAction requestUserAction() {

        MessagesPrinter messagesPrinter = MessagesPrinter.getInstance();

        messagesPrinter.printMessage("Enter the action here : ");
        messagesPrinter.printMessage("1 - export all contacts to csv ");
        messagesPrinter.printMessage("2 - export all contacts from list to csv ");
        messagesPrinter.printMessage("3 - import contacts from csv ");
        messagesPrinter.printMessage("4 - exit ");
        Integer action = 0;
        try{
            BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
            String s = bufferRead.readLine();
            try{
            action = Integer.parseInt(s);
            } catch (NumberFormatException e) {
                requestUserAction();
            }
             UserAction userAction = UserAction.getActionFromIndex(action);
            if (userAction!=null) {
                return userAction;
            } else {
                requestUserAction();
            }
        }
        catch(IOException e)
        {
            messagesPrinter.printErrorMessage("Invalid input parameters... the application now will be terminated");
        }
        return null;
    }

    public String getFilePath() {
        String path = "";
        MessagesPrinter messagesPrinter = MessagesPrinter.getInstance();
        messagesPrinter.printMessage("Please enter the file path");
        try{
            BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
            path = bufferRead.readLine();
        }
        catch(IOException e)
        {
            messagesPrinter.printErrorMessage("Invalid input parameters... the application now will be terminated");
        }
        return path;
    }
}
