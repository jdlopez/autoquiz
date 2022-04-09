package es.jdl.autoquiz;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.jdl.autoquiz.domain.Answer;
import es.jdl.autoquiz.domain.EnumNumering;
import es.jdl.autoquiz.domain.EnumQuestionType;
import es.jdl.autoquiz.domain.Question;
import es.jdl.autoquiz.domain.Quiz;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

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

        if (args.length == 0) {
            usage();
            return;
        } else {
            inputPdf = new File(args[0]);
            Properties conf = new Properties();
            conf.load(new FileReader(args[1]));
            if (args.length > 2)
                quizId = args[2];
            else
                quizId = conf.getProperty("quizId");
            if (args.length > 3)
                pageStart = Integer.parseInt(args[3]);
            else
                pageStart = Integer.parseInt(conf.getProperty("pageStart", "1"));
            ignorePrefix = conf.getProperty("ignorePrefix").split(",");
            String answArr = conf.getProperty("answersPrefix");
            answersPrefix = answArr.substring(1, answArr.length() - 1).split(",");
            if (args.length > 4)
                outFile = new File(args[4]);
            else
                outFile = new File(conf.getProperty("outFile", inputPdf.toString() + ".json"));
        }

        try ( PDDocument document = PDDocument.load(inputPdf) ) {

            PDFTextStripper stripper = new PDFTextStripper();

            //stripper.setSortByPosition(true);
            int currentQ = 1;
            Quiz quiz = new Quiz();
            quiz.setId(quizId);
            quiz.setTitle(inputPdf.getName());
            quiz.setAnswernumbering(EnumNumering.LETTERS);
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
            ObjectMapper om = new ObjectMapper();
            om.writerWithDefaultPrettyPrinter().writeValue(outFile, quiz);
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
                actualQuestion.setAnswers(new LinkedList<>());
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

