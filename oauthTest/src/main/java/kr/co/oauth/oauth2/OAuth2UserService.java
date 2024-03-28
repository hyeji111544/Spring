package kr.co.oauth.oauth2;

import kr.co.oauth.entity.User;
import kr.co.oauth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    // 사용자 정보가 넘어오는 곳

        String accessToken = userRequest.getAccessToken().getTokenValue(); //카카오에서 오는 토큰
        log.info("loadUser....1 :" + accessToken);

        String provider = userRequest.getClientRegistration().getRegistrationId();
        log.info("loadUser....2 :" + provider);

        OAuth2User oauth2User = super.loadUser(userRequest);
        log.info("loadUser....3 : " + oauth2User);

        Map<String , Object> attributes = oauth2User.getAttributes();
        log.info("loadUser...4 : " + attributes);

        //KakaoInfo kakaoInfo = new KakaoInfo(attributes);

        // 회원가입 처리
        /*
        String nick = kakaoInfo.getNickName();
        String image = kakaoInfo.getProfileImage();
        String pro = kakaoInfo.getProvider();

        User user = User.builder()
                        .uid(pro+"_"+nick)
                        .name(nick)
                        .build();
        userRepository.save(user);

        return user;

        */
        // 회원가입 처리
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");



        // 사용자 확인
        Optional<User> optUser = userRepository.findById(email);

        User user = null;

        if(optUser.isEmpty()){
            // 회원가입
            user = User.builder()
                    .uid(email)
                    .name(name)
                    .provider(provider)
                    .build();

            userRepository.save(user);

        }else{
            user = optUser.get();
        }
        return user;
    }
}
