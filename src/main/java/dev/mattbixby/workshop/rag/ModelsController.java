package dev.mattbixby.workshop.rag;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ModelsController {

    private final ChatClient chatClient;

    // this configuration for the constructor and the get method stays constant regardless
    // of if you switch out in-memory vector store for pgVector or pinecone
    public ModelsController(ChatClient.Builder builder, VectorStore vectorStore) {
        this.chatClient = builder
                .defaultAdvisors(new QuestionAnswerAdvisor(vectorStore))
                .build();
    }

    @GetMapping("/rag/models")
    public Models fag(@RequestParam(value = "message", defaultValue = "Give me a list of all the models from OpenAI along with their context window.") String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .entity(Models.class);
    }
}


