package dev.mattbixby.workshop.byod;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ModelComparison {

    private final ChatClient chatClient;

    public ModelComparison(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping("/models")
    public String models() {
        return chatClient.prompt()
                .user("Can you give me an up to date list of popular large language models and their current context mindow?")
                .call()
                .content();
    }


    @GetMapping("/models/stuff-the-prompt")
    public String modelsStuffThePrompt() {

        var system = """
        If you're asked about up to date language models and their context window here is some information to help you with your response:
        [
          { "company": "OpenAI",        "model": "GPT-4o",                 "context_window_size": 128000 },
          { "company": "OpenAI",        "model": "o1-preview",             "context_window_size": 128000 },
          
          { "company": "Anthropic",     "model": "Claude Opus 4",          "context_window_size": 200000 },
          { "company": "Anthropic",     "model": "Claude Sonnet 4",        "context_window_size": 200000 },
          
          { "company": "Google",        "model": "Gemini 2.5 Pro",         "context_window_size": 1000000 },
          { "company": "Google",        "model": "Gemini 2.0 Pro (Exp.)",  "context_window_size": 2000000 },
          { "company": "Google",        "model": "Gemini 2.0 Flash",       "context_window_size": 1000000 },
          
          { "company": "Meta AI",       "model": "Llama 3.1 405B",         "context_window_size": 128000 },
          
          { "company": "xAI",           "model": "Grok 3",                 "context_window_size": 1000000 },
          
          { "company": "Mistral AI",    "model": "Mistral Large 2",        "context_window_size": 128000 },
          
          { "company": "Alibaba Cloud", "model": "Qwen 2.5 72B",           "context_window_size": 128000 },
          
          { "company": "DeepSeek",      "model": "DeepSeek R1",            "context_window_size": 128000 }
        ]
        """;

        return chatClient.prompt()
                .user("Can you give me an up to date list of popular large language models and their current context mindow?")
                .system(system)
                .call()
                .content();
    }
}
