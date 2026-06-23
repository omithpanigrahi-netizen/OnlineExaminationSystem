package com.example.onlineexaminationsystem.controller;

import com.example.onlineexaminationsystem.model.Question;
import com.example.onlineexaminationsystem.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/exam")
@CrossOrigin(origins = "*")
public class ExamController {

    @Autowired
    private QuestionRepository repository;

    // 1. అన్ని ప్రశ్నలను పొందడానికి (Get All Questions)
    @GetMapping("/questions")
    public List<Question> getQuestions() {
        if(repository.count() == 0) {
            Question q1 = new Question();
            q1.setQuestionText("What is the default value of an instance variable in Java if it is an object reference?");
            q1.setOptionA("null"); q1.setOptionB("0"); q1.setOptionC("undefined"); q1.setOptionD("Depends on compiler");
            q1.setCorrectAnswer("A");
            repository.save(q1);

            Question q2 = new Question();
            q2.setQuestionText("Which component is used to compile, debug and execute the Java programs?");
            q2.setOptionA("JRE"); q2.setOptionB("JIT"); q2.setOptionC("JDK"); q2.setOptionD("JVM");
            q2.setCorrectAnswer("C");
            repository.save(q2);

            Question q3 = new Question();
            q3.setQuestionText("Which of these packages contains all the Java collection framework classes and interfaces?");
            q3.setOptionA("java.lang"); q3.setOptionB("java.util"); q3.setOptionC("java.io"); q3.setOptionD("java.net");
            q3.setCorrectAnswer("B");
            repository.save(q3);
        }
        return repository.findAll();
    }

    // 2. ఎగ్జామ్ రిజల్ట్ క్యాలిక్యులేట్ చేయడానికి (Submit and Calculate Score)
    @PostMapping("/submit")
    public String calculateScore(@RequestBody Map<Long, String> userAnswers) {
        int score = 0;
        List<Question> questions = repository.findAll();

        for (Question q : questions) {
            String submittedAnswer = userAnswers.get(q.getId());
            if (submittedAnswer != null && submittedAnswer.equalsIgnoreCase(q.getCorrectAnswer())) {
                score++;
            }
        }
        return "You scored: " + score + " out of " + questions.size();
    }
}