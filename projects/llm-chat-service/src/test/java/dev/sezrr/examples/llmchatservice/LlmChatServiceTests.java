package dev.sezrr.examples.llmchatservice;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

class SupportedModelControllerTest {

//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockitoBean
//    private SupportedModelRepository supportedModelRepository;
//
//    @Test
//    void testController() throws Exception {
//        mockMvc.perform(get("/api/v1/supported-model"))
//                .andExpect(status().isOk());
//    }

    ApplicationModules modules = ApplicationModules.of(LlmChatService.class);
    
    @Test
    void testModulithDependencies() {
        modules.forEach(System.out::println);
        modules.verify();
    }
    
    @Test
    void writeDocumentationSnippets() {
        new Documenter(modules)
                .writeModulesAsPlantUml()
                .writeIndividualModulesAsPlantUml();
    }
}

