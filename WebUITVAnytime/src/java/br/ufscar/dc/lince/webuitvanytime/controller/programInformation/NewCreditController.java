package br.ufscar.dc.lince.webuitvanytime.controller.programInformation;

import bbc.rd.tvanytime.TVAnytimeException;
import bbc.rd.tvanytime.creditsInformation.Character;
import bbc.rd.tvanytime.creditsInformation.*;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.api.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Label;
import br.ufscar.dc.lince.tvanytime.core.TVAnytimeGenreToolboxExtended;

/**
 *
 * @author lince
 */
public class NewCreditController extends Window {

    public void addPerson() {
        Textbox tbNewPersonGivenName = (Textbox) getFellow("tbPersonName").clone();
        Textbox tbNewPersonFamilyName = (Textbox) getFellow("tbPersonFamilyname").clone();
        Listbox lbPerson = (Listbox) getFellow("lbPerson");
        Integer index = (lbPerson.getItemCount());

        Listitem li = new Listitem();
        Listcell cell1 = new Listcell();
        Listcell cell2 = new Listcell();
        Listcell cell3 = new Listcell();
        cell1.appendChild(new Label(new Integer(index).toString()));
        cell2.appendChild(new Label(tbNewPersonGivenName.getValue()));
        cell3.appendChild(new Label(tbNewPersonFamilyName.getValue()));
        li.appendChild(cell1);
        li.appendChild(cell2);
        li.appendChild(cell3);
        li.setParent(lbPerson);

    }

    public void removePerson() {
        Listbox lbPerson = (Listbox) getFellow("lbPerson");
        lbPerson.removeItemAtApi(lbPerson.getSelectedIndex());
        Button btRemovePerson = (Button) getFellow("btRemovePerson");
        btRemovePerson.setDisabled(true);
    }

    public void selectPerson() {
        Listbox lbPerson = (Listbox) getFellow("lbPerson");
        Button btRemovePerson = (Button) getFellow("btRemovePerson");
        if (lbPerson.getSelectedCount() > 0) {
            btRemovePerson.setDisabled(false);
        } else {
            btRemovePerson.setDisabled(true);
        }
    }

    public void addCharacter() {

        Textbox tbCharacterName = (Textbox) getFellow("tbCharacterName").clone();
        Textbox tbCharacterFamilyName = (Textbox) getFellow("tbCharacterFamilyname").clone();
        Listbox lbCharacter = (Listbox) getFellow("lbCharacter");
        Integer index = (lbCharacter.getItemCount());

        Listitem li = new Listitem();
        Listcell cell1 = new Listcell();
        Listcell cell2 = new Listcell();
        Listcell cell3 = new Listcell();
        cell1.appendChild(new Label(new Integer(index).toString()));
        cell2.appendChild(new Label(tbCharacterName.getValue()));
        cell3.appendChild(new Label(tbCharacterFamilyName.getValue()));
        li.appendChild(cell1);
        li.appendChild(cell2);
        li.appendChild(cell3);
        li.setParent(lbCharacter);
    }

    public void removeCharacter() {
        Listbox lbCharacter = (Listbox) getFellow("lbCharacter");
        lbCharacter.removeItemAtApi(lbCharacter.getSelectedIndex());
        Button btRemoveCharacter = (Button) getFellow("btRemoveCharacter");
        btRemoveCharacter.setDisabled(true);
    }

    public void selectCharacter() {
        Listbox lbCharacter = (Listbox) getFellow("lbCharacter");
        Button btRemoveCharacter = (Button) getFellow("btRemoveCharacter");
        if (lbCharacter.getSelectedCount() > 0) {
            btRemoveCharacter.setDisabled(false);
        } else {
            btRemoveCharacter.setDisabled(true);
        }
    }

    public void addOrganization() {
        Textbox tbOrganization = (Textbox) getFellow("tbOrganization").clone();
        Listbox lbOrganization = (Listbox) getFellow("lbOrganization");
        Integer index = (lbOrganization.getItemCount());

        tbOrganization.setId(tbOrganization.getId() + "_" + index.toString());

        Listitem li = new Listitem();
        Listcell cell1 = new Listcell();
        Listcell cell2 = new Listcell();
        cell1.appendChild(new Label(new Integer(index).toString()));
        cell2.appendChild(new Label(tbOrganization.getValue()));
        li.appendChild(cell1);
        li.appendChild(cell2);
        li.setParent(lbOrganization);
    }

    public void removeOrganization() {
        Listbox lbOrganization = (Listbox) getFellow("lbOrganization");
        lbOrganization.removeItemAtApi(lbOrganization.getSelectedIndex());
        Button btRemoveOrganization = (Button) getFellow("btRemoveOrganization");
        btRemoveOrganization.setDisabled(true);
    }

    public void selectOrganization() {
        Listbox lbOrganization = (Listbox) getFellow("lbOrganization");
        Button btRemoveOrganization = (Button) getFellow("btRemoveOrganization");
        if (lbOrganization.getSelectedCount() > 0) {
            btRemoveOrganization.setDisabled(false);
        } else {
            btRemoveOrganization.setDisabled(true);
        }
    }

    /**
     * Constructs the Credit-object out of the information of the controlls of this window
     * The information gets added to the listbox-component lbCredits of the
     */
    public void saveCredit() {

        CreditsItem credit = new CreditsItem();

        Listbox lbPerson = (Listbox) getFellow("lbPerson");
        Listbox lbCharacter = (Listbox) getFellow("lbCharacter");
        Listbox lbOrganization = (Listbox) getFellow("lbOrganization");

        /**
         * Collection the Information of the persons
         */
        // The items of the listbox start with an index of 1 !!!!
        // The element lbPerson.getItemAtIndex(0) exists, but its empty
        // listboxItem[]={[]} = this information is important for all listboxes
        for (int i = 1; i < lbPerson.getItemCount(); i++) {
            Listitem it = (Listitem) lbPerson.getItemAtIndex(i);
            List children = it.getChildren();
            PersonName pers = new PersonName();

            pers.setGivenName(new Name(((Label) ((Listcell) children.get(1)).getFirstChild()).getValue()));
            pers.setFamilyName(new Name(((Label) ((Listcell) children.get(2)).getFirstChild()).getValue()));
            credit.addPersonName(pers);
        }

        /**
         * Collection the Information of the characters
         */
        for (int i = 1; i < lbCharacter.getItemCount(); i++) {
            Listitem it = (Listitem) lbCharacter.getItemAtIndex(i);
            List children = it.getChildren();
            Character character = new Character();

            character.setGivenName(new Name(((Label) ((Listcell) children.get(1)).getFirstChild()).getValue()));
            character.setFamilyName(new Name(((Label) ((Listcell) children.get(2)).getFirstChild()).getValue()));
            credit.addCharacter(character);
        }

        /**
         * Collection the Information of the organizations
         */
        for (int i = 1; i < lbOrganization.getItemCount(); i++) {
            Listitem it = (Listitem) lbOrganization.getItemAtIndex(i);
            List children = it.getChildren();
            OrganizationName orgName = new OrganizationName();

            orgName.setOrganizationName(((Label) ((Listcell) children.get(1)).getFirstChild()).getValue());
            credit.addOrganizationName(orgName);
        }

        /**
         * Getting the data of the role information
         */
        Combobox cbRolePrefix = (Combobox) getFellow("cbRolePrefix");
        Textbox tbRoleName = (Textbox) getFellow("tbRoleName");

        // if there is no prefix selected, then the role is not added
        if (cbRolePrefix.getSelectedItem() != null) {
            try {
                // build the role-string: roleprefix + rolename
                credit.setRole(cbRolePrefix.getSelectedItem().getLabel() + tbRoleName.getValue());
            } catch (TVAnytimeException ex) {
                Logger.getLogger(NewCreditController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        // With the Desktop-object, there is access to the page, and width the page, there is access
        // to the windows of this namespace
        // The name of the page of this application is "WebUITVAnytime"
        // All windows (or subspaces) of this application lie within this page
        //
        // Pagename = "WebUITVAnytime"
        //
        // For a zul-site to lie within a page there has to be the following
        // tag in the beginnig of the page :
        // <?page id="WebUITVAnytime"?>
        Listbox lbCreditItems = (Listbox) getDesktop().getPage("WebUITVAnytime").getFellow("winProgramInformation").getFellow("lbCredits");

        int index = lbCreditItems.getItemCount();
        Listitem li = new Listitem();
        Listcell c1 = new Listcell();
        Listcell c2 = new Listcell();        

        Label lbNumber = new Label();
        Label lbCreditItem = new Label();
        
        lbNumber.setValue(Integer.toString(index));
        lbCreditItem.setValue(credit.getRole());

        c1.appendChild(lbNumber);
        c2.appendChild(lbCreditItem);

        li.setAttribute("creditItem", credit);
        li.appendChild(c1);
        li.appendChild(c2);
        li.setParent(lbCreditItems);        

        // Close the window
        Window winNewCredit = (Window) getFellow("winNewCredit");
        winNewCredit.detach();
    }

    /**
     * Method to close the modal window
     */
    public void cancelCredit() {

        Window winNewCredit = (Window) getFellow("win_newCredit");
        winNewCredit.detach();
    }
}
