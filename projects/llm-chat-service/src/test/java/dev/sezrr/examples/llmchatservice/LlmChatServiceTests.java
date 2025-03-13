package dev.sezrr.examples.llmchatservice;

import dev.sezrr.examples.llmchatservice.modules.aimodel.controller.SupportedModelController;
import dev.sezrr.examples.llmchatservice.modules.aimodel.repository.SupportedModelRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SupportedModelController.class)
class SupportedModelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SupportedModelRepository supportedModelRepository;

    @Test
    void testController() throws Exception {
        mockMvc.perform(get("/api/v1/supported-model"))
                .andExpect(status().isOk());
    }
    
    @Test
    void testModulithDependencies() {
        ApplicationModules modules = ApplicationModules.of(LlmChatService.class);
        modules.verify();
        modules.forEach(System.out::println);
    }
}

