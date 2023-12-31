package Trello.com.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Tasks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description; // TEXT
    @Column(name = "status")
    private int status = 0; // 0 - todo, 1 - intest, 2 - done, 3 - failed
    @ManyToOne(fetch = FetchType.LAZY)
    private Folders folder; // Many To One
}
