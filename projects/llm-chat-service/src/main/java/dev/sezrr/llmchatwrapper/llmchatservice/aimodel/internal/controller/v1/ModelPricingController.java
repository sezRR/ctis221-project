package dev.sezrr.llmchatwrapper.llmchatservice.aimodel.internal.controller.v1;

import dev.sezrr.llmchatwrapper.llmchatservice.aimodel.exposed.contract.ModelPricingService;
import dev.sezrr.llmchatwrapper.llmchatservice.aimodel.exposed.dto.model_pricing.ModelPricingAddDto;
import dev.sezrr.llmchatwrapper.llmchatservice.aimodel.exposed.dto.model_pricing.ModelPricingQueryDto;
import dev.sezrr.llmchatwrapper.llmchatservice.shared.custom_response_entity.CustomResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/model-pricings")
public class ModelPricingController {
    private final ModelPricingService modelPricingService;
    
    @GetMapping("/active")
    public ResponseEntity<CustomResponseEntity<List<ModelPricingQueryDto>>> getAdditionalPricings(
            @RequestParam(required = false) String modelName,
            @RequestParam(required = false) String apiUrl
    ) {
        List<ModelPricingQueryDto> additionalPricings;
        if (modelName != null || apiUrl != null)
            additionalPricings = modelPricingService.filterActiveAdditionalPricings(modelName, apiUrl);
        else
            additionalPricings = modelPricingService.getAdditionalPricings();
        
        if (additionalPricings.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        
        return ResponseEntity.ok(CustomResponseEntity.success(additionalPricings));
    }
    
    @PostMapping
    public ResponseEntity<CustomResponseEntity<ModelPricingQueryDto>> addAdditionalPricing(@RequestBody ModelPricingAddDto modelPricingAddDto) {
        var additionalPricing = modelPricingService.addAdditionalPricing(modelPricingAddDto);
        
        if (additionalPricing == null)
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        
        return ResponseEntity.status(HttpStatus.CREATED).body(CustomResponseEntity.success(additionalPricing));
    }
    
    @PutMapping("/{modelPricingId}/activate")
    public ResponseEntity<CustomResponseEntity<ModelPricingQueryDto>> activatePricing(@PathVariable String modelPricingId) {
        ModelPricingQueryDto activatedPricing = modelPricingService.activatePricing(UUID.fromString(modelPricingId));
        
        if (activatedPricing == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        
        return ResponseEntity.ok(CustomResponseEntity.success(activatedPricing));
    }
    
    @PutMapping("/{modelPricingId}/deactivate")
    public ResponseEntity<CustomResponseEntity<ModelPricingQueryDto>> deactivatePricing(@PathVariable String modelPricingId) {
        ModelPricingQueryDto deactivatedPricing = modelPricingService.deactivatePricing(UUID.fromString(modelPricingId));
        
        if (deactivatedPricing == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        
        return ResponseEntity.ok(CustomResponseEntity.success(deactivatedPricing));
    }
}
