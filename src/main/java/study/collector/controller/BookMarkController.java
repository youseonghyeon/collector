package study.collector.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import study.collector.entity.BookMark;
import study.collector.repository.BookMarkRepository;
import study.collector.service.BookMarkService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class BookMarkController {

    private final BookMarkRepository bookMarkRepository;
    private final BookMarkService bookMarkService;

    /**
     * 북마크 조회
     * @param = Long userId
     * @return = Long bookMarkId, String name, String url, String imgUrl, String category
     */
    @PostMapping("/bookmark/search")
    public Result getBookMark(@RequestBody GetBookMarkRequest request) {
        List<BookMark> bookMarks = bookMarkService.searchById(request.getUserId());
        List<SearchBookMarkResponse> responses = simpleBookMarkMapping(bookMarks);

        return new Result(responses);
    }

    @Data
    static class GetBookMarkRequest {
        private Long userId;
    }

    @Data
    static class SearchBookMarkResponse {
        private Long bookMarkId;
        private String name;
        private String url;
        private String imgUrl;
        private String category;

        public SearchBookMarkResponse(Long bookMarkId, String name, String url, String imgUrl, String category) {
            this.bookMarkId = bookMarkId;
            this.name = name;
            this.url = url;
            this.imgUrl = imgUrl;
            this.category = category;
        }
    }

    public List<SearchBookMarkResponse> simpleBookMarkMapping(List<BookMark> bookMarks) {
        return bookMarks.stream()
                .map(b -> new SearchBookMarkResponse(b.getId(), b.getName(), b.getUrl(), b.getImgUrl(), b.getCategory()))
                .collect(Collectors.toList());
    }


    /**
     * 북마크 추가
     * @param = Long userId, String name, String url, String imgUrl, String category
     * @return = Long bookMarkId, String name, String url, String imgUrl, String category
     */
    @PostMapping("/bookmark/create")
    public Result createBookMark(@RequestBody CreateBookMarkRequest request) {
        bookMarkService.create(request.getName(), request.getUrl(), request.getImgUrl(), request.getCategory(), request.getUserId());

        List<BookMark> bookMarks = bookMarkService.searchById(request.getUserId());
        List<SearchBookMarkResponse> responses = simpleBookMarkMapping(bookMarks);

        return new Result(responses);
    }

    @Data
    static class CreateBookMarkRequest {
        private Long userId;
        private String name;
        private String url;
        private String imgUrl;
        private String category;
    }


    /**
     * 북마크 삭제
     * @param = Long userId, Long bookMarkId
     * @return = Long bookMarkId, String name, String url, String imgUrl, String category
     */
    @PostMapping("/bookmark/remove")
    public Result removeBookMark(@RequestBody removeBookMarkRequest request) {
        bookMarkService.remove(request.getBookMarkId());

        List<BookMark> bookMarks = bookMarkService.searchById(request.getUserId());
        List<SearchBookMarkResponse> responses = simpleBookMarkMapping(bookMarks);

        return new Result(responses);
    }

    @Data
    static class removeBookMarkRequest {
        private Long userId;
        private Long bookMarkId;
    }

    /**
     * 북마크 수정
     * @param = Long userId, Long bookMarkId, String name, String url, String imgUrl, String category
     * @return = Long bookMarkId, String name, String url, String imgUrl, String category
     */
    @PostMapping("/bookmark/revise")
    public Result removeBookMark(@RequestBody ReviseBookMarkRequest request) {
        bookMarkService.change(request.getName(), request.getUrl(), request.getImgUrl(), request.getCategory(), request.getBookMarkId());

        List<BookMark> bookMarks = bookMarkService.searchById(request.getUserId());
        List<SearchBookMarkResponse> responses = simpleBookMarkMapping(bookMarks);

        return new Result(responses);
    }

    @Data
    static class ReviseBookMarkRequest {
        private Long userId;
        private Long bookMarkId;
        private String name;
        private String url;
        private String imgUrl;
        private String category;
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
}
