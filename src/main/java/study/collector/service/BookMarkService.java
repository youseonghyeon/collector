package study.collector.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.collector.entity.BookMark;
import study.collector.entity.User;
import study.collector.repository.BookMarkRepository;
import study.collector.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookMarkService {

    private final BookMarkRepository bookMarkRepository;
    private final UserRepository userRepository;

    // 북마크 조회
    public List<BookMark> searchById(Long userId) {
        return bookMarkRepository.findAllByUserId(userId);
    }

    //북마크 추가
    @Transactional
    public Long create(String name, String url, String imgUrl, String category, Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            BookMark bookMark = new BookMark(name, url, imgUrl, category, optionalUser.get());
            bookMarkRepository.save(bookMark);
            return bookMark.getId();
        } else {
            return 0L;
        }
    }

    //북마크 삭제
    @Transactional
    public void remove(Long bookMarkId) {
        bookMarkRepository.deleteById(bookMarkId);
    }

    //북마크 수정
    @Transactional
    public Long change(String name, String url, String imgUrl, String category, Long bookMarkId) {
        Optional<BookMark> optionalBookMark = bookMarkRepository.findById(bookMarkId);
        if (optionalBookMark.isPresent()) {
            BookMark bookMark = optionalBookMark.get();
            bookMark.changeBookMark(name, url, imgUrl, category);
            return bookMarkId;
        } else {
            return 0L;
        }
    }
}
