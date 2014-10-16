package com.elance.mailchimp.structs;

import com.ecwid.mailchimp.MailChimpObject;

import java.util.List;

/**
 * Created by Sergiy on 01-Oct-14.
 */
public class MembersResult extends MailChimpObject {
    @Field
    public Integer total;

    @Field
    public List<MyMemberInfoData> data;
}
