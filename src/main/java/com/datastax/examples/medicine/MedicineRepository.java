package com.datastax.examples.medicine;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RestResource(exported = false)
public interface MedicineRepository extends CassandraRepository<Medicine, UUID> {
}

