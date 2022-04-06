package es.jdl.autoquiz.rest;

import es.jdl.autoquiz.service.QuizService;

//@RestController
public class UserRestService {

    private QuizService service;
/*
    @GetMapping("/user")
    public LocalUser user(@AuthenticationPrincipal OAuth2User principal) {
        return new LocalUser(principal);
    }

    @GetMapping("/user/quizzes")
    public List<Quiz> myQuizzes(@AuthenticationPrincipal OAuth2User principal) {
        LocalUser u = new LocalUser(principal);
        return service.findUserQuiz(u.getLogin());
    }

 */
}
