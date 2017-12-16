package es.jdl.autoquiz.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.jdl.autoquiz.domain.Answer;
import es.jdl.autoquiz.domain.EnumNumering;
import es.jdl.autoquiz.domain.EnumQuestionType;
import es.jdl.autoquiz.domain.Question;
import es.jdl.autoquiz.domain.Quiz;
import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ConvertMoodleXML {
    public static void main(String[] args) throws Exception {
        ObjectMapper om = new ObjectMapper();
        File sourcePath = new File(args[0]);
        File targetPath = new File(args[1]);
        targetPath.mkdirs();
        List<Quiz> quizList = new ArrayList<>();
        for (File f: sourcePath.listFiles()) {
            if (f.getName().endsWith(".xml")) {
                Quiz q = createQuiz(f);
                File out = new File(targetPath, q.getId());
                om.writerWithDefaultPrettyPrinter().writeValue(out, q);
                // temp, only desc data
                Quiz quizName = new Quiz();
                quizName.setId(q.getId());
                quizName.setTitle(q.getTitle());
                quizList.add(quizName);
                System.out.println("Quiz created: " + q.getTitle() + " questions: " + q.getQuestions().size());
            }
        } // end for
        om.writerWithDefaultPrettyPrinter().writeValue(new File(targetPath, "quiz_list.json"), quizList);
    }

    private static String removeHtml(String html) {
        return Jsoup.parse(html).text();
    }

    private static String getFirstChildTextValue(Element parent, String tagName) {
        Element elemTag = getFirstElementByTagName(parent, tagName);
        if (elemTag != null)
            return elemTag.getTextContent();
        else
            return null;
    }

    private static Element getFirstElementByTagName(Element parent, String tagName) {
        if (parent != null) {
            NodeList nl = parent.getElementsByTagName(tagName);
            if (nl != null && nl.getLength() > 0) {
                // test element?
                return (Element) nl.item(0); // first
            }
        }
        return null;
    }

    private static Quiz createQuiz(File f) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document dom = db.parse(f);

        Quiz quiz = new Quiz();
        Element quizinfo = getFirstElementByTagName(dom.getDocumentElement(), "quizinfo");
        if (quizinfo != null) {
            quiz.setId(UUID.randomUUID().toString());
            quiz.setTitle(getFirstChildTextValue(quizinfo, "title"));
            quiz.setInstructions(getFirstChildTextValue(quizinfo, "instructions"));
            // todo: author
            quiz.setQuestions(new ArrayList<Question>());
            quiz.setSuffleQuestions(true);
            quiz.setPassFraction(80);
        }
        NodeList nlQuestions = dom.getElementsByTagName("question");
        for (int i = 0; i < nlQuestions.getLength(); i++) {
            Element questionElem = (Element) nlQuestions.item(i);
            Question q = new Question();
            q.setId(String.valueOf(i+1));
            q.setType(EnumQuestionType.multichoice);
            q.setAnswernumbering(EnumNumering.LETTERS);
            q.setShuffleanswers(true);
            q.setSingle(true);
            q.setText(removeHtml(questionElem.getTextContent()));
            q.setAnswers(new ArrayList<Answer>());
            NodeList nlAnswers = ((Element)questionElem.getParentNode()).getElementsByTagName("answer");
            for (int j = 0; j < nlAnswers.getLength(); j++) {
                Element answerElem = (Element) nlAnswers.item(j);
                Answer answer = new Answer();
                answer.setText(removeHtml(answerElem.getTextContent()));
                if ("true".equalsIgnoreCase(answerElem.getParentNode().getAttributes().getNamedItem("correct").getTextContent())) {
                    answer.setFraction(100);
                    answer.setFeedback("Correcto");
                } else {
                    answer.setFraction(0);
                    answer.setFeedback("Error");
                }
                q.getAnswers().add(answer);
            } // for answers
            quiz.getQuestions().add(q);
        } // for questions

        return quiz;
    }



}
