package com.elance.mailchimp.io;

/**
 * Created by Sergiy on 02-Oct-14.
 */
public class MessagesPrinter {

    private static MessagesPrinter messagesPrinter = new MessagesPrinter();

    private MessagesPrinter(){}

    public static MessagesPrinter getInstance(){
        return messagesPrinter;
    }

    public void printMessage(String message){
        System.out.println(message);
    }

    public void printErrorMessage(String message){
        System.err.println(message);
    }
}
