package study.collector.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.collector.dto.bookmarkdto.BookMarkDto;
import study.collector.dto.bookmarkdto.BookMarkResponse;
import study.collector.entity.BookMark;
import study.collector.entity.BookMarkTable;
import study.collector.entity.User;
import study.collector.exception.customexception.BookMarkException;
import study.collector.repository.BookMarkRepository;
import study.collector.repository.BookMarkTableRepository;
import study.collector.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookMarkService implements BookMarkServiceInterface {

    private final BookMarkRepository bookMarkRepository;
    private final BookMarkTableRepository tableRepository;
    private final UserRepository userRepository;
    private final JPAQueryFactory queryFactory;


    // 북마크 조회
    public List<BookMarkResponse> search(Long userId) {
        return findAndConvBookMark(userId);
    }

    //북마크 테이블 추가
    @Transactional
    public Long createTable(String name, Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            BookMarkTable bookMarkTable = new BookMarkTable(name, optionalUser.get());
            // 테이블이 유저의 테이블인지 검증
            if (bookMarkTable.getUser().getId().equals(userId)) {
                BookMarkTable saveTable = tableRepository.save(bookMarkTable);
                return saveTable.getId();
            }
        }
        return null;
    }

    //북마크 테이블 제거
    @Transactional
    public Long removeTable(Long tableId, Long userId) {
        // 회원의 테이블인지 검증 로직을 만들어 주세요 TODO
        tableRepository.deleteById(tableId);
        return null;
    }

    //북마크 추가
    @Transactional
    public Long create(String name, String url, Long bookMarkTableId) {
        Optional<BookMarkTable> optionalTable = tableRepository.findById(bookMarkTableId);
        if (optionalTable.isPresent()) {
            BookMark saveBookmark = bookMarkRepository.save(new BookMark(name, url, optionalTable.get()));
            return saveBookmark.getId();
        } else {
            throw new BookMarkException("북마크 저장 실패");
        }
    }

    //북마크 삭제
    @Transactional
    public void remove(Long bookMarkId) {
        bookMarkRepository.deleteById(bookMarkId);
    }

    //북마크 수정
    @Transactional
    public Long change(String name, String url, Long bookMarkId) {
        Optional<BookMark> optionalBookMark = bookMarkRepository.findById(bookMarkId);
        if (optionalBookMark.isPresent()) {
            BookMark bookMark = optionalBookMark.get();
            bookMark.changeBookMark(name, url);
            return bookMarkId;
        } else {
            throw new BookMarkException("해당 북마크가 존재하지 않습니다.");
        }
    }


    // 북마크 조회 & 변환 로직
    public List<BookMarkResponse> findAndConvBookMark(Long userId) {
        List<BookMarkTable> findTables = tableRepository.findByUserId(userId);

        List<BookMarkResponse> responseList = new ArrayList<>();

        for (BookMarkTable findTable : findTables) {
            List<BookMarkDto> bookmarks = findTable.getBookMarks().stream()
                    .map(BookMarkDto::new)
                    .collect(Collectors.toList());

            responseList.add(new BookMarkResponse(findTable.getId(), findTable.getTableName(), bookmarks));
        }
        return responseList;
    }


}


