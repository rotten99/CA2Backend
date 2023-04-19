package facades;

import dtos.BugReportDTO;
import dtos.DifficultyCheckerDTO;
import entities.BugReport;
import entities.DifficultyChecker;
import errorhandling.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class DifficultyCheckerFacade {

    private static EntityManagerFactory emf;
    private static DifficultyCheckerFacade instance;

    private DifficultyCheckerFacade() {
    }

    /**
     * @param _emf
     * @return the instance of this facade.
     */
    public static DifficultyCheckerFacade getFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new DifficultyCheckerFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    //This method creates an instance of a difficulty checker and returns a dto
    public DifficultyCheckerDTO createDifficultyChecker(DifficultyCheckerDTO difficultyCheckerDTO) {
        EntityManager em = emf.createEntityManager();
        DifficultyChecker difficultyChecker = null;

        TypedQuery<DifficultyChecker> query = em.createQuery("SELECT d FROM DifficultyChecker d WHERE d.countryName = :countryName", DifficultyChecker.class);
        query.setParameter("countryName", difficultyCheckerDTO.getCountryName());
        try {
            difficultyChecker = query.getSingleResult();
        } catch (Exception e) {

            difficultyChecker = new DifficultyChecker(difficultyCheckerDTO.getCountryName(), difficultyCheckerDTO.getFlagURL(), 0);
            try {
                em.getTransaction().begin();
                em.persist(difficultyChecker);
                em.getTransaction().commit();
            } finally {
                em.close();
            }
        }

        return new DifficultyCheckerDTO(difficultyChecker);
    }

    public List<DifficultyCheckerDTO> getAllDifficultyCheckers() {
        List<DifficultyCheckerDTO> difficultyCheckerDTOList = new ArrayList<>();
        EntityManager em = emf.createEntityManager();
        TypedQuery<DifficultyChecker> query = em.createQuery("SELECT d FROM DifficultyChecker d", DifficultyChecker.class);
        List<DifficultyChecker> difficultyCheckers = query.getResultList();
        for (DifficultyChecker difficultyChecker : difficultyCheckers) {
            difficultyCheckerDTOList.add(new DifficultyCheckerDTO(difficultyChecker));
        }
        return difficultyCheckerDTOList;
    }

    public DifficultyCheckerDTO editDifficultyChecker(DifficultyCheckerDTO difficultyCheckerDTO) throws NotFoundException {

        EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("DifficultyChecker.findById");
        query.setParameter("id", difficultyCheckerDTO.getId());
        DifficultyChecker df = (DifficultyChecker) query.getSingleResult();

        try {
            em.getTransaction().begin();
            df.setCountryName(difficultyCheckerDTO.getCountryName());
            df.setFlagURL(difficultyCheckerDTO.getFlagURL());
            df.setTimesNotAnswered(difficultyCheckerDTO.getTimesNotAnswered());
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new NotFoundException("Could not edit bug report");
        } finally {
            em.close();
        }
        return new DifficultyCheckerDTO(df);


    }
}
