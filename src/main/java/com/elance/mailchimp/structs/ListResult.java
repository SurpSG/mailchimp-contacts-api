package com.elance.mailchimp.structs;

import com.ecwid.mailchimp.MailChimpObject;

import java.util.List;

/**
 * Created by Sergiy on 01-Oct-14.
 */
public class ListResult extends MailChimpObject {
    @Field
    public Integer total;

    @Field
    public List<Data> data;

    public static class Data extends MailChimpObject{
        @Field
        public String id;

        @Field
        public String name;
    }
}
