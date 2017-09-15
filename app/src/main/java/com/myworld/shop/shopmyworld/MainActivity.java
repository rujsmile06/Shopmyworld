package com.myworld.shop.shopmyworld;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.myworld.shop.shopmyworld.Http.HttpHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView lv;
    JSONObject jsonobject;
    // URL to get contacts JSON
    private static String url = "http://192.168.100.148/testdata/test_product.php";

    ArrayList<HashMap<String, String>> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        productList = new ArrayList<>();

        lv = (ListView) findViewById(R.id.jsonListView);

        new GetContacts().execute();
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetContacts extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("product");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String product_name = c.getString("product_name");
                        String product_price = c.getString("product_price");
                        String product_image = c.getString("product_image");


//                        Integer email = c.getInt("image_product");
//                        String address = c.getString("address");
//                        String gender = c.getString("gender");

                        // Phone node is JSON Object
//                        JSONObject phone = c.getJSONObject("phone");
//                        String mobile = phone.getString("mobile");
//                        String home = phone.getString("home");
//                        String office = phone.getString("office");

                        // tmp hash map for single map
                        HashMap<String, String> map = new HashMap<>();

                        // adding each child node to HashMap key => value
                        map.put("product_name", product_name);
                        map.put("product_price", product_price);
                        map.put("product_image", product_image);
//                        map.put("mobile", mobile);

                        // adding map to map list
                        productList.add(map);
                    }



                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }



        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, productList,
                    R.layout.list_item, new String[]{"product_name", "product_price","product_image"},
                    new int[]{R.id.product_name,
                    R.id.product_price,R.id.imageProduct});

            lv.setAdapter(adapter);
        }

    }
}



//    ListView jsonListview;
//    List<String> exData;
//    ProgressDialog progressDialog;
//    SimpleCursorAdapter adapter;
//    ArrayList<HashMap<String, String>> listdata = new ArrayList<HashMap<String, String>>();
//    Button bt;
//    String urlString;
//    URL url = null;
//    HttpURLConnection conn = null;
//    BufferedReader bufferedReader;
//    StringBuilder stringBuilder;
//    JSONObject json = null;
//    String json_string;
//    TextView textView;
//    ArrayList<HashMap<String, String>> listdata = new ArrayList<HashMap<String, String>>();


//        ButtonRead();

//        jsonListview = (ListView)findViewById(R.id.listview);
//        exData = new ArrayList<String>();
//
//
//        new AsyncTask<String, Void, String>() {
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                progressDialog = new ProgressDialog(MainActivity.this);
//                progressDialog.setCancelable(false);
//                progressDialog.setMessage("Downloading ...");
//                progressDialog.show();
//            }
//            @Override
//            protected String doInBackground(String... strings) {
//                try {
//
//                    URL url = new URL("http://192.168.100.148/test.php");
//                    URLConnection urlConnection = url.openConnection();
//                    HttpURLConnection httpURLConnection = (HttpURLConnection)urlConnection;
//                    httpURLConnection.setInstanceFollowRedirects(true);
//                    httpURLConnection.setRequestMethod("GET");
//                    httpURLConnection.connect();
//
//                    InputStream inputStream = null;
//
//                    if(httpURLConnection.getResponseCode()== HttpURLConnection.HTTP_OK)
//                        inputStream=httpURLConnection.getInputStream();
//
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,
//                            "iso-8859-1"),8);
//                    StringBuilder stringBuilder = new StringBuilder();
//                    String line = null;
//                    while ((line=reader.readLine())!=null){
//                        stringBuilder.append(line);
//                    }
//                    inputStream.close();
//
//                    Log.d("JSON Result =",stringBuilder.toString());
////                    String id, name;
//
//                    JSONObject jsonObject = new JSONObject(stringBuilder.toString());
//                    JSONArray jsonArray = jsonObject.getJSONArray("Collaborator");
//                    for (int i=0;i < jsonArray.length(); i++){
//                        JSONObject jsonObj = jsonArray.getJSONObject(i);
//                        String info = jsonArray.getJSONObject(i).getString("area")
//                                + jsonArray.getJSONObject(i).getString("city");
//                        exData.add(info);
//
////                        jsonObj.getString("student_id");
////                        jsonObj.getString("name");
////                        exData.add(jsonObj.getString("test"));
//
////                        id = jsonObj.getString("student_id");
////                        name = jsonObj.getString("name");
////                        String dataoutlist = id + " " + name;
////                        HashMap<String, String> map = new HashMap<String, String>();
////                        listdata.add(map);
////                        map.put("student_id", id);
////                        map.put("name", dataoutlist);
////                        ListAdapter adapter = new SimpleAdapter(MainActivity.this, listdata,
////                                R.layout.list_item, new String[]{"data",}, new int[]{
////                                R.id.ColName});
////        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(MainActivity.this,
////                android.R.layout.simple_list_item_1,android.R.id.text1, exData);
//
////                        jsonListview.setAdapter(adapter);
//                    }
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                return null;
//            }
//            @Override
//            protected void onPostExecute(String avoid) {
//                super.onPostExecute(avoid);
//
//                ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(MainActivity.this,
//                        android.R.layout.simple_list_item_1,android.R.id.text1, exData);
//                jsonListview.setAdapter(myAdapter);
////                ListAdapter adapter = new SimpleAdapter(MainActivity.this, listdata,
////                        R.layout.list_item, new String[]{"data",}, new int[]{
////                        R.id.ColName});
////                jsonListview.setAdapter(adapter);
//                progressDialog.dismiss();
//            }
//
//        }.execute();

//    public class Collaborator {
//        private String student_id;
//        private String name;
//
//        public String getStudent_id() {
//            return student_id;
//        }
//
//        public void setStudent_id(String student_id) {
//            this.student_id = student_id;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setPosition(String name) {
//            this.name = name;
//        }
//    }
//    public void ButtonRead() {
//        bt = (Button) findViewById(R.id.button);
//        bt.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                new Auth().execute();
//            }
//        });
//
//    }
//
//
//    private class Auth extends android.os.AsyncTask<String, Void, String> {
//
//        @Override
//        protected String doInBackground(String... strings) {
//
//            try {
//                urlString = "http://192.168.100.148/test.php";
//                url = new URL(urlString);
//                System.out.println("urlString : " + urlString);
//                conn = (HttpURLConnection) url.openConnection();
//                conn.setReadTimeout(10000);
//                conn.setConnectTimeout(15000);
//                conn.setDoOutput(true);
//                conn.setInstanceFollowRedirects(false);
//                conn.setRequestMethod("GET");
//                conn.setRequestProperty("charset", "utf-8");
//                conn.setUseCaches(false);
//
//                InputStream inputStream = new BufferedInputStream(conn.getInputStream());
//
//                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//                stringBuilder = new StringBuilder();
//                String line;
//                while ((line = bufferedReader.readLine()) != null) {
//                    stringBuilder.append(line);
//                }
//
//                System.out.println("stringBuilder : " + stringBuilder);
//
//                json = new JSONObject(stringBuilder.toString());
//                JSONArray jsonArray = json.getJSONArray(json.toString());
//                for (int i = 0; i < jsonArray.length(); i++) {
////                        JSONObject jsonObj = jsonArray.getJSONObject(i);
////                        exData.add(jsonObj.getString("test"));
//
//                    System.out.println("Json : " + json);
//
//                    if (json == null) {
//                        Toast.makeText(getApplicationContext(), "Empty", Toast.LENGTH_SHORT).show();
//
//                    } else {
//                        textView.setText(json);
//                    }
//
//                }
//
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            } catch (ProtocolException e) {
//                e.printStackTrace();
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
    //        jsonListview = (ListView)findViewById(R.id.json);
////        exData = new ArrayList<String>();
//
//
//        new AsyncTask<String, Void, String>() {
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                progressDialog = new ProgressDialog(MainActivity.this);
//                progressDialog.setCancelable(false);
//                progressDialog.setMessage("Downloading ...");
//                progressDialog.show();
//            }
//            @Override
//            protected String doInBackground(String... strings) {
//                try {
//
//                    URL url = new URL("http://192.168.100.148/test.php");
//                    URLConnection urlConnection = url.openConnection();
//                    HttpURLConnection httpURLConnection = (HttpURLConnection)urlConnection;
//                    httpURLConnection.setInstanceFollowRedirects(true);
//                    httpURLConnection.setRequestMethod("GET");
//                    httpURLConnection.connect();
//
//                    InputStream inputStream =null;
//
//                    if(httpURLConnection.getResponseCode()== HttpURLConnection.HTTP_OK)
//                        inputStream=httpURLConnection.getInputStream();
//
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,
//                            "iso-8859-1"),8);
//                    StringBuilder stringBuilder = new StringBuilder();
//                    String line = null;
//                    while ((line=reader.readLine())!=null){
//                        stringBuilder.append(line + "\n");
//                    }
//                    inputStream.close();
//                    Log.d("JSON Result =",stringBuilder.toString());
//                    String id, name;
//                    JSONObject jsonObject = new JSONObject(stringBuilder.toString());
//                    JSONArray jsonArray = jsonObject.getJSONArray("student");
//                    for (int i=0;i < jsonArray.length(); i++){
//                        JSONObject jsonObj = jsonArray.getJSONObject(i);
//                        exData.add(jsonObj.getString("test"));
//
//                        id = jsonObj.getString("student_id");
//                        name = jsonObj.getString("name");
//                        String dataoutlist = id + " " + name;
//                        HashMap<String, String> map = new HashMap<String, String>();
//                        listdata.add(map);
//                        map.put("student_id", id);
//                        map.put("name", dataoutlist);
////                        ListAdapter adapter = new SimpleAdapter(MainActivity.this, listdata,
////                                R.layout.list_item, new String[]{"data",}, new int[]{
////                                R.id.ColName});
////        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(MainActivity.this,
////                android.R.layout.simple_list_item_1,android.R.id.text1, exData);
//
////                        jsonListview.setAdapter(adapter);
//                    }
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                return null;
//            }
//            @Override
//            protected void onPostExecute(String avoid) {
//                super.onPostExecute(avoid);
//
////                ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(MainActivity.this,
////                        android.R.layout.simple_list_item_1,android.R.id.text1, exData);
////                jsonListview.setAdapter(myAdapter);
//                ListAdapter adapter = new SimpleAdapter(MainActivity.this, listdata,
//                        R.layout.list_item, new String[]{"data",}, new int[]{
//                        R.id.ColName});
//                jsonListview.setAdapter(adapter);
//                progressDialog.dismiss();
//            }
//
//        }.execute();
//            return null;
//        }
//
//    }
//}
//        jsonListview = (ListView)findViewById(R.id.json);
////        exData = new ArrayList<String>();
//
//
//        new AsyncTask<String, Void, String>() {
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                progressDialog = new ProgressDialog(MainActivity.this);
//                progressDialog.setCancelable(false);
//                progressDialog.setMessage("Downloading ...");
//                progressDialog.show();
//            }
//            @Override
//            protected String doInBackground(String... strings) {
//                try {
//
//                    URL url = new URL("http://192.168.100.148/test.php");
//                    URLConnection urlConnection = url.openConnection();
//                    HttpURLConnection httpURLConnection = (HttpURLConnection)urlConnection;
//                    httpURLConnection.setInstanceFollowRedirects(true);
//                    httpURLConnection.setRequestMethod("GET");
//                    httpURLConnection.connect();
//
//                    InputStream inputStream =null;
//
//                    if(httpURLConnection.getResponseCode()== HttpURLConnection.HTTP_OK)
//                        inputStream=httpURLConnection.getInputStream();
//
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,
//                            "iso-8859-1"),8);
//                    StringBuilder stringBuilder = new StringBuilder();
//                    String line = null;
//                    while ((line=reader.readLine())!=null){
//                        stringBuilder.append(line + "\n");
//                    }
//                    inputStream.close();
//                    Log.d("JSON Result =",stringBuilder.toString());
//                    String id, name;
//                    JSONObject jsonObject = new JSONObject(stringBuilder.toString());
//                    JSONArray jsonArray = jsonObject.getJSONArray("student");
//                    for (int i=0;i < jsonArray.length(); i++){
//                        JSONObject jsonObj = jsonArray.getJSONObject(i);
//                        exData.add(jsonObj.getString("test"));
//
//                        id = jsonObj.getString("student_id");
//                        name = jsonObj.getString("name");
//                        String dataoutlist = id + " " + name;
//                        HashMap<String, String> map = new HashMap<String, String>();
//                        listdata.add(map);
//                        map.put("student_id", id);
//                        map.put("name", dataoutlist);
////                        ListAdapter adapter = new SimpleAdapter(MainActivity.this, listdata,
////                                R.layout.list_item, new String[]{"data",}, new int[]{
////                                R.id.ColName});
////        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(MainActivity.this,
////                android.R.layout.simple_list_item_1,android.R.id.text1, exData);
//
////                        jsonListview.setAdapter(adapter);
//                    }
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                return null;
//            }
//            @Override
//            protected void onPostExecute(String avoid) {
//                super.onPostExecute(avoid);
//
////                ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(MainActivity.this,
////                        android.R.layout.simple_list_item_1,android.R.id.text1, exData);
////                jsonListview.setAdapter(myAdapter);
//                ListAdapter adapter = new SimpleAdapter(MainActivity.this, listdata,
//                        R.layout.list_item, new String[]{"data",}, new int[]{
//                        R.id.ColName});
//                jsonListview.setAdapter(adapter);
//                progressDialog.dismiss();
//            }
//
//        }.execute();