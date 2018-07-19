/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json.to.csv.report;


import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;


/**
 * @author Akash
 */
public class Test {

    public static void main(String[] args) {
        JSONArray json_array = Test.getJsonArray();
        JSONObject json_obj2 = Test.getJsonObject();
        GroupDetailsModel gdm2 = GroupDetailsModel.fromJson(json_obj2);
        // System.out.println(json_array.getJSONObject(0).get("uniqueName"));
        FileWriter writer = null;
        try {

            writer = (new FileWriter("/home/akash/Desktop/Akash.csv"));
            writer.append("Name,Category,GroupsName,OpeningBalance,CreaditTotal,DebitTotal,ClosingBalance\n");
            System.out.println("Testing");


            int l = json_array.length();
            for (int i = 0; i < l; i++) {
                JSONObject json_object = json_array.getJSONObject(i);
                GroupDetailsModel gdm = GroupDetailsModel.fromJson(json_object);
                writer.append(gdm.getName() + "," + gdm.getCategory() + "," + gdm.getGroupName() + "," + gdm.getForwardedBalance() + " " + gdm.getForwardType() + "," + gdm.getCreaditTotal() + "," + gdm.getDebitTotal() + "," + gdm.getClosingBalance() + " " + gdm.getClosingType() + "\n");
            }
            writer.append("Total" + "," + "" + "," + "," + gdm2.getForwardedBalance() + gdm2.getForwardType() + "," + gdm2.getCreaditTotal() + "," + gdm2.getDebitTotal() + "," + gdm2.getClosingBalance() + gdm2.getClosingType() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(writer);
        }
    } 
     
   /*   List<Map<String, String>> list_map=jf.handleAsArray(s);
      for(int i=0;i<list_map.size();i++)
        {
            Map<String,String> m=list_map.get(i);
            
        }
     }*/
     
 /*  JsonFlattener parser = new JsonFlattener();
        CSVWriter writer = new CSVWriter();
        try{
         List<Map<String, String>> flatJson = parser.handleAsArray(s);
          writer.writeAsCSV(flatJson, "C:\\Users\\Akash\\Documents\\Akash5.csv");
        }
      catch(Exception e)
      {
          System.out.println(e);
      }*/


    //   JSONArray obj_jsonarray=new JSONArray(s);
    //  JSONObject obj_json=new JSONObject(s);
    //  System.out.println(obj_json.get("groupDetails").toString());
    // JSONArray obj_jsonarray=obj_json.getJSONArray("groupDetails");


    public static void close(FileWriter writer) {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JSONArray getJsonArray() {
        Response response = JsonToCsvApi.getResponse();
        String response_string = "";
        String s = "";
        try {
            InputStream instream = response.body().byteStream();
            response_string += convertStreamToString(instream);
            // System.out.println(response_string);
            s += response_string.substring(response_string.indexOf('['), response_string.lastIndexOf(']') + 1);
            //System.out.println(s);
            instream.close();
        } catch (Exception e) {
            System.out.print(e);
        }
        //  JsonFlattener jf=new JsonFlattener();
        return new JSONArray(s);
    }

    public static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static JSONObject getJsonObject() {
        Response response = JsonToCsvApi.getResponse();
        String response_string = "";
        String s = "";
        try {
            InputStream instream = response.body().byteStream();
            response_string += convertStreamToString(instream);
            // System.out.println(response_string);
            s += response_string.substring(response_string.indexOf('y') + 3, response_string.indexOf("groupDetails") - 2) + "}";
            System.out.println(s);
            instream.close();
        } catch (Exception e) {
            System.out.print(e);
        }
        //  JsonFlattener jf=new JsonFlattener();
        return new JSONObject(s);
    }

}
