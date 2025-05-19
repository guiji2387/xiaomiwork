package com.example.xiaomidemo.application.controller;

import com.example.xiaomidemo.application.dto.Response;
import com.example.xiaomidemo.application.dto.SignalBatch;
import com.example.xiaomidemo.application.dto.WarnResult;
import com.example.xiaomidemo.domain.service.SignalProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/warn")
public class WarnController {
     @Autowired
     private SignalProcessingService signalProcessingService;

     @PostMapping
     public Response<List<WarnResult>> processWarn(@RequestBody List<SignalBatch.SignalRecord> records) {
         SignalBatch batch = new SignalBatch();
         batch.setRecords(records);
         List<WarnResult> results = signalProcessingService.processBatch(batch);
         return Response.success(results);
     }
}
