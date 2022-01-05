package study.collector.dto.bookmarkdto;

import lombok.Data;

@Data
public class createBookMarkRequest {

    private String bookmarkName;
    private String url;
    private Long tableId;
}
