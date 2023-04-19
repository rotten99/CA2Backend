package facades;

import dtos.BugReportDTO;
import entities.BugReport;
import errorhandling.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class BugReportFacade {

    private static EntityManagerFactory emf;
    private static BugReportFacade instance;

    private BugReportFacade() {
    }

    /**
     * @param _emf
     * @return the instance of this facade.
     */
    public static BugReportFacade getBugReportFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new BugReportFacade();
        }
        return instance;
    }

    private static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    //This method is used to create a new bug report
    public static BugReportDTO createBugReport(BugReportDTO bugReportDTO) {
        BugReport bugReport = new BugReport(bugReportDTO.getDescription(), bugReportDTO.getIsFixed());

        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(bugReport);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new BugReportDTO(bugReport);
    }

    //This method is used to edit a bug report
    public static BugReportDTO editBugReport(BugReportDTO bugReportDTO) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("BugReport.findById");
        query.setParameter("id", bugReportDTO.getId());
        BugReport bugReport = (BugReport) query.getSingleResult();

        try {
            em.getTransaction().begin();
            bugReport.setDescription(bugReportDTO.getDescription());
            bugReport.setIsFixed(bugReportDTO.getIsFixed());
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new NotFoundException("Could not edit bug report");
        } finally {
            em.close();
        }
        return new BugReportDTO(bugReport);
    }

    //This method is used to get all bug reports
    public static List<BugReportDTO> getAllBugReports() {
        List<BugReportDTO> bugReportDTOs = new ArrayList<>();
        EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("BugReport.findAll");
        List<BugReport> resultList = (List<BugReport>) query.getResultList();
        for (BugReport br : resultList) {
            bugReportDTOs.add(new BugReportDTO(br));
        }
        return bugReportDTOs;
    }
}
