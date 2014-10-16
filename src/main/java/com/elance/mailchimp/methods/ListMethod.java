package com.elance.mailchimp.methods;

import com.ecwid.mailchimp.MailChimpAPIVersion;
import com.ecwid.mailchimp.MailChimpMethod;
import com.ecwid.mailchimp.method.v2_0.lists.ListsRelatedMethod;
import com.elance.mailchimp.structs.ListResult;

/**
 * Created by Sergiy on 01-Oct-14.
 */
@MailChimpMethod.Method(name = "/lists/list", version = MailChimpAPIVersion.v2_0)
public class ListMethod  extends ListsRelatedMethod<ListResult> {

}
