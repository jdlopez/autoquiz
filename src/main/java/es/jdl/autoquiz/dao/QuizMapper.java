package es.jdl.autoquiz.dao;

import es.jdl.autoquiz.domain.Answer;
import es.jdl.autoquiz.domain.NameCounter;
import es.jdl.autoquiz.domain.Question;
import es.jdl.autoquiz.domain.Quiz;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface QuizMapper {

    @Select("select id, title, instructions from quiz where showPublic = true")
    List<Quiz> selectQuizPublic();

    @Insert("insert into quiz (id, title, instructions, showPublic, showCorrect, suffleQuestions, " +
            "passFraction, answernumbering, shuffleanswers, owner) " +
            "values (" +
            "#{id}, #{title}, #{instructions}, #{showPublic}, #{showCorrect}, #{suffleQuestions}, " +
            "#{passFraction}, #{answernumbering}, #{shuffleanswers}, #{owner}" +
            ")")
    void insertQuiz(Quiz quiz);

    @Insert("insert into question " +
            "(id,quizId,text,type,single,answernumbering,shuffleanswers) " +
            "values " +
            "(#{id},#{quizId},#{text},#{type},#{single},#{answernumbering},#{shuffleanswers})")
    void insertQuestion(Question question);

    @Insert("insert into answer " +
            "(id,quizId,questionId,fraction,text,feedback) " +
            "values " +
            "(#{id},#{quizId},#{questionId},#{fraction},#{text},#{feedback})")
    int insertAnswer(Answer answer);

    @Select("select * from quiz where id = #{id}")
    Quiz selectQuizById(String id);

    @Select("select q.* from quiz q join quiz_tag qt on q.id = qt.quizId where qt.tagName = #{tag}")
    List<Quiz> selectQuizByTag(String tag);

    @Insert("insert into tag (name, description) values (#{name}, #{description})")
    int insertTag(@Param("name") String name, @Param("description") String description);

    @Insert("insert into quiz_tag (quizId, tagName) values (#{quizId}, #{tag})")
    int insertQuizTag(@Param("quizId") String quizId, @Param("tag") String tag);

    @Insert("insert into question_tag (quizId, questionId, tagName) values (#{quizId}, #{questionId}, #{tag})")
    int insertQuestionTag(@Param("quizId") String quizId, @Param("questionId") String questionId, @Param("tag") String tag);

    @Update("update answer set fraction = #{fraction}, feedback = #{feedback} " +
            "where questionId = #{questionId} and quizId = #{quizId} ")
    int updateAllAnswersFromQuestion(@Param("quizId") String quizId,
                                      @Param("questionId") String questionId,
                                      @Param("fraction") int fraction,
                                      @Param("feedback") String feedback);

    @Update("update answer set fraction = #{fraction}, feedback = #{feedback} " +
            "where questionId = #{questionId} and quizId = #{quizId} and id = #{answerId}")
    int updateSingleAnswer(@Param("quizId") String quizId,
                            @Param("questionId") String questionId,
                            @Param("answerId") String answerId,
                            @Param("fraction") int fraction,
                            @Param("feedback") String feedback);

    @Select("select tagName as name, count(*) as counter from quiz_tag group by tagName ")
    List<NameCounter> selectQuizCountGroupByTag();

    @Select("select * from quiz where owner = #{userName}")
    List<Quiz> selectQuizByUser(String userName);

    @Select("select distinct q.* from question q " +
            " left join question_tag qt on q.quizId = qt.quizId and q.id = qt.questionId " +
            " left join quiz_tag zt on zt.quizId = q.quizId " +
            " where qt.tagName = #{tag} or zt.tagName = #{tag}")
    List<Question> selectQuestionByTag(String tag);

    @Select("select * from answer where quizId = #{quizId} and questionId = #{questionId}")
    List<Answer> selectAnsers(String quizId, String questionId);

    @Select("select * from question where quizId = #{quizId} and id = #{questionId}")
    Question selectQuestionById(String quizId, String questionId);

    @Delete({
            "delete from answer where quizId = #{quizId} and questionId = #{questionId}; ",
            "delete from question where quizId = #{quizId} and id = #{questionId}"
    })
    int deleteFullQuestion(String quizId, String questionId);
}
