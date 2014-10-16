package com.elance.mailchimp.io.utils;

import com.elance.mailchimp.structs.ContactData;
import com.elance.mailchimp.structs.MyMemberInfoData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergiy on 02-Oct-14.
 */
public class DataUtils {

    private static final String DELIM = ",";

    private static final String[] HEADER = {
            "first name",
            "last name",
            "email",
    };

    public static List<String> generateListForRecordToFile(List<ContactData> contacts) {
        List<String> data = new ArrayList<>();
        data.add(getHeader());
        data.addAll(convertToStringList(contacts));
        return data;
    }

    private static List<String> convertToStringList(List<ContactData> contacts) {
        List<String> data = new ArrayList<>();

        for (ContactData contactData : contacts) {
            String row = String.format(contactData.FNAME + DELIM
                    + contactData.LNAME + DELIM
                    + contactData.EMAIL);
            data.add(row);
        }
        return data;
    }

    private static String getHeader() {
        String header = "";
        for (String string : HEADER) {
            header += String.format("%s%s", string, DELIM);
        }
        return header;
    }
}
