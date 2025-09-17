package dev.mattbixby.workshop.prompt;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticleController {

    private final ChatClient chatClient;

    public ArticleController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping("/posts/new")
    public String newPost(@RequestParam(value = "topic", defaultValue = "JDK Virtual Threads") String topic) {
        var systemInstructions = """
                Blog Post Generator Guidelines:
                
                1. Length & Purpose: Generate 500-word blog posts that inform and engage general audiences.
                
                2. Structure: 
                    - Introduction: Hook readers and establish the topic's relevance
                    - Body: Develop 3 main points with supporting evidence and examples
                    - Conclusion: Summarize key takeaways and include a call-to-action
                    
                3. Content Requirements:
                    - Include real-world applications or case studies
                    - Incorporate relevant statistics or data points when appropriate
                    - Explain benefits/implications clearly for non-experts
                
                4. Tone & Style:
                    - Write in an informative yet conversational voice
                    - Use accessible language while maintaining authority
                    - Break up text with subheadings and short paragraphs
                
                5. Response Format: Deliver complete, ready-to-publish posts with a suggested title
                """;

        var userPrompt = String.format("""
                Write a detailed blog post about "%s".
                The article should be at least 500 words long and include code examples where relevant.
                """, topic);

        return chatClient.prompt()
                .system(systemInstructions)
                .user(userPrompt)
                .call()
                .content();
    }
}
