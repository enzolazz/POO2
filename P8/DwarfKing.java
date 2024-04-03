package com.iluwatar.abstractfactory;

/**
 * DwarfKing.
 */
public class DwarfKing implements King {

  static final String DESCRIPTION = "This is the dwarf king!";

  @Override
  public String getDescription() {
    return DESCRIPTION;
  }
}
