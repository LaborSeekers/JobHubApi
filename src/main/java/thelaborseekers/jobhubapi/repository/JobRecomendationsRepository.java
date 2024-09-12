package thelaborseekers.jobhubapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import thelaborseekers.jobhubapi.model.entity.JobModality;
import thelaborseekers.jobhubapi.model.entity.JobOffer;

import java.util.List;

public interface JobRecomendationsRepository extends JpaRepository<JobOffer, Integer> {
    @Query("SELECT j FROM JobOffer j WHERE ABS(j.salary - :salary) <= 10000 ")
    List<JobOffer> findSimilarJobs(@Param("salary") long salary);

}
