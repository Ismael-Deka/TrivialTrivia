package com.ismaeldeka.TrivialTrivia;

import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Ismael on 2/15/2018.
 */

public class TriviaUtils {
    private final static String[] CATEGORIES = {"Any Category", "General Knowledge","Entertainment: Books","Entertainment: Film","Entertainment: Music","Entertainment: Musicals & Theatres","Entertainment: Television","Entertainment: Video Games","Entertainment: Board Games","Science & Nature","Science: Computers","Science: Mathematics","Mythology","Sports","Geography","History","Politics","Art","Celebrities","Animals","Vehicles","Entertainment: Comics","Science: Gadgets","Entertainment: Japanese Anime & Manga","Entertainment: Cartoon & Animations"};
    private final static String[] DIFFICULTY = {"Easy","Medium","Hard"};
    private final static String[] QUESTION_TYPE = {"Multiple Choice","True or False"};
    private final static String[] GAME_TYPE = {"Custom Game","30-Second Relay","15-Question Trivia","25-Question Trivia","50-Question Trivia"};
    public static class GameParams {
        public static final int CATEGORY = 0;
        public static final int TYPE = 1;
        public static final int DIFFICULTY = 2;
        public static final int QUESTION = 3;
        public static final int CORRECT_ANSWER = 4;
        public static final int INCORRECT_ANSWERS = 5;
    }

    public final static int CUSTOM_GAME = 0;
    public final static int THIRTY_SEC_RELAY = 1;
    public final static int FIFTEEN_QUESTIONS = 2;
    public final static int TWENTY_FIVE_QUESTIONS = 3;
    public final static int FIFTY_QUESTIONS = 4;

    public final static int MULTIPLE_CHOICE = 0;
    public final static int TRUE_FALSE = 1;

    public final static int ANY_CATEGORY = 0;
    public final static int MAX_NUM_QUESTIONS = 50;


    private static ArrayList<String> mCategoryList = new ArrayList<>(Arrays.asList(CATEGORIES));
    private static ArrayList<String> mDifficultyList = new ArrayList<>(Arrays.asList(DIFFICULTY));
    private static ArrayList<String> mQuestionTypeList = new ArrayList<>( Arrays.asList(QUESTION_TYPE));
    private static ArrayList<String> mGameTypeList = new ArrayList<>( Arrays.asList(GAME_TYPE));


    public static ArrayList<String> getCategoryList() {
        return mCategoryList;
    }

    public static ArrayList<String> getDifficultyList() {
        return mDifficultyList;
    }

    public static ArrayList<String> getQuestionTypeList() {
        return mQuestionTypeList;
    }

    public static ArrayList<String> getGameTypeList() {
        return mGameTypeList;
    }


    public static int getCategoryId(String category){

        if(category.equals(mCategoryList.get(ANY_CATEGORY)))
            return -1;

        if(mCategoryList.contains(category)){
            int catagoryIndex = mCategoryList.indexOf(category);
            return catagoryIndex + 9;
        }else {
            return -1;
        }
    }

    public static String getCategoryName(int id){
        int categoryLength = mCategoryList.size();
        int offsetId = id - 9;

        if(offsetId < categoryLength && offsetId >= 0){
            return mCategoryList.get(offsetId);
        }else {
            return null;
        }
    }

    public static String getQuestionTypeParam(String questionType){
        if(questionType.equals(QUESTION_TYPE[MULTIPLE_CHOICE])){
            return "multiple";
        }else if(questionType.equals(QUESTION_TYPE[TRUE_FALSE])){
            return "boolean";
        }else{
            return null;
        }
    }
    public static String getQuestionTypeName(String questionTypeParam){
        if(questionTypeParam.equals("multiple")){
            return QUESTION_TYPE[MULTIPLE_CHOICE];
        }else if(questionTypeParam.equals("boolean")){
            return QUESTION_TYPE[TRUE_FALSE];
        }else{
            return null;
        }
    }

    public static boolean isQuestionMultipleChoice(Question question){
        String questionType = question.getQuestionType();

        if(questionType.equals("multiple")){
            return true;
        }else {
            return false;
        }

    }

    public static Bundle getGameArgs(int gameType, Context context){
        switch (gameType){
            case THIRTY_SEC_RELAY:
                return setGameType(context,GAME_TYPE[ANY_CATEGORY],null,MAX_NUM_QUESTIONS,30);

            case FIFTEEN_QUESTIONS:
                return setGameType(context,GAME_TYPE[ANY_CATEGORY],null, 15,-1);

            case TWENTY_FIVE_QUESTIONS:
                return setGameType(context,GAME_TYPE[ANY_CATEGORY],null, 25,-1);

            case FIFTY_QUESTIONS:
                return setGameType(context,GAME_TYPE[ANY_CATEGORY],null,MAX_NUM_QUESTIONS,-1);
        }
        return null;
    }

    private static Bundle setGameType(Context context,String category,String difficulty, int numQuestions, int timeLimit){
        Bundle b = new Bundle();
        b.putString(context.getString(R.string.category),category);
        b.putString(context.getString(R.string.difficulty), difficulty);
        b.putInt(context.getString(R.string.num_questions), numQuestions);
        b.putInt(context.getString(R.string.time_limit), timeLimit);
        return b;
    }
}
