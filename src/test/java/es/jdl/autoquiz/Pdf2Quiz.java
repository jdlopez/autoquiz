package es.jdl.autoquiz;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.jdl.autoquiz.domain.Answer;
import es.jdl.autoquiz.domain.EnumNumering;
import es.jdl.autoquiz.domain.EnumQuestionType;
import es.jdl.autoquiz.domain.Question;
import es.jdl.autoquiz.domain.Quiz;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * Read a PDF containing a quiz and parse it and transform into JSON file:
 * <pre>
 * quizId
 * ignorePrefix
 * answersPrefix
 * pageStart
 * </pre>
 */
public class Pdf2Quiz {

    private static String[] ignorePrefix;
    private static String quizId;
    private static String[] answersPrefix;
    private static int pageStart = 1;

    public static void main(String[] args) throws IOException {
        File inputPdf;
        File outFile;
        File answersFile;
        String title;
        Properties conf = new Properties();

        if (args.length == 0) {
            usage();
            return;
        } else {
            inputPdf = new File(args[0]);
            conf.load(new FileReader(args[1]));
            quizId = parseArgs(args, 2, conf.getProperty("quizId"));
            pageStart = Integer.parseInt(parseArgs(args, 3, conf.getProperty("pageStart", "1")));
            outFile = new File( parseArgs(args, 4, conf.getProperty("outFile", inputPdf.toString() + ".json")) );
            answersFile = new File( parseArgs(args, 5, conf.getProperty("outFile", inputPdf.toString() + ".csv")) );
            title = parseArgs(args, 6, inputPdf.getName());

            ignorePrefix = conf.getProperty("ignorePrefix").split(",");
            String answArr = conf.getProperty("answersPrefix");
            answersPrefix = answArr.substring(1, answArr.length() - 1).split(",");

        }

        try ( PDDocument document = PDDocument.load(inputPdf) ) {

            PDFTextStripper stripper = new PDFTextStripper();

            //stripper.setSortByPosition(true);
            int currentQ = 1;
            Quiz quiz = new Quiz();
            quiz.setId(quizId);
            quiz.setTitle(title);
            quiz.setAnswernumbering(EnumNumering.LETTERS);
            quiz.setInstructions("");
            quiz.setSuffleQuestions(true);
            quiz.setShowCorrect(true);
            quiz.setPassFraction(Integer.parseInt(conf.getProperty("passFraction", "100")));
            quiz.setFractionSuccess(Integer.parseInt(conf.getProperty("fractionSuccess", "100")));
            quiz.setFractionWrong(Integer.parseInt(conf.getProperty("fractionWrong", "0")));

            List<Question> questions = new LinkedList<>();
            quiz.setQuestions(questions);
            //
            for (int p = pageStart; p <= document.getNumberOfPages(); ++p) {
                stripper.setStartPage(p);
                stripper.setEndPage(p);

                String text = stripper.getText(document);
                printPage(p, text);

                List<Question> questionsPage = parseaPage(text, currentQ, p - 1);
                questions.addAll(questionsPage);
                currentQ += questionsPage.size();

            } // for paginas
            // add answers
            parseAnswers(quiz, answersFile);
            // write to disk
            ObjectMapper om = new ObjectMapper();
            om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            om.writerWithDefaultPrettyPrinter().writeValue(outFile, quiz);
        }
    }

    private static String parseArgs(String[] args, int pos, String defaultValue) {
        if (args.length > pos)
            return args[pos];
        else
            return defaultValue;
    }

    private static void parseAnswers(Quiz quiz, File answersCVS) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(answersCVS));
        String line;
        while ((line = br.readLine()) != null) {
            String[] row = line.split(";");
            if (row.length < 2) { // no es fila valida
                System.out.println("Answers ended at line: " + line);
                break;
            }
            String questionId = row[0];
            String answerId = row[1];
            //quiz.getQuestions().stream().filter(x -> x.getId().equals(row[0])).findFirst().get()
            for (Question q: quiz.getQuestions()) {
                if (q.getId().equals(questionId)) {
                    for (Answer a: q.getAnswers()) {
                        if (a.getId().equals(answerId)) {
                            a.setFraction(quiz.getFractionSuccess());
                            a.setFeedback("Correct!");
                        } else {
                            a.setFraction(quiz.getFractionWrong());
                            a.setFeedback("Wrong!");
                        }
                    } // for a
                }
            } // for q
        }

    }

    private static void printPage(int pageNum, String text) {
        String pageStr = "Page " + pageNum;
        System.out.println(pageStr);
        for (int i = 0; i < pageStr.length(); ++i) {
            System.out.print("=");
        }
        System.out.println("\n" + text.trim() + "\n");
    }

    private static boolean startsWith(String[] prefix, String testString) {
        for (String s: prefix) {
            if (testString.startsWith(s))
                return true;
        }
        return false;
    }

    private static List<Question> parseaPage(String text, int currentQ, int currentPage) {
        List<Question> ret = new LinkedList<>();
        String[] lines = text.split("\n");
        Question actualQuestion = null;
        Answer answer = null;
        String nextLetter = answersPrefix[0];
        int nextLetterIdx = 0;
        boolean inQuestion = false, inAnswer = false;
        for (int i = 0; i < lines.length; i++) {
            String l = lines[i].trim();
            if (startsWith(ignorePrefix, l))
                continue;
            if (l.startsWith(currentQ + ".")) { // q start
                // if not first q
                if (actualQuestion != null) {
                    if (inAnswer) {
                        actualQuestion.getAnswers().add(answer);
                    }
                    addQuestion(ret, actualQuestion);
                    answer = null;
                    inAnswer = false;
                }
                actualQuestion = new Question();
                actualQuestion.setAnswers(new LinkedList<>());
                actualQuestion.setId(String.valueOf(currentQ));
                actualQuestion.setQuizId(quizId);
                actualQuestion.setType(EnumQuestionType.multichoice);
                actualQuestion.setSingle(true);
                actualQuestion.setText(l);
                inQuestion = true;
                currentQ++;
            } else if (inQuestion && !l.startsWith( answersPrefix[0] )) {
                actualQuestion.setText(actualQuestion.getText() + " " + l);
            } else if (inQuestion && l.startsWith(answersPrefix[0])) { // comienzan las respuestas
                inQuestion = false;
                inAnswer = true;
                answer = newAnswer(actualQuestion.getId(), l);
                nextLetter = answersPrefix[1];
                nextLetterIdx = 1;
            } else if (inAnswer && !l.startsWith(nextLetter + ")")) {
                answer.setText(answer.getText() + " " + l);
            } else if (inAnswer && l.startsWith(nextLetter + ")")) {
                // otra respuesta
                actualQuestion.getAnswers().add(answer);
                answer = newAnswer(actualQuestion.getId(), l);
                nextLetterIdx++;
                nextLetter = answersPrefix[nextLetterIdx];
            }
        } // for
        if (answer != null) { // adds last q
            actualQuestion.getAnswers().add(answer);
            addQuestion(ret, actualQuestion);
        }
        return ret;
    }

    private static void addQuestion(List<Question> questions, Question question) {
        // split same-line answers
        if (question.getAnswers().size() == 1) {
            Answer a = question.getAnswers().get(0);
            //  b, c y d remaining
            int idxB = a.getText().indexOf(answersPrefix[1]);
            int idxC = a.getText().indexOf(answersPrefix[2]);
            int idxD = a.getText().indexOf(answersPrefix[3]);
            if (idxB > -1 && idxB < idxC)
                question.getAnswers().add(newAnswer(a.getQuestionId(), a.getText().substring(idxB, idxC)));
            if (idxC > -1 && idxC < idxD)
                question.getAnswers().add(newAnswer(a.getQuestionId(), a.getText().substring(idxC, idxD)));
            if (idxD > -1 && idxD < a.getText().length())
                question.getAnswers().add(newAnswer(a.getQuestionId(), a.getText().substring(idxD)));
            a.setText(a.getText().substring(0, idxB));
        }
        questions.add(question);
    }

    private static Answer newAnswer(String questionId, String texto) {
        Answer answer = new Answer();
        answer.setQuestionId(questionId);
        answer.setId(String.valueOf(texto.charAt(0)));
        answer.setText(texto.substring(2).trim()); // remove letter(abcd) + ')'
        return answer;
    }

    private static void usage() {
        System.err.println("Usage: java " + Pdf2Quiz.class.getName() + " <input-pdf> <config-file> quizId(optional)");
        System.exit(-1);
    }
}

