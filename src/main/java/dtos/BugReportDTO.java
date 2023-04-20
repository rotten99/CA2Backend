package dtos;

import entities.BugReport;

public class BugReportDTO {
    private Long id;
    private String description;
    private Boolean isFixed;

    public BugReportDTO() {
    }

    public BugReportDTO(BugReport bugReport) {
        this.id = bugReport.getId();
        this.description = bugReport.getDescription();
        this.isFixed = bugReport.getIsFixed();
    }

    public BugReportDTO(String description, Boolean isFixed) {
        this.description = description;
        this.isFixed = isFixed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getFixed() {
        return isFixed;
    }

    public void setFixed(Boolean fixed) {
        isFixed = fixed;
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
}
