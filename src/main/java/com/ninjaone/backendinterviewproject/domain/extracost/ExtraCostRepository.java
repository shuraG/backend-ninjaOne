package com.ninjaone.backendinterviewproject.domain.extracost;

import java.util.Optional;

public interface ExtraCostRepository {
    Optional<ExtraCost> findByType(TypeExtraCost t);
}
