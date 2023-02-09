package teamproject.pocoapoco.controller.main.ui;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import teamproject.pocoapoco.domain.dto.mail.UserMailResponse;
import teamproject.pocoapoco.domain.dto.response.Response;
import teamproject.pocoapoco.domain.dto.user.UserJoinRequest;
import teamproject.pocoapoco.domain.dto.user.UserLoginRequest;
import teamproject.pocoapoco.domain.dto.user.UserLoginResponse;
import teamproject.pocoapoco.service.MailService;
import teamproject.pocoapoco.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ViewController {

    private final UserService userService;
    private final MailService mailService;

    @PostMapping("/view/v1/signup")
    public String signup(UserJoinRequest userJoinRequest) {

        userService.saveUser(userJoinRequest);
        return "redirect:/view/v1/start";
    }

    @PostMapping("/view/v1/signin")
    public String login(UserLoginRequest userLoginRequest, HttpServletResponse response) throws UnsupportedEncodingException {

        UserLoginResponse tokens = userService.login(userLoginRequest);

        //cookie 설정은 스페이스가 안되기 때문에 Bearer 앞에 +를 붙인다. Security Filter에서 + -> " " 로 치환할 것이다.
        Cookie cookie = new Cookie("jwt", "Bearer+" + tokens.getAccessToken());


        cookie.setPath("/");
//        cookie.setSecure(true);
        //변경요소

        cookie.setHttpOnly(true);
        cookie.setMaxAge(60 * 25); //초단위 25분설정
        response.addCookie(cookie);

        return "redirect:/view/v1/crews";
    }

    @GetMapping("/view/v1/start")
    public String testForm(Model model) {
        model.addAttribute("userLoginRequest", new UserLoginRequest());
        model.addAttribute("userJoinRequest", new UserJoinRequest());
        return "start/start";
    }
    @GetMapping("/view/v1/test")
    public String test(Model model) {
        return "test/test";
    }

    @GetMapping("")
    public String oauthLogin() {
        return "redirect:/view/v1/crews";
    }

    @GetMapping("/view/v1/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        Cookie cookie = new Cookie("jwt", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        return "redirect:/view/v1/crews";
    }
    @PostMapping("/login/mailConfirm")
    @ResponseBody
    public Response mailConfirm(@RequestParam("email") String email) throws Exception {

        UserMailResponse userMailResponse = mailService.sendSimpleMessage(email);
        System.out.println("인증코드 : " + userMailResponse.getCode());
        return Response.success(userMailResponse);
    }
    @PostMapping("/login/verifyCode")
    @ResponseBody
    public int verifyCode(@RequestParam("code") String code) {
        int result = 0;
        System.out.println("code : "+code);
        System.out.println("code match : "+ mailService.ePw.equals(code));
        if(mailService.ePw.equals(code)) {
            result =1;
        }
        return result;
    }
}
