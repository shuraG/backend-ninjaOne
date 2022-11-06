package com.ninjaone.backendinterviewproject.domain.rmmservice;

public interface RepositoryRMMService {

    void save(RMMService e);

    RMMService getById(Long id);

}
