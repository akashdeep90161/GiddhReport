/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json.to.csv.report;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Akash
 */
public class GroupDetailsModel {
    String unique_name;
    String category;
    String group_name;
    String creadit_Total;
    String debit_Total;
    String forwarded_balance;
    String closing_balance;
    String forwarded_type;
    String closing_type;

    public static GroupDetailsModel fromJson(JSONObject json_object) {
        GroupDetailsModel gdm = new GroupDetailsModel();
        try {
            gdm.unique_name = json_object.get("uniqueName").toString();
            gdm.category = json_object.get("category").toString();
            gdm.group_name = json_object.get("groupName").toString();
        } catch (JSONException e) {

        }
        gdm.creadit_Total = json_object.get("creditTotal").toString();
        gdm.debit_Total = json_object.get("debitTotal").toString();
        gdm.closing_balance = ((JSONObject) json_object.get("closingBalance")).get("amount").toString();


        try {
            gdm.forwarded_balance = ((JSONObject) json_object.get("forwardedBalance")).get("amount").toString();
            if ((((JSONObject) json_object.get("forwardedBalance")).getString("type")).equals("DEBIT"))
                gdm.forwarded_type = "Dr";
            else
                gdm.forwarded_type = "Cr";
        } catch (JSONException e) {
            gdm.forwarded_balance = ((JSONObject) json_object.get("openingBalance")).get("amount").toString();
            if ((((JSONObject) json_object.get("openingBalance")).getString("type")).equals("DEBIT"))
                gdm.forwarded_type = "Dr";
            else
                gdm.forwarded_type = "Cr";
        }
        if ((((JSONObject) json_object.get("closingBalance")).getString("type")).equals("DEBIT"))
            gdm.closing_type = "Dr";
        else
            gdm.closing_type = "Cr";
        // gdm.forwarded_type.equals("DEBIT")? gdm.forwarded_type="Dr":gdm.forwarded_type="Cr";
        return gdm;

    }


    public String getName() {
        return this.unique_name;
    }

    public String getGroupName() {
        return this.group_name;
    }

    public String getCategory() {
        return this.category;
    }

    public String getForwardedBalance() {
        return this.forwarded_balance;
    }

    public String getClosingBalance() {
        return this.closing_balance;
    }

    public String getCreaditTotal() {
        return this.creadit_Total;
    }

    public String getDebitTotal() {
        return this.debit_Total;
    }

    public String getForwardType() {
        return this.forwarded_type;
    }

    public String getClosingType() {
        return this.closing_type;
    }
}