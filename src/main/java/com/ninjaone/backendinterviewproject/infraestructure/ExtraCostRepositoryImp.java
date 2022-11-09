package com.ninjaone.backendinterviewproject.infraestructure;

import com.ninjaone.backendinterviewproject.domain.extracost.ExtraCostRepository;
import com.ninjaone.backendinterviewproject.domain.extracost.ExtraCost;
import com.ninjaone.backendinterviewproject.domain.extracost.TypeExtraCost;

import java.math.BigDecimal;
import java.util.Optional;

public class ExtraCostRepositoryImp implements ExtraCostRepository {

    @Override
    public Optional<ExtraCost> findByType(TypeExtraCost t) {
        return Optional.of(new ExtraCost(new BigDecimal(4.0), TypeExtraCost.BASE_COST_OBLIGATORY));
    }
}
