package com.elance.mailchimp.methods;

import com.ecwid.mailchimp.MailChimpAPIVersion;
import com.ecwid.mailchimp.MailChimpMethod;
import com.ecwid.mailchimp.method.v2_0.lists.ListsRelatedMethod;
import com.elance.mailchimp.structs.ListResult;
import com.elance.mailchimp.structs.PingResult;

/**
 * Created by Sergiy on 01-Oct-14.
 */
@MailChimpMethod.Method(name = "/helper/ping", version = MailChimpAPIVersion.v2_0)
public class PingMethod extends MailChimpMethod<PingResult> {
}
