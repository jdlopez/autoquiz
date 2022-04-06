package es.jdl.autoquiz.domain;

import lombok.Getter;
import lombok.ToString;
//import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

@Getter @ToString
public class LocalUser {

    private /*final*/ String name;
    private /*final*/ String avatarUrl;
    private /*final*/ String id;
    private /*final*/ String login;
    private /*final*/ String email;

//    public LocalUser(OAuth2User principal) {
//        Map<String, Object> attrs = principal.getAttributes();
//        this.id = attrs.containsKey("id")?attrs.get("id").toString():null; // seems like Integer
//        this.login = (String) attrs.get("login");
//        this.name = (String) (attrs.containsKey("name")?attrs.get("name"):attrs.get("login"));
//        this.avatarUrl = (String) attrs.get("avatar_url");
//        this.email = (String) attrs.get("email");
//    }
}
