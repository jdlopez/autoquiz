package es.jdl.autoquiz;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.jdl.autoquiz.domain.Quiz;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class MakeQuizzes {

    public static void main(String[] args) throws Exception {
        List<Quiz> outQuizzes = new LinkedList<>();
        File inDir = new File(args[0]);
        if (!inDir.isDirectory()) {
            System.out.println(inDir + " must be a directory");
            System.exit(-1);
        }
        ObjectMapper om = new ObjectMapper();
        String quizzesName = "quizzes.json";
        for (String fileName: inDir.list((dir, name) -> name.endsWith(".json") && !name.equalsIgnoreCase(quizzesName))) {
            Quiz q = om.readValue(new File(inDir, fileName), Quiz.class);
            q.setQuestions(null);
            outQuizzes.add(q);
        }
        // out
        if (!outQuizzes.isEmpty()) {
            om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            om.writerWithDefaultPrettyPrinter().writeValue(new File(inDir, quizzesName), outQuizzes);
            System.out.println(outQuizzes.size() + " quizzes write in " + inDir.toString());
        } else
            System.out.println("No quizzes read");
    }
}
