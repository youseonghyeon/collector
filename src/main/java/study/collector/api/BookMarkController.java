package study.collector.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.collector.dto.bookmarkdto.*;
import study.collector.service.BookMarkService;
import study.collector.session.SessionManager;
import study.collector.session.SessionUserDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/bookmark")
public class BookMarkController {

    private final BookMarkService bookMarkService;
    private final SessionManager sessionManager;

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
        bookMarkService.create(bookMarkRequest.getBookmarkName(), bookMarkRequest.getUrl(), null, bookMarkRequest.getTableId());

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

    /**
     * 북마크 삭제
     *
     * @param = Long userId, Long bookMarkId
     * @return = Long bookMarkId, String name, String url, String imgUrl, String category
     */
//    @PostMapping("/remove")
//    public Result removeBookMark(@RequestBody removeBookMarkRequest request) {
//        bookMarkService.remove(request.getBookMarkId());
//
//        List<BookMark> bookMarks = bookMarkService.searchById(request.getUserId());
//        List<SearchBookMarkResponse> responses = simpleBookMarkMapping(bookMarks);
//
//        return new Result(responses);
//    }

//    @Data
//    static class removeBookMarkRequest {
//        private Long userId;
//        private Long bookMarkId;
//    }

    /**
     * 북마크 수정
     *
     * @param = Long userId, Long bookMarkId, String name, String url, String imgUrl, String category
     * @return = Long bookMarkId, String name, String url, String imgUrl, String category
     */
//    @PostMapping("/revise")
//    public Result removeBookMark(@RequestBody ReviseBookMarkRequest request) {
//        bookMarkService.change(request.getName(), request.getUrl(), request.getImgUrl(), request.getCategory(), request.getBookMarkId());
//
//        List<BookMark> bookMarks = bookMarkService.searchById(request.getUserId());
//        List<SearchBookMarkResponse> responses = simpleBookMarkMapping(bookMarks);
//
//        return new Result(responses);
//    }
//
//    @Data
//    static class ReviseBookMarkRequest {
//        private Long userId;
//        private Long bookMarkId;
//        private String name;
//        private String url;
//        private String imgUrl;
//        private String category;
//    }
//
    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
}
