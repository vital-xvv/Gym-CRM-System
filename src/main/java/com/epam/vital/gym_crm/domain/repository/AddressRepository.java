package com.epam.vital.gym_crm.domain.repository;

import com.epam.vital.gym_crm.domain.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
