package com.example.xiaomidemo.domain.model;

import com.example.xiaomidemo.infrastructure.util.SignalParser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.springframework.data.annotation.Id;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

@Data
@Builder
public class WarningRule {
    private Long id;
    private Integer ruleId;
    private BatteryType batteryType;
    private String signalType; // 电压差/电流差

    private List<RuleSegment> segments;

    @Tolerate
    public WarningRule() {
    }

    //匹配规则
    public Optional<Integer> match(Double difference) {
        return segments.stream()
                .filter(segment ->
                        difference >= segment.getMin() &&
                                (segment.getMax() == null || difference < segment.getMax())
                )
                .findFirst()
                .map(RuleSegment::getLevel);
    }
}


