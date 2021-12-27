package study.collector.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Schedule {

    @Id
    @GeneratedValue
    @Column(name = "schedule_id")
    private Long id;

//    @JsonFormat(pattern="yyyy-MM-dd")
//    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalDate date;
    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Schedule(LocalDate date, String content, User user) {
        this.date = date;
        this.content = content;
        if (user != null) {
            assignUser(user);
        }
    }

    public void assignUser(User user) {
        this.user = user;
        user.getSchedules().add(this);
    }
}
