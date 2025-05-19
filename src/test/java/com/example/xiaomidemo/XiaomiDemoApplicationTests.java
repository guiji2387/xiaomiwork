package com.example.xiaomidemo;

import com.example.xiaomidemo.application.controller.RuleController;
import com.example.xiaomidemo.application.controller.VehicleController;
import com.example.xiaomidemo.application.controller.WarnController;
import com.example.xiaomidemo.application.dto.Response;
import com.example.xiaomidemo.application.dto.SignalBatch;
import com.example.xiaomidemo.application.dto.WarnResult;
import com.example.xiaomidemo.domain.exception.RuleParseException;
import com.example.xiaomidemo.domain.model.BatterySignal;
import com.example.xiaomidemo.domain.model.RuleSegment;
import com.example.xiaomidemo.domain.model.VehicleInfo;
import com.example.xiaomidemo.domain.model.WarningRule;
import com.example.xiaomidemo.domain.repository.VehicleRepository;
import com.example.xiaomidemo.domain.repository.WarningRuleRepository;
import com.example.xiaomidemo.domain.service.DifferenceCalculator;
import com.example.xiaomidemo.domain.service.SignalProcessingService;
import com.example.xiaomidemo.infrastructure.persistence.model.VehicleInfoDO;
import com.example.xiaomidemo.infrastructure.persistence.model.WarningRuleDO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class XiaomiDemoApplicationTests {

    @InjectMocks
    private WarnController warnController;

    @Mock
    private SignalProcessingService signalProcessingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcessWarn_Success() {
        // Arrange
        List<SignalBatch.SignalRecord> records = new ArrayList<>();
        SignalBatch.SignalRecord record = new SignalBatch.SignalRecord();
        records.add(record);

        List<WarnResult> mockResults = Collections.singletonList(new WarnResult());
        when(signalProcessingService.processBatch(any(SignalBatch.class))).thenReturn(mockResults);

        // Act
        Response<List<WarnResult>> response = warnController.processWarn(records);

        // Assert
        assertEquals(200, response.getCode());
        assertEquals(mockResults, response.getData());
        verify(signalProcessingService, times(1)).processBatch(any(SignalBatch.class));
    }

    @Test
    void testProcessWarn_EmptyRecords() {
        // Arrange
        List<SignalBatch.SignalRecord> records = Collections.emptyList();

        List<WarnResult> mockResults = Collections.emptyList();
        when(signalProcessingService.processBatch(any(SignalBatch.class))).thenReturn(mockResults);

        // Act
        Response<List<WarnResult>> response = warnController.processWarn(records);

        // Assert
        assertEquals(200, response.getCode());
        assertEquals(mockResults, response.getData());
        verify(signalProcessingService, times(1)).processBatch(any(SignalBatch.class));
    }
}
