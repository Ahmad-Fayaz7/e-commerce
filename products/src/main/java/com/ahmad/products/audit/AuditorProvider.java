package com.ahmad.products.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component("auditorProvider")
public class AuditorProvider implements AuditorAware {
    @Override
    public Optional getCurrentAuditor() {
        return Optional.of("Mr.Auditor");
    }
}
