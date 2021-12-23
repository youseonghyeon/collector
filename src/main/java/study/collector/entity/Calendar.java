package study.collector.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Calendar {

    @Id
    @GeneratedValue
    @Column(name = "calendar_id")
    private Long id;

    private LocalDateTime date;
    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Calendar(LocalDateTime date, String content, User user) {
        this.date = date;
        this.content = content;
        if (user != null) {
            assignUser(user);
        }
    }

    public void assignUser(User user) {
        this.user = user;
        user.getCalendars().add(this);
    }
}
