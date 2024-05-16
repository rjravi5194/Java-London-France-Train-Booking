package com.train.london.france.booking.repository;

import com.train.london.france.booking.model.BookingDetailsDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookingDetailsRepository extends JpaRepository<BookingDetailsDao, UUID> {

    @Query(value = "select sap.*,dig.status from  public.sap_template_title  AS sap "+
            "INNER JOIN digitized_sap_template AS dig ON sap.id=dig.uuid where dig.status= 5 "+
            "and  (case when(:type IS true) then (dig.is_library_candidate = :type) else (dig.is_library_candidate is not null) end) "+
            "and (sap.description is not null and sap.description <> '' IS not false) "+
            "and dig.is_deleted = false order by sap.date desc limit :limit offset :limit*(:offset-1)", nativeQuery = true)
    List<BookingDetailsDao> getAllFinalizedSaps(int limit, int offset, boolean type);


}
