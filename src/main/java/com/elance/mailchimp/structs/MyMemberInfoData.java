package com.elance.mailchimp.structs;

import com.ecwid.mailchimp.MailChimpObject;

/**
 * Created by Sergiy on 02-Oct-14.
 */
public class MyMemberInfoData extends MailChimpObject{

        @Field
        public String email;

        @Field
        public MyMemberInfoMerges merges;

    }