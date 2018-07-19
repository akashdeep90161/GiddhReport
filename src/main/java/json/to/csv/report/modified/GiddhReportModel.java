package json.to.csv.report.modified;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Map<String,Object> studentDataMap = new HashMap<String,Object>();
@JsonIgnoreProperties(ignoreUnknown = true)
public class GiddhReportModel {
    List<Map<String,Object>> groupDetails=new ArrayList<>();
    Map<String,Object> forwardedBalance=new HashMap<>();
    double creditTotal;
    double debitTotal;
    Map<String,Object> closingBalance=new HashMap<>();
    List<Map<String,Object>> childGroups=new ArrayList<>();
    List<Map<String,Object>> accounts=new ArrayList<>();
    String uniqueName;
    String groupName;
    String category;

    public List<Map<String,Object>> getGroupDetails()
    {
        return groupDetails;
    }
    public Map<String,Object> getForwardedBalance(){
        return forwardedBalance;
    }
    public Map<String,Object> getClosingBalance()
    {
        return closingBalance;
    }


    public double getCreditTotal()
    {
        return creditTotal;
    }
    public double getDebitTotal()
    {
        return debitTotal;
    }
    public String getName()
    {
        return uniqueName;
    }
    public String getGroupName()
    {
        return groupName;
    }
    public String getCategory()
    {
        return category;
    }
}
