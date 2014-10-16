package com.elance.mailchimp.io.readers;

import java.util.Scanner;

/**
 * Created by Sergiy on 02-Oct-14.
 */
public class ApiKeyReader {

    public static String readApiKey(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
