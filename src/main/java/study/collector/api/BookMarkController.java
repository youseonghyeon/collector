package study.collector.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.collector.dto.bookmarkdto.*;
import study.collector.entity.BookMark;
import study.collector.entity.BookMarkTable;
import study.collector.repository.BookMarkRepository;
import study.collector.repository.BookMarkTableRepository;
import study.collector.service.BookMarkService;
import study.collector.session.SessionManager;
import study.collector.session.SessionUserDto;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/bookmark")
public class BookMarkController {

    private final BookMarkService bookMarkService;
    private final SessionManager sessionManager;
    private final BookMarkTableRepository tableRepository;
    private final EntityManager em;

    @PostMapping("/search") // 검색
    public Result getBookMark(HttpServletRequest request) {
        SessionUserDto sessionUser = sessionManager.getUserFromSession(request);
        List<BookMarkResponse> bookMarkTableDtos = bookMarkService.search(sessionUser.getId());
        return new Result(bookMarkTableDtos);
    }

    @PostMapping("/table/create")
    public Result createBookMarkTable(@RequestBody BookMarkTableDto tableDto, HttpServletRequest request) {
        SessionUserDto sessionUser = sessionManager.getUserFromSession(request);
        // 북마크 테이블 저장
        bookMarkService.createTable(tableDto.getName(), sessionUser.getId());

        List<BookMarkResponse> bookMarkTableDtos = bookMarkService.search(sessionUser.getId());
        return new Result(bookMarkTableDtos);
    }

    @PostMapping("/table/remove")
    public Result removeBookMarkTable(@RequestBody TableIdRequest tableRequest, HttpServletRequest request) {
        SessionUserDto sessionUser = sessionManager.getUserFromSession(request);
        bookMarkService.removeTable(tableRequest.getTableId(), sessionUser.getId());

        List<BookMarkResponse> bookMarkTableDtos = bookMarkService.search(sessionUser.getId());
        return new Result(bookMarkTableDtos);
    }

    @PostMapping("/bookmark/create")
    public Result createBookMark(@RequestBody createBookMarkRequest bookMarkRequest, HttpServletRequest request) {
        SessionUserDto sessionUser = sessionManager.getUserFromSession(request);
        bookMarkService.create(bookMarkRequest.getBookmarkName(), bookMarkRequest.getUrl(), bookMarkRequest.getTableId());

        List<BookMarkResponse> bookMarkTableDtos = bookMarkService.search(sessionUser.getId());
        return new Result(bookMarkTableDtos);
    }

    //북마크 제거
    @PostMapping("/bookmark/remove")
    public Result removeBookMark(@RequestBody removeBookMarkRequest removeRequest, HttpServletRequest request) {
        SessionUserDto sessionUser = sessionManager.getUserFromSession(request);
        log.info("bookMarkDto={}", removeRequest.getBookmarkId());
        bookMarkService.remove(removeRequest.getBookmarkId());

        List<BookMarkResponse> bookMarkTableDtos = bookMarkService.search(sessionUser.getId());
        return new Result(bookMarkTableDtos);
    }

    // 테이블 이름 변경
    @PostMapping("/table/change")
    @Transactional
    public void changeTableName(@RequestBody ChangeTableRequest tableRequest, HttpServletRequest request) {
        SessionUserDto sessionUser = sessionManager.getUserFromSession(request);
        Optional<BookMarkTable> optionalTable = tableRepository.findById(tableRequest.getTableId());
        if (optionalTable.isPresent()) {
            log.info("tableRequest.getTitle()={}", tableRequest.getTitle());
            BookMarkTable bookMarkTable = optionalTable.get();
            bookMarkTable.changeTableName(tableRequest.getTitle());
        }
    }



    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
}
