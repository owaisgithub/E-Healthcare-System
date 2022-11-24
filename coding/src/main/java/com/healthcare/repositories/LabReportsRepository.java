package com.healthcare.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.healthcare.models.LabReports;

@Repository
public interface LabReportsRepository extends JpaRepository<LabReports, Integer> {

}
