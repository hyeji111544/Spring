package kr.co.sboard.controller;

import kr.co.sboard.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class FileController {

    private final FileService fileService;

    @GetMapping("/file/fileDownload/{fno}")
    public ResponseEntity<?> fileDownload(@PathVariable("fno") int fno){
        log.info("fileDownload : " + fno);
        return fileService.fileDownload(fno);
    }

    @GetMapping("/file/downloadCount/{fno}")
    public ResponseEntity<?> fileDownloadCount(@PathVariable("fno") int fno){
        log.info("fileDownloadCount : " + fno);
        return fileService.fileDownloadCount(fno);
    }

    @PostMapping("/file/modifyFile")
    public void deleteFile(@RequestBody Map<String , List<Integer>> map){
        log.info("여기!!!!!!!!!!!");
        List<Integer> fnolists =  map.get("fno");
        log.info(fnolists.get(0).toString());
        Integer ano = null;
        // 파일 갯수
        int count =0;
        for(Integer fno : fnolists){
            ano = fileService.deleteFile(fno);
            if(ano != null){
                count++;
            }
        }
        //serviceArticle. file 컬럼 마이너스 로직 호출.

    }


}
