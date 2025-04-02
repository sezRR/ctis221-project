package dev.sezrr.examples.llmchatservice.aimodel.internal.controller.v1;

import dev.sezrr.examples.llmchatservice.aimodel.exposed.contract.ModelPricingService;
import dev.sezrr.examples.llmchatservice.aimodel.internal.model.ModelPricing;
import dev.sezrr.examples.llmchatservice.shared.customresponseentity.CustomResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/modelPricings")
public class ModelPricingController {
    private final ModelPricingService modelPricingService;
    
    @GetMapping
    public ResponseEntity<CustomResponseEntity<List<ModelPricing>>> getAdditionalPricings() {
        var additionalPricings = modelPricingService.getAdditionalPricings();
        
        if (additionalPricings.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        
        return ResponseEntity.ok(CustomResponseEntity.success(additionalPricings));
    }
    
    @GetMapping("/model/{modelId}")
    public ResponseEntity<CustomResponseEntity<ModelPricing>> getAdditionalPricingByModelId(@PathVariable String modelId) {
        var additionalPricing = modelPricingService.getAdditionalPricingByModelId(UUID.fromString(modelId));
        
        if (additionalPricing == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        
        return ResponseEntity.ok(CustomResponseEntity.success(additionalPricing));
    }
    
    @GetMapping("/name/{modelName}")
    public ResponseEntity<CustomResponseEntity<List<ModelPricing>>> getAdditionalPricingsByModel(@PathVariable String modelName) {
        var additionalPricings = modelPricingService.getAdditionalPricingsByModelName(modelName);
        
        if (additionalPricings.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        
        return ResponseEntity.ok(CustomResponseEntity.success(additionalPricings));
    }
    
    @GetMapping("/api/{apiUrl}")
    public ResponseEntity<CustomResponseEntity<List<ModelPricing>>> getAdditionalPricingsByApiUrl(@PathVariable String apiUrl) {
        var additionalPricings = modelPricingService.getAdditionalPricingsByApiUrl(apiUrl);
        
        if (additionalPricings.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        
        return ResponseEntity.ok(CustomResponseEntity.success(additionalPricings));
    }
    
    @PostMapping
    public ResponseEntity<CustomResponseEntity<ModelPricing>> addAdditionalPricing(@RequestBody ModelPricing modelPricing) {
        var additionalPricing = modelPricingService.addAdditionalPricing(modelPricing);
        
        if (additionalPricing == null)
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        
        return ResponseEntity.status(HttpStatus.CREATED).body(CustomResponseEntity.success(additionalPricing));
    }
}
