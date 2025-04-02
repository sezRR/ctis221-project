rootProject.name = "modulo-ai"
include("modulo-ai-core")
include("providers:modulo-ai-openai")
findProject(":providers:modulo-ai-openai")?.name = "modulo-ai-openai"
