package com.iluwatar.abstractfactory;

public class OrcWizard implements Wizard {

    static final String DESCRIPTION = "This is the orc wizard!";

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}