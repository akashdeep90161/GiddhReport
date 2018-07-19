package json.to.csv.report.modified;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CreateReport {

    public static void main(String[] args) {




      // Response response=JsonToCsvApi.getResponse();
        //System.out.println(resp.toString());
       // HttpEntity entity=response.getEntity();
        String response_string =GetJsonResponse.getJsonString();
        String s = "";

           // InputStream instream = response.body().byteStream();
            //response_string += Test.convertStreamToString(instream);

           System.out.println(response_string);
           s += "{" + response_string.substring(response_string.indexOf("forwardedBalance") - 1, response_string.lastIndexOf(']') + 1) + "}";
            System.out.println(s);



       ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        FileWriter writer = null;
        FileWriter acc_writer = null;
        // mapper.configure(JsonParser.Feature.ALLOW_UNCOLON_FIELD_NAMES, true);
        try {
            GiddhReportModel gd
                    = mapper.readValue(s, GiddhReportModel.class);
              // System.out.println((gd.getGroupDetails().get(0)).get("debitTotal"));

            writer = (new FileWriter("/home/akash/Desktop/GroupDetails.csv"));
            acc_writer = (new FileWriter("/home/akash/Desktop/GiddhAccounts.csv"));
            acc_writer.append("Name,Name_ID,OpeningBalance,CreaditTotal,DebitTotal,ClosingBalance\n");
            writer.append("Name,Category,GroupsName,OpeningBalance,CreaditTotal,DebitTotal,ClosingBalance\n");
            for(int i=0;i<gd.getGroupDetails().size();i++) {
                Map<String,Object> map=gd.getGroupDetails().get(i);
                List<Map<String,Object>> child_list=((List<Map<String,Object>>)map.get("childGroups"));


                double total_open_balance=0;
                double total_close_balance=0;
                double total_credit=0;
                double total_debit=0;
                for (int j=0;j<child_list.size();j++)
                {
                    Map<String,Object> child_map=child_list.get(j);
                    try {

                        total_credit += (int) child_map.get("creditTotal");
                    }
                    catch (ClassCastException e) {
                        total_credit += (double) child_map.get("creditTotal");
                    }
                    try {
                        total_debit += (int) child_map.get("debitTotal");
                    }
                    catch (ClassCastException e) {
                        total_debit += (double) child_map.get("debitTotal");
                    }
                      try {
                          total_close_balance += (int) ((Map<String, Object>) child_map.get("closingBalance")).get("amount");
                      }
                      catch (ClassCastException e) {
                          total_close_balance += (double) ((Map<String, Object>) child_map.get("closingBalance")).get("amount");
                      }
                      try {
                        total_open_balance += (int) ((Map<String, Object>) child_map.get("forwardedBalance")).get("amount");
                    }
                    catch (ClassCastException e)
                    {

                        total_open_balance += (double) ((Map<String, Object>) child_map.get("forwardedBalance")).get("amount");
                    }
                    String total_open_type=((Map<String,Object>)child_map.get("forwardedBalance")).get("type").toString();

                    String total_close_type=((Map<String,Object>)child_map.get("closingBalance")).get("type").toString();


                    List<Map<String,Object>> acc_list=(List<Map<String,Object>>)child_map.get("accounts");
                    for (int k=0;k<acc_list.size();k++)
                    {
                        Map<String,Object> acc_map=acc_list.get(k);
                        String acc_name=acc_map.get("name").toString();
                        String acc_name_id=acc_map.get("uniqueName").toString();
                        String acc_open_amount= (((Map<String,Object>)acc_map.get("openingBalance")).get("amount")).toString();
                        String acc_open_type= (((Map<String,Object>)acc_map.get("openingBalance")).get("type")).toString();
                        String acc_close_amount= (((Map<String,Object>)acc_map.get("closingBalance")).get("amount")).toString();
                        String acc_close_type= (((Map<String,Object>)acc_map.get("closingBalance")).get("type")).toString();
                        String acc_creadit_total=acc_map.get("creditTotal").toString();
                        String acc_debit_total=acc_map.get("debitTotal").toString();


                        acc_writer.append(acc_name+","+acc_name_id+","+acc_open_amount+" ("+acc_open_type+")"+","+acc_creadit_total+","+acc_debit_total+","+acc_close_amount+" ("+acc_close_type+")"+"\n");
                    }
                    acc_writer.append("Total" + "," + "," + total_open_balance + " (" +total_open_type+")"+ "," +total_credit + "," + total_debit + "," + total_close_balance + " (" +total_close_type+")" +"\n");
                }

                String name=map.get("uniqueName").toString();
                String category=map.get("category").toString();
                String groupName=map.get("groupName").toString();
                String open_amount= (((Map<String,Object>)map.get("forwardedBalance")).get("amount")).toString();
                String open_type= (((Map<String,Object>)map.get("forwardedBalance")).get("type")).toString();
                String close_amount= (((Map<String,Object>)map.get("closingBalance")).get("amount")).toString();
                String close_type= (((Map<String,Object>)map.get("closingBalance")).get("type")).toString();
                String creadit_total=map.get("creditTotal").toString();
                String debit_total=map.get("debitTotal").toString();

                writer.append(name + "," + category + "," + groupName + ","+open_amount+" "+"("+open_type+")"+ ","+creadit_total +"," +debit_total+ "," +close_amount+" "+"("+close_type+")"+"\n");
            }
            writer.append("Total" + "," + "" + "," + "," + gd.getForwardedBalance().get("amount").toString()+" "+"("+ gd.getForwardedBalance().get("type").toString()+")" + "," + gd.getCreditTotal() + "," + gd.getDebitTotal() + "," + gd.getClosingBalance().get("amount").toString()+" "+"("+ gd.getClosingBalance().get("type").toString()+")" + "\n");
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
            finally {
            close(writer);
        }

      }
    public static void close(FileWriter writer) {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    }

