package com.baiye959.webhooks;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@RestController
@RequestMapping("/webhooks")
public class WebhooksController {

    @PostMapping
    public String handleWebhook(@RequestBody String payload) {
        // 在这里处理接收到的GitHub Webhook payload
        System.out.println("Received payload: " + payload);

        // 执行sh脚本
        String scriptPath = "/app/script.sh";
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(scriptPath);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder output = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                return "Script executed successfully:\n" + output.toString();
            } else {
                return "Script execution failed with exit code: " + exitCode;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Error executing script: " + e.getMessage();
        }
    }
}
