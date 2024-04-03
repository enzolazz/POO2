package com.iluwatar.abstractfactory;

public class DwarfWizard implements Wizard {

    static final String DESCRIPTION = "This is the dwarf wizard!";

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}