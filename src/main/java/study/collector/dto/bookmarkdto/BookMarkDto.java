package study.collector.dto.bookmarkdto;

import lombok.Data;
import study.collector.entity.BookMark;

@Data
public class BookMarkDto {
    private Long bookmarkId;
    private String name;
    private String url;

    public BookMarkDto(BookMark bookMark) {
        this.bookmarkId = bookMark.getId();
        this.name = bookMark.getName();
        this.url = bookMark.getUrl();
    }
}
