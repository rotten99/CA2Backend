package entities;

import javax.persistence.*;

@Entity
@Table(name = "bug_report")
@NamedQueries({
        @NamedQuery(name = "BugReport.findById", query = "select b from BugReport b where b.id = :id"),
        @NamedQuery(name = "BugReport.findAll", query = "select b from BugReport b"),
        @NamedQuery(name = "BugReport.deleteAllRows", query = "delete from BugReport")
})
public class BugReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "is_fixed")
    private Boolean isFixed;

    public BugReport() {
    }

    public BugReport(String description, Boolean isFixed) {
        this.description = description;
        this.isFixed = isFixed;
    }

    public Boolean getIsFixed() {
        return isFixed;
    }

    public void setIsFixed(Boolean isFixed) {
        this.isFixed = isFixed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}