package dev.sezrr.llmchatwrapper.llmchatservice.aimodel.internal.controller.v1;

import dev.sezrr.llmchatwrapper.llmchatservice.aimodel.exposed.dto.supported_model.SupportedModelQueryDto;
import dev.sezrr.llmchatwrapper.llmchatservice.aimodel.exposed.contract.SupportedModelService;
import dev.sezrr.llmchatwrapper.llmchatservice.aimodel.exposed.dto.supported_model.SupportedModelAddDto;
import dev.sezrr.llmchatwrapper.llmchatservice.shared.custom_response_entity.CustomResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/models")
public class SupportedModelController {
    private final SupportedModelService supportedModelService;
    
    @GetMapping
    public ResponseEntity<CustomResponseEntity<List<SupportedModelQueryDto>>> getSupportedModels(
            @RequestParam(required = false) String apiUrl,
            @RequestParam(required = false) String model
    ) {
        List<SupportedModelQueryDto> supportedModels;
        if (apiUrl == null && model == null)
            supportedModels = supportedModelService.findAllCache();
        else        
            supportedModels = supportedModelService.getSupportedModels(apiUrl, model);
        
        if (supportedModels.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        
        return ResponseEntity.ok(CustomResponseEntity.success(supportedModels));
    }
    
    @PostMapping
    public ResponseEntity<CustomResponseEntity<SupportedModelQueryDto>> addSupportedModel(@RequestBody SupportedModelAddDto supportedModelAddDto) {
        var supportedModel = supportedModelService.addSupportedModel(supportedModelAddDto);

        if (supportedModel == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        return ResponseEntity.ok(CustomResponseEntity.success(supportedModel));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponseEntity<Boolean>> deleteSupportedModel(@PathVariable UUID id) {
        var result = supportedModelService.deleteSupportedModel(id);
        if (!result)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.ok(CustomResponseEntity.success("Model deleted successfully", true));
    }
}
