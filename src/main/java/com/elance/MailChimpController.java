package com.elance;

import com.elance.mailchimp.io.parsers.CsvContactsParser;
import com.elance.mailchimp.MailchimpUtil;
import com.elance.mailchimp.io.MessagesPrinter;
import com.elance.mailchimp.io.readers.ApiKeyReader;
import com.elance.mailchimp.io.utils.DataUtils;
import com.elance.mailchimp.io.utils.FileUtils;
import com.elance.mailchimp.structs.ContactData;
import com.elance.mailchimp.structs.ListResult;
import com.elance.mailchimp.structs.MyMemberInfoData;
import com.elance.mailchimp.ui.Menu;
import com.elance.mailchimp.ui.UserAction;

import java.util.List;
import java.util.Scanner;

public class MailChimpController {


    private static final String LIST_ID_ERROR = "id_error";
    private static final String CSV = ".csv";

    private static MessagesPrinter messagesPrinter = MessagesPrinter.getInstance();

	public static void main(String[] args){

        messagesPrinter.printMessage("Enter api key: ");
        String apikey = ApiKeyReader.readApiKey();
        MailchimpUtil mailchimpUtil = new MailchimpUtil(apikey);

        try{
            messagesPrinter.printMessage("Check api key: " + mailchimpUtil.ping());
        }catch (Exception e){
            messagesPrinter.printErrorMessage("invalid api key");
            return;
        }

        Menu menu = new Menu();

        while (true){
            UserAction userAction = menu.requestUserAction();

            switch (userAction) {
                case EXPORT:
                    exportContacts(mailchimpUtil);
                    break;
                case EXPORT_FROM_LIST:
                    exportContactsFromList(mailchimpUtil);
                    break;
                case IMPORT:
                    importContacts(mailchimpUtil, menu.getFilePath());
                    break;
                case EXIT:
                    return;
            }
        }


	}

    private static void exportContacts( MailchimpUtil mailchimpUtil){
        List<ContactData> contactDatas = mailchimpUtil.getAllContacts();
        List<String> stringList = DataUtils.generateListForRecordToFile(contactDatas);
        String title = FileUtils.getFileNameTimeStamp() + "_ALL_LISTS";
        FileUtils.writeToFile(stringList, title + CSV);
        printContacts(contactDatas);
        messagesPrinter.printMessage("Total: "+contactDatas.size()+" \n");
    }

    private static void exportContactsFromList( MailchimpUtil mailchimpUtil){
        String listId = chooseList(mailchimpUtil.getListsList());
        if(listId.equals(LIST_ID_ERROR)){
            messagesPrinter.printErrorMessage("No one list was created or incorrect list id.");
            return;
        }
        List<ContactData> contactDatas = mailchimpUtil.getContactsFromList(listId);
        List<String> stringList = DataUtils.generateListForRecordToFile(contactDatas);
        String title = FileUtils.getFileNameTimeStamp() + "_LIST_ID_"+listId;
        FileUtils.writeToFile(stringList, title + CSV);
        printContacts(contactDatas);
        messagesPrinter.printMessage("Total: "+contactDatas.size()+" \n");
    }

    private static void importContacts(MailchimpUtil mailchimpUtil, String path){
        String listId = chooseList(mailchimpUtil.getListsList());
        if(listId.equals(LIST_ID_ERROR)){
            messagesPrinter.printErrorMessage("No one list was created or incorrect list id.");
            return;
        }
        CsvContactsParser csvContactsParser = new CsvContactsParser(path);
        List<ContactData> contactDatas = csvContactsParser.getContacts();
        mailchimpUtil.addContacts(listId, contactDatas);
        messagesPrinter.printMessage("\n");
        messagesPrinter.printMessage("Success : " + contactDatas.size());
        printContacts(contactDatas);

        List<ContactData> incorrectContact = csvContactsParser.getIncorrectContacts();
        messagesPrinter.printMessage("\n");
        messagesPrinter.printMessage("Failure : " + incorrectContact.size());
        printContacts(incorrectContact);
    }

    /**
     *
     * @return list id
     */
    private static String chooseList(List<ListResult.Data> list){
        printLists(list);
        int index = readListNumber()-1;
        if(index >= list.size()){
            return LIST_ID_ERROR;
        }
        return list.get(index).id;
    }

    private static void printLists(List<ListResult.Data> list){
        int counter = 1;
        for (ListResult.Data listResult:list){
            System.out.println(counter+". "+listResult.name);
            counter++;
        }
    }

    private static int readListNumber(){
        messagesPrinter.printMessage("Enter list number");
        return new Scanner(System.in).nextInt();
    }

    private static void printContacts(List<ContactData> contactDatas){
        int counter = 1;
        for (ContactData contactData:contactDatas){
            messagesPrinter.printMessage("#"+counter+" =======================");
            messagesPrinter.printMessage("First name: "+contactData.FNAME);
            messagesPrinter.printMessage("Last name: "+contactData.LNAME);
            messagesPrinter.printMessage("Email: " + contactData.EMAIL);
            counter++;
        }
    }
}