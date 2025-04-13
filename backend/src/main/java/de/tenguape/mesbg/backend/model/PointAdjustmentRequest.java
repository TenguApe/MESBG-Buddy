package de.tenguape.mesbg.backend.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PointAdjustmentRequest {
    private PointType type;
    private OperationType operation;
}