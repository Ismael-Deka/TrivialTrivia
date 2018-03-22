package com.ismaeldeka.TrivialTrivia;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.text.Html;
import android.util.Log;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Ismael on 2/15/2018.
 */

public class QuestionLoader extends AsyncTaskLoader<ArrayList<Question>> {

    private ApiCall mApiCall;

    private final String LOG_TAG = "QuestionLoader";

    public QuestionLoader(Context context, ApiCall apiCall) {
        super(context);
        mApiCall = apiCall;
    }

    @Override
    public ArrayList<Question> loadInBackground() {

        if(mApiCall == null){
            //if there's no internet connection
            return null;
        }

        String jsonResponse = "";
        try {
            jsonResponse = makeHttpRequest(mApiCall.buildApiCall());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(jsonResponse == null){
            return null;
        }else if(!jsonResponse.equals(""))
            return getQuestions(jsonResponse);
        else
            //if there's no json response
            return null; // TODO: Differentiate empty json response from No internet
    }

    private String makeHttpRequest(URL url) throws IOException {

        // If the URL is null, then return early.
        if (url == null) {
            return "";
        }

        String response = null;
        HttpsURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            ProviderInstaller.installIfNeeded(getContext());
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                response = readFromStream(inputStream);

            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the response.", e);

        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null&&inputStream!=null) {
                urlConnection.disconnect();
                inputStream.close();
            }
        }
        return response;
    }
    private String readFromStream(InputStream inputStream) throws IOException {
        //Getting String JSON response from InputStream
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }

        }
        return output.toString();
    }

    private ArrayList<Question> getQuestions(String jsonResponse){

        ArrayList<Question> questions = new ArrayList<>();

        JSONObject reader = null;
        try {
            reader = new JSONObject(jsonResponse);
            JSONArray arr = reader.getJSONArray("results");
            for(int i = 0; i < arr.length();i++) {
                Question tempQuestion = new Question();
                reader = arr.getJSONObject(i);
                tempQuestion.setCategory(reader.getString(getContext().getString(R.string.category)));
                tempQuestion.setQuestionType(reader.getString(getContext().getString(R.string.type)));
                tempQuestion.setDifficulty(reader.getString(getContext().getString(R.string.difficulty)));
                tempQuestion.setQuestion(reader.getString(getContext().getString(R.string.question)));
                tempQuestion.setCorrectAnswer(reader.getString(getContext().getString(R.string.correct_answer)));
                tempQuestion.setIncorrectAnswers(getIncorrectAnswers(reader.getJSONArray(getContext().getString(R.string.incorrect_answers))));
                questions.add(tempQuestion);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
                return questions;
    }

    private ArrayList<String> getIncorrectAnswers(JSONArray array) throws JSONException {
        ArrayList<String> arrayList = new ArrayList<>();
        for(int i = 0; i < array.length();i++){
            arrayList.add(Html.fromHtml(array.getString(i)).toString());
        }
        return arrayList;


    }
}
