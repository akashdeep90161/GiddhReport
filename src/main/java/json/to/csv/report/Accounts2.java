/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json.to.csv.report;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static json.to.csv.report.Test.close;

/**
 * @author Akash
 */
public class Accounts2 {
    static double creadit_Total;
    static double debit_Total;
    static double forwarded_balance;
    static double closing_balance;
    static String forwarded_type;
    static String closing_type;

    public static void main(String[] args) {
        FileWriter writer = null;
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Java Books");
            
       /*     writer = (new FileWriter("C:\\Users\\Akash\\Documents\\Accounts.csv"));
           writer.append("Name,Nsme_ID,OpeningBalance,CreaditTotal,DebitTotal,ClosingBalance\n");
           System.out.println("Testing"); */
            List<JSONObject> json_obj = Accounts.getAccountObject();
            for (int i = 0; i < json_obj.size(); i++) {
                JSONObject obj = json_obj.get(i);
                AccountDetailModel gdm = AccountDetailModel.fromAccountJson(obj);
                writer.append(gdm.getName() + "," + gdm.getNameId() + "," + gdm.getOpeningBalance() + " " + gdm.getOpeningType() + "," + gdm.getCreaditTotal() + "," + gdm.getDebitTotal() + "," + gdm.getClosingBalance() + " " + gdm.getClosingType() + "\n");
            }

            writer.append("Total" + "," + "," + Accounts.getTotalForwardedBalance() + " " + Accounts.getTotalForward() + "," + Accounts.getTotalCreadit() + "," + Accounts.getTotalDebit() + "," + Accounts.getTotalClosingBalance() + " " + Accounts.getTotalClosing() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(writer);
        }
    }

    public static List<JSONObject> getAccountObject() {
        List<JSONObject> json_obj_list = new ArrayList<>();
        JSONArray json_array = Test.getJsonArray();
        int l = json_array.length();
        for (int i = 0; i < l; i++) {
            JSONObject group_json_object = json_array.getJSONObject(i);
            JSONArray childGroup_array = group_json_object.getJSONArray("childGroups");
            int len = childGroup_array.length();
            for (int j = 0; j < len; j++) {

                JSONObject childGroup_json_object = childGroup_array.getJSONObject(j);
                try {
                    forwarded_balance += ((JSONObject) childGroup_json_object.get("forwardedBalance")).getDouble("amount");
                    forwarded_type = ((JSONObject) childGroup_json_object.get("forwardedBalance")).getString("type");
                    closing_balance += ((JSONObject) childGroup_json_object.get("closingBalance")).getDouble("amount");
                    closing_type = ((JSONObject) childGroup_json_object.get("closingBalance")).getString("type");
                    creadit_Total += childGroup_json_object.getDouble("creditTotal");
                    debit_Total += childGroup_json_object.getDouble("debitTotal");
                } catch (JSONException e) {
                }
                JSONArray account_array = childGroup_json_object.getJSONArray("accounts");
                for (int k = 0; k < account_array.length(); k++) {
                    JSONObject obj = account_array.getJSONObject(k);
                    json_obj_list.add(obj);
                }
            }
        }
        return json_obj_list;
    }

    public static double getTotalForwardedBalance() {
        return forwarded_balance;
    }

    public static double getTotalClosingBalance() {
        return closing_balance;
    }

    public static double getTotalCreadit() {
        return creadit_Total;
    }

    public static double getTotalDebit() {
        return debit_Total;
    }

    public static String getTotalForward() {
        return forwarded_type;
    }

    public static String getTotalClosing() {
        return closing_type;
    }
}
