package kr.co.oauth.oauth2;

import lombok.AllArgsConstructor;

import java.awt.*;
import java.util.Map;
import java.util.Objects;

@AllArgsConstructor
public class KakaoInfo {

    private Map<String, Object> attributes;

    public String getId(){
        return attributes.get("id").toString();
    }

    public String getProvider(){
        return "kakao";
    }

    public String getNickName(){
        Map<?, ?> map = (Map<?, ?>) attributes.get("properties");
        String nickname=(String) map.get("nickname");
        return nickname;
    }

    public String getProfileImage(){
        Map<?, ?> map = (Map<?, ?>) attributes.get("properties");
        String image = (String) map.get("image");
        return image;
    }
}
