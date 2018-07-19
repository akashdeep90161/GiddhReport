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
public class AccountDetailModel {
    String unique_name;
    String name;
    String creadit_Total;
    String debit_Total;
    String forwarded_balance;
    String closing_balance;
    String forwarded_type;
    String closing_type;

    public static AccountDetailModel fromAccountJson(JSONObject json_object) {
        AccountDetailModel gdm = new AccountDetailModel();
        try {
            gdm.unique_name = json_object.get("uniqueName").toString();

            gdm.name = json_object.get("name").toString();
        } catch (JSONException e) {

        }
        gdm.creadit_Total = json_object.get("creditTotal").toString();
        gdm.debit_Total = json_object.get("debitTotal").toString();
        gdm.forwarded_balance = ((JSONObject) json_object.get("openingBalance")).get("amount").toString();
        gdm.closing_balance = ((JSONObject) json_object.get("closingBalance")).get("amount").toString();
        if ((((JSONObject) json_object.get("openingBalance")).getString("type")).equals("DEBIT"))
            gdm.forwarded_type = "Dr";
        else
            gdm.forwarded_type = "Cr";
        if ((((JSONObject) json_object.get("closingBalance")).getString("type")).equals("DEBIT"))
            gdm.closing_type = "Dr";
        else
            gdm.closing_type = "Cr";
        // gdm.forwarded_type.equals("DEBIT")? gdm.forwarded_type="Dr":gdm.forwarded_type="Cr";
        return gdm;

    }


    public String getNameId() {
        return this.unique_name;
    }

    public String getName() {
        return this.name;
    }

    public String getOpeningBalance() {
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

    public String getOpeningType() {
        return this.forwarded_type;
    }

    public String getClosingType() {
        return this.closing_type;
    }
}
