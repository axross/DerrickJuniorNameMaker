package com.example.derrick_junior_name_maker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.derrick_junior_name_maker.models.NameLogic;
import com.example.derrick_junior_name_maker.models.Question;
import com.example.derrick_junior_name_maker.models.QuestionList;
import com.example.derrick_junior_name_maker.models.QuestionListBuilder;
import com.example.derrick_junior_name_maker.models.QuestionOption;
import com.example.derrick_junior_name_maker.models.WorldPeopleNameList;
import com.example.derrick_junior_name_maker.models.WorldPeopleNameListItem;
import com.example.derrick_junior_name_maker.view_models.QuestionStackViewModel;
import com.example.derrick_junior_name_maker.view_models.QuestionViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.jar.Attributes;

public class MainActivity extends AppCompatActivity {
    private Spinner countrySpinner;
    private RadioGroup genderRadioGroup;
    private RecyclerView questionListRecyclerView;

    private QuestionListRecyclerViewAdapter questionListRecyclerViewAdapter;

    private QuestionStackViewModel questionStackViewModel;
    private WorldPeopleNameList worldPeopleNameList;
    private NameLogic nameLogic;

    public static final String EXTRA_NAME = "com.example.android.derrick_junior_name_maker.extra.NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countrySpinner = findViewById(R.id.country_spinner);
        genderRadioGroup = findViewById(R.id.gender_radio_group);
        questionListRecyclerView = findViewById(R.id.question_list);

        questionStackViewModel = new QuestionStackViewModel(getQuestionList());
        worldPeopleNameList = getWorldPeopleNameList();
        nameLogic = NameLogic.getNameLogic();

        ArrayAdapter<String> countrySpinnerAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, worldPeopleNameList.getCountries());
        countrySpinner.setAdapter(countrySpinnerAdapter);

        questionListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        questionListRecyclerViewAdapter = new QuestionListRecyclerViewAdapter(questionStackViewModel);
        questionListRecyclerView.setAdapter(questionListRecyclerViewAdapter);

        genderRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.gender_radio_male:
                        nameLogic.setCountry(countrySpinner.getSelectedItem().toString());
                        nameLogic.setWorldPeopleNameList(worldPeopleNameList);
                        nameLogic.setSex("MALE");
                        questionStackViewModel.setFirstQuestion(101);

                        break;
                    case R.id.gender_radio_female:
                        nameLogic.setCountry(countrySpinner.getSelectedItem().toString());
                        nameLogic.setWorldPeopleNameList(worldPeopleNameList);
                        nameLogic.setSex("FEMALE");
                        questionStackViewModel.setFirstQuestion(201);

                        break;
                }
            }
        });

        questionStackViewModel.getQuestions().observe(this, (questions) -> {
            questionListRecyclerViewAdapter.notifyDataSetChanged();
        });
    }

    private WorldPeopleNameList getWorldPeopleNameList() {
        try {
            JSONArray json = new JSONArray(new Scanner(getResources().openRawResource(R.raw.names)).useDelimiter("\\A").next());
            ArrayList<WorldPeopleNameListItem> list = new ArrayList<>();

            for (int i = 0; i < json.length(); ++i) {
                JSONObject regionItem = json.getJSONObject(i);

                String region = regionItem.getString("region");
                JSONArray maleNamesJson = regionItem.getJSONArray("male");
                JSONArray femaleNamesJson = regionItem.getJSONArray("female");

                ArrayList<String> maleNames = new ArrayList<>();

                for (int j = 0; j < maleNamesJson.length(); ++j) {
                    maleNames.add(maleNamesJson.getString(j));
                }

                ArrayList<String> femaleNames = new ArrayList<>();

                for (int j = 0; j < femaleNamesJson.length(); ++j) {
                    femaleNames.add(femaleNamesJson.getString(j));
                }

                list.add(new WorldPeopleNameListItem(region, maleNames, femaleNames));
            }

            return new WorldPeopleNameList(list);
        } catch (JSONException error) {
            throw new RuntimeException(error);
        }
    }

    private QuestionList getQuestionList() {
        try {
            JSONArray json = new JSONArray(new Scanner(getResources().openRawResource(R.raw.questions)).useDelimiter("\\A").next());

            QuestionListBuilder questionListBuilder = new QuestionListBuilder();

            for (int i = 0; i < json.length(); ++i) {
                JSONObject questionJson = json.getJSONObject(i);

                int questionId = questionJson.getInt("id");
                String questionText = questionJson.getString("text");
                JSONArray optionsJson = questionJson.getJSONArray("options");

                ArrayList<QuestionOption> options = new ArrayList<>();

                for (int j = 0; j < optionsJson.length(); ++j) {
                    JSONObject optionJson = optionsJson.getJSONObject(j);

                    int optionId = optionJson.getInt("id");
                    String optionText = optionJson.getString("text");

                    Integer nextQuestionId = optionJson.isNull("nextQuestionId")
                        ? null
                        : optionJson.getInt("nextQuestionId");

                    QuestionOption option = new QuestionOption(optionId, optionText, nextQuestionId);

                    options.add(option);
                }

                Question question = new Question(questionId, questionText, options);

                questionListBuilder.setQuestion(questionId, question);
            }

            return questionListBuilder.build();
        } catch (JSONException error) {
            throw new RuntimeException(error);
        }
    }
}

class QuestionListRecyclerViewAdapter extends RecyclerView.Adapter<QuestionListRecyclerViewAdapter.ViewHolder> {
    QuestionListRecyclerViewAdapter(QuestionStackViewModel questionStackViewModel) {
        this.questionStackViewModel = questionStackViewModel;
    }

    private final QuestionStackViewModel questionStackViewModel;

    @NonNull
    @Override
    public QuestionListRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionListRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.setQuestionViewModel(questionStackViewModel.getQuestions().getValue().get(position));
    }

    @Override
    public int getItemCount() {
        return questionStackViewModel.getQuestions().getValue().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ViewHolder(View view) {
            super(view);

            root = view;
            textView = view.findViewById(R.id.question_list_item_text);
            optionRadioGroup = view.findViewById(R.id.question_list_item_radio_group);
        }

        private final View root;

        private final TextView textView;

        private final RadioGroup optionRadioGroup;

        private void setQuestionViewModel(QuestionViewModel questionViewModel) {
            Question question = questionViewModel.getQuestion();
            QuestionOption selectedOption = questionViewModel.getSelectedOption().getValue();

            textView.setText(question.getText());

            optionRadioGroup.clearCheck();
            optionRadioGroup.removeAllViews();

            List<QuestionOption> options = question.getOptions();

            for (QuestionOption option: options) {
                RadioButton optionRadioButton = new RadioButton(root.getContext());

                optionRadioButton.setText(option.getText());
                optionRadioButton.setChecked(option.equals(selectedOption));

                optionRadioButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        if(question.getId() != 301){

                            questionViewModel.setSelectedOption(option);
                        } else {

                            Button button = new Button(root.getContext());
                            button.setText("Get Name");

                            optionRadioGroup.addView(button);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    NameLogic nameLogic = NameLogic.getNameLogic();
                                    String name = nameLogic.getName();

                                    Context context = root.getContext();
                                    Intent intent = new Intent(context, NameActivity.class);
                                    intent.putExtra(MainActivity.EXTRA_NAME, name);

                                    context.startActivity(intent);
                                }
                            });
                        }
                    }
                });

                optionRadioGroup.addView(optionRadioButton);
            }
        }
    }
}
