package com.elance.mailchimp;

import com.ecwid.mailchimp.MailChimpClient;
import com.ecwid.mailchimp.MailChimpException;
import com.ecwid.mailchimp.MailChimpMethod;
import com.ecwid.mailchimp.method.v2_0.lists.Email;
import com.ecwid.mailchimp.method.v2_0.lists.ListsRelatedMethod;
import com.ecwid.mailchimp.method.v2_0.lists.SubscribeMethod;
import com.elance.mailchimp.io.MessagesPrinter;
import com.elance.mailchimp.methods.PingMethod;
import com.elance.mailchimp.structs.*;
import com.elance.mailchimp.methods.ListMethod;
import com.elance.mailchimp.methods.MembersMethod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergiy on 01-Oct-14.
 */
public class MailchimpUtil {

    private String apiKey;
    private MailChimpClient mailChimpClient;

    public MailchimpUtil(){
        mailChimpClient = new MailChimpClient();
    }

    public MailchimpUtil(String apiKey){
        this();
        this.apiKey = apiKey;
    }

    public String ping(){
        PingMethod pingMethod = new PingMethod();
        pingMethod.apikey = this.apiKey;
        return ((PingResult)execute(pingMethod)).msg;
    }


    public void addNewContact(String listId, String firstName, String lastName, String email){
        SubscribeMethod  subscribeMethod = new SubscribeMethod();
        subscribeMethod.apikey = this.apiKey;
        subscribeMethod.id = listId;
        subscribeMethod.email = new Email();
        subscribeMethod.email.email = email;
        subscribeMethod.double_optin = false;
        subscribeMethod.update_existing = true;
        subscribeMethod.merge_vars = new ContactData(email, firstName, lastName);

        execute(subscribeMethod);
    }

    public void addContacts(String listId, List<ContactData> datas){
        for (ContactData contactData:datas){
            addNewContact(listId, contactData);
        }
    }

    public void addNewContact(String listId, ContactData mergeVars){
        addNewContact(listId, mergeVars.FNAME, mergeVars.LNAME, mergeVars.EMAIL);
    }

    public List<ContactData> getContactsFromList(String listId){
        MembersMethod membersMethod = new MembersMethod();
        membersMethod.id = listId;
        membersMethod.apikey = apiKey;
        MembersResult membersResult = (MembersResult) execute(membersMethod);
        return convertToContactDataList(membersResult.data);
    }

    public List<ListResult.Data> getListsList(){
        ListMethod listMethod = new ListMethod();
        listMethod.apikey = apiKey;

        ListResult listResult = (ListResult) execute(listMethod);

        return listResult.data;
    }

    public List<ContactData> getAllContacts(){
        List<ContactData> memberInfoDataList = new ArrayList<>();

        for (ListResult.Data listId: getListsList()){
            memberInfoDataList.addAll(getContactsFromList(listId.id));
        }

        return memberInfoDataList;
    }


    private Object execute(MailChimpMethod<?> method){
        MailChimpClient mailChimpClient = new MailChimpClient();
        try {
            return mailChimpClient.execute(method);
        } catch (IOException e) {
            MessagesPrinter.getInstance().printErrorMessage("Data reading error. Check your Internet connection");
        } catch (MailChimpException e) {
            MessagesPrinter.getInstance().printErrorMessage(e.getLocalizedMessage());
        }finally {
            mailChimpClient.close();
        }
        return null;
    }

    private ContactData convertMyMemberInfoDataToContactData(MyMemberInfoData myMemberInfoData){
        ContactData contactData = new ContactData();
        contactData.EMAIL = myMemberInfoData.email;
        contactData.FNAME = myMemberInfoData.merges.FNAME;
        contactData.LNAME = myMemberInfoData.merges.LNAME;
        return contactData;
    }

    private List<ContactData> convertToContactDataList(List<MyMemberInfoData> list){
        List<ContactData> resultList = new ArrayList<>();
        for (MyMemberInfoData myMemberInfoData:list){
            resultList.add(convertMyMemberInfoDataToContactData(myMemberInfoData));
        }
        return resultList;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

}
