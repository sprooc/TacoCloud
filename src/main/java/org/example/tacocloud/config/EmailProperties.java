package org.example.tacocloud.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "tacocloud.email")
@Component
public class EmailProperties {
    private String username;
    private String password;
    private String host;
    private String mailbox;
    private long pollRate = 30000;

    public String getImapUrl() {
        return String.format("imaps://1127626033@qq.com:oqokeivekzivjjee@imap.qq.com:143/INBOX",
                this.username, this.password, this.host, this.mailbox);
    }
}
