package study.collector.service;

import study.collector.dto.bookmarkdto.BookMarkResponse;

import java.util.List;

public interface BookMarkServiceInterface {

    //조회
    List<BookMarkResponse> search(Long userId);
    //생성
    Long create(String name, String url, String imgUrl, Long bookMarkTableId);
    //삭제
    void remove(Long bookMarkId);
    //수정
    Long change(String name, String url, String imgUrl, Long bookMarkId);
}
