/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json.to.csv.report;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

/**
 * @author Akash
 */
public class JsonFlattener {
    public Map<String, String> parse(JSONObject jsonObject) {
        Map<String, String> flatJson = new HashMap<String, String>();
        flatten(jsonObject, flatJson, "");
        System.out.println(flatJson.remove("childGroups"));
        System.out.println(flatJson.remove("accounts"));
        System.out.println(flatJson.remove("childGroups"));
        return flatJson;
    }

    public List<Map<String, String>> parse(JSONArray jsonArray) {
        List<Map<String, String>> flatJson = new ArrayList<Map<String, String>>();
        int length = jsonArray.length();
        for (int i = 0; i < length; i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Map<String, String> stringMap = parse(jsonObject);
            System.out.println(stringMap.remove("childGroups"));
            System.out.println(stringMap.remove("accounts"));
            System.out.println(stringMap.remove("childGroups"));
            flatJson.add(stringMap);
        }
        return flatJson;
    }

    public List<Map<String, String>> parseJson(String json) throws Exception {
        List<Map<String, String>> flatJson = null;
        try {
            JSONObject jsonObject = new JSONObject(json);
            flatJson = new ArrayList<Map<String, String>>();
            flatJson.add(parse(jsonObject));
        } catch (JSONException je) {
            flatJson = handleAsArray(json);
        }
        return flatJson;
    }

    public List<Map<String, String>> handleAsArray(String json) throws Exception {
        List<Map<String, String>> flatJson = null;
        try {
            if (json.charAt(0) != '[') {
                json = "[" + json + "]";
            }
            JSONArray jsonArray = new JSONArray(json);
            flatJson = parse(jsonArray);
        } catch (Exception e) {
            //throw new Exception("Json might be malformed");
            System.out.println(e);
        }
        return flatJson;
    }

    private void flatten(JSONArray obj, Map<String, String> flatJson, String prefix) {
        int length = obj.length();
        for (int i = 0; i < length; i++) {
            if (obj.get(i).getClass() == JSONArray.class) {
                JSONArray jsonArray = (JSONArray) obj.get(i);
                if (jsonArray.length() < 1) continue;
                flatten(jsonArray, flatJson, prefix + i);
            } else if (obj.get(i).getClass() == JSONObject.class) {
                JSONObject jsonObject = (JSONObject) obj.get(i);
                flatten(jsonObject, flatJson, prefix + (i + 1));
            } else {
                String value;
                try {
                    if (obj.get(i).equals(null)) value = "null";
                    else
                        value = obj.getString(i);
                } catch (JSONException e) {

                    value = String.valueOf(obj.getDouble(i));
                }
                if (value != null)
                    flatJson.put(prefix + (i + 1), value);
            }
        }
    }

    private void flatten(JSONObject obj, Map<String, String> flatJson, String prefix) {
        Iterator iterator = obj.keys();
        while (iterator.hasNext()) {
            String key = iterator.next().toString();
            if (obj.get(key).getClass() == JSONObject.class) {
                JSONObject jsonObject = (JSONObject) obj.get(key);
                flatten(jsonObject, flatJson, prefix);
            } else if (obj.get(key).getClass() == JSONArray.class) {
                JSONArray jsonArray = (JSONArray) obj.get(key);
                if (jsonArray.length() < 1) continue;
                flatten(jsonArray, flatJson, key);
            } else {
                String value;
                try {
                    if (obj.get(key).equals(null)) value = "null";
                    else
                        value = obj.getString(key);
                } catch (JSONException e) {

                    value = String.valueOf(obj.getDouble(key));

                }
                if (value != null && !value.equals("null"))
                    flatJson.put(prefix + key, value);
            }
        }
    }

}
