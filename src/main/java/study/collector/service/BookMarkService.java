package study.collector.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.collector.repository.BookMarkRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookMarkService {

    private final BookMarkRepository bookMarkRepository;

    //북마크 추가
    @Transactional
    public Long create() {
        return null;
    }
}
