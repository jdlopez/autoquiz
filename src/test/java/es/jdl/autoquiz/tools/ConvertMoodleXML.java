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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;

public class ConvertMoodleXML {
    public static void main(String[] args) throws Exception {
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document dom = db.parse("c:\\desarrollo\\workspace\\autoquiz\\src\\test\\resources\\quiz.xml");

        Quiz quiz = new Quiz();
        Element quizinfo = getFirstElementByTagName(dom.getDocumentElement(), "quizinfo");
        if (quizinfo != null) {
            quiz.setTitle(getFirstChildTextValue(quizinfo, "title"));
            quiz.setInstructions(getFirstChildTextValue(quizinfo, "instructions"));
            // todo: author
            quiz.setQuestions(new ArrayList<Question>());
        }
        NodeList nlQuestions = dom.getElementsByTagName("question");
        for (int i = 0; i < nlQuestions.getLength(); i++) {
            Element questionElem = (Element) nlQuestions.item(i);
            Question q = new Question();
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

        // out generation:
        ObjectMapper om = new ObjectMapper();
        System.out.println(om.writerWithDefaultPrettyPrinter().writeValueAsString(quiz));
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

}
