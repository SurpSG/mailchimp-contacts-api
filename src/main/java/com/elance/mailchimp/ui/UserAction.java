package com.elance.mailchimp.ui;


public enum UserAction {
    EXPORT(1, "Export"),
    EXPORT_FROM_LIST(2, "ExportFromList"),
    IMPORT(3,"Import"),
    EXIT(4, "Exit");
    private int number;
    private String name;

    private UserAction(int number, String name) {
        this.number = number;
        this.name = name;
    }
    public static UserAction getActionFromIndex(Integer index) {
        for (UserAction userAction:values()) {
            if (userAction.number == index) {
                return userAction;
            }
        }
        return null;
    }

}
