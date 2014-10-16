package com.elance.mailchimp.structs;

import com.ecwid.mailchimp.MailChimpObject;

/**
 * Created by Sergiy on 01-Oct-14.
 */
public class ContactData extends MailChimpObject {

    @Field
    public String EMAIL, FNAME, LNAME;

    public ContactData(){}

    public ContactData(String email, String fname, String lname) {
        this.EMAIL = email;
        this.FNAME = fname;
        this.LNAME = lname;
    }
}