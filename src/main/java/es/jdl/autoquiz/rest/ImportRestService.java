package es.jdl.autoquiz.rest;

import es.jdl.autoquiz.domain.Answer;
import es.jdl.autoquiz.domain.Question;
import es.jdl.autoquiz.domain.Quiz;
import es.jdl.autoquiz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

@RestController
@RequestMapping("/admin")
public class ImportRestService {

    @Autowired
    private QuizService service;

    @PostMapping("/import_json")
    public Quiz importFromJson(@RequestBody Quiz quiz) {
        service.saveQuizDeep(quiz);
        return quiz;
    }

    @PostMapping("/import_answers_csv")
    public Quiz importAnswersFromCSV(@RequestParam("file") MultipartFile file,
                                     @RequestParam("quiz") String quizId,
                                     @RequestParam( name = "feedbackOk", required = false, defaultValue = "Correct!") String feedbackOk,
                                     @RequestParam( name = "feedbackError", required = false, defaultValue = "Wrong!") String feedbackError,
                                     @RequestParam( name = "fractionOk", required = false, defaultValue = "100") Integer fractionOk,
                                     @RequestParam( name = "fractionError", required = false, defaultValue = "0") Integer fractionError
    ) throws IOException {
        Quiz q = service.findQuizById(quizId);
        q.setQuestions(new ArrayList<>()); // will be filled with just codes
        BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
        String line;
        while ((line = br.readLine()) != null) {
            String[] row = line.split(";");
            Answer a = new Answer();
            a.setQuestionId(row[0]);
            a.setId(row[1]);
            int count = service.markSingleAnswer(quizId, a.getQuestionId(), a.getId(), feedbackOk, fractionOk,
                    feedbackError, fractionError);
            if (count == 1) {
                a.setFeedback(feedbackOk);
                a.setFraction(fractionOk);
                Question question = new Question();
                question.setId(a.getQuestionId());
                question.setAnswers(new ArrayList<>(1));
                question.getAnswers().add(a);
                q.getQuestions().add(question);
            }
        } // while
        br.close();

        return q;
    }
}
