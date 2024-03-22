package kr.co.sboard.controller;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.sboard.config.AppInfo;
import kr.co.sboard.dto.ArticleDTO;
import kr.co.sboard.dto.PageRequestDTO;
import kr.co.sboard.dto.PageResponseDTO;
import kr.co.sboard.entity.Article;
import kr.co.sboard.service.ArticleService;
import kr.co.sboard.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ArticleController {

    private final ArticleService articleService;
    private final AppInfo appInfo;

    /*
        @ModelAttribute("cate")
         - modelAttribute("cate", cate)와 동일
    */
    @GetMapping("/article/list")
    public String list(Model model, PageRequestDTO pageRequestDTO, @ModelAttribute("cate") String cate) {
        PageResponseDTO pageResponseDTO = articleService.findByParentAndCate(pageRequestDTO);

        log.info("pageResponseDTO : " + pageResponseDTO);

        model.addAttribute(pageResponseDTO);

        return "/article/list";
    }

    @GetMapping("/article/write")
    public String write(@ModelAttribute("cate") String cate){
        log.info("write ...:" + cate);
        return "/article/write";
    }

    @PostMapping("/article/write")
    public String write(HttpServletRequest req, ArticleDTO articleDTO ,Model model){
        /*
            글작성을 테스트할 때는 로그인해야하기 때문에
            SecurityConfig 인가 설정 수정할 것
        */
        String regip = req.getRemoteAddr();
        articleDTO.setRegip(regip);

        log.info(articleDTO.toString());
        String cate = articleDTO.getCate();


       Article article= articleService.insertArticle(articleDTO);
       int no =article.getNo();

        return "redirect:/article/view?no="+no+"&cate="+cate;
    }

    @GetMapping("/article/view")
    public String view(int no , int pg, Model model){
        ArticleDTO articleDTO = articleService.findById(no);
        model.addAttribute(articleDTO);
        model.addAttribute("pg", pg);
        log.info(articleDTO.toString());
        return "/article/view";
    }

    // fileDownload 메서드 FileController 로 이동

}