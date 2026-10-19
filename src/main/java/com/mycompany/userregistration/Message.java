package com.mycompany.userregistration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

/** Represents a QuickChat message and its send, discard, and storage operations. */
public class Message {

    private static int totalMessages;

    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;

    public Message() {
        messageID = generateMessageID();
        messageNumber = totalMessages;
    }

    public Message(String recipient, String messageText) {
        this();
        this.recipient = recipient;
        this.messageText = messageText;
        messageHash = createMessageHash(messageID, messageNumber, messageText);
    }

    public Message(String messageID, String recipient, String messageText, int messageNumber) {
        this.messageID = messageID;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageNumber = messageNumber;
        messageHash = createMessageHash(messageID, messageNumber, messageText);
    }

    public String generateMessageID() {
        StringBuilder idBuilder = new StringBuilder(10);
        idBuilder.append(ThreadLocalRandom.current().nextInt(1, 10));
        for (int index = 1; index < 10; index++) {
            idBuilder.append(ThreadLocalRandom.current().nextInt(10));
        }
        return idBuilder.toString();
    }

    public boolean checkMessageID() {
        return checkMessageID(messageID);
    }

    public boolean checkMessageID(String id) {
        return id != null && id.matches("\\d{1,10}");
    }

    public String checkRecipientCell() {
        return checkRecipientCell(recipient);
    }

    public String checkRecipientCell(String recipientCell) {
        if (recipientCell != null && recipientCell.matches("\\+27[0-9]{9}")) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }

    public String createMessageHash() {
        return createMessageHash(messageID, messageNumber, messageText);
    }

    public String createMessageHash(String id, int number, String text) {
        String safeId = id == null ? "00" : id;
        String prefix = safeId.length() >= 2 ? safeId.substring(0, 2) : "0" + safeId;
        String safeText = text == null ? "" : text.trim();
        String[] words = safeText.isEmpty() ? new String[0] : safeText.split("\\s+");
        String firstWord = words.length == 0 ? "" : cleanWord(words[0]);
        String lastWord = words.length == 0 ? "" : cleanWord(words[words.length - 1]);
        return prefix + ":" + number + ":" + (firstWord + lastWord).toUpperCase(Locale.ROOT);
    }

    private String cleanWord(String word) {
        return word.replaceAll("^[^A-Za-z0-9]+|[^A-Za-z0-9]+$", "");
    }

    public String checkMessageLength(String text) {
        if (text == null) {
            return "Message exceeds 250 characters by 250; please reduce the size.";
        }
        int length = text.length();
        if (length <= 250) {
            return "Message ready to send.";
        }
        return "Message exceeds 250 characters by " + (length - 250)
                + "; please reduce the size.";
    }

    public String validateMessage() {
        return checkMessageLength(messageText);
    }

    public String SentMessage() {
        return "Message successfully sent.";
    }

    public String SentMessage(String selection) {
        if (selection == null) {
            return "Invalid selection.";
        }
        return switch (selection.trim().toLowerCase(Locale.ROOT)) {
            case "1", "send", "send message" -> {
                incrementTotalMessages();
                yield "Message successfully sent.";
            }
            case "0", "2", "discard", "disregard", "disregard message" ->
                "Press 0 to delete the message.";
            case "3", "store", "store message" -> "Message successfully stored.";
            default -> "Invalid selection.";
        };
    }

    public static int returnTotalMessages() {
        return totalMessages;
    }

    public static void incrementTotalMessages() {
        totalMessages++;
    }

    public static void resetTotalMessages() {
        totalMessages = 0;
    }

    public String storeMessage() {
        try {
            storeMessage(Path.of("messages.json"));
            return "Message successfully stored.";
        } catch (IOException exception) {
            return "Unable to store message: " + exception.getMessage();
        }
    }

    public void storeMessage(Path file) throws IOException {
        String object = "{\"messageID\":\"" + escapeJson(messageID)
                + "\",\"messageHash\":\"" + escapeJson(messageHash)
                + "\",\"recipient\":\"" + escapeJson(recipient)
                + "\",\"message\":\"" + escapeJson(messageText) + "\"}";
        String existing = Files.exists(file)
                ? Files.readString(file, StandardCharsets.UTF_8).trim() : "";
        String json;
        if (existing.isEmpty()) {
            json = "[\n  " + object + "\n]\n";
        } else if (existing.startsWith("[") && existing.endsWith("]")) {
            String entries = existing.substring(1, existing.length() - 1).trim();
            json = "[\n  " + entries + (entries.isEmpty() ? "" : ",")
                    + "\n  " + object + "\n]\n";
        } else {
            throw new IOException("The message file does not contain a JSON array.");
        }
        Files.writeString(file, json, StandardCharsets.UTF_8);
    }

    private String escapeJson(String value) {
        if (value == null) {
            return "";
        }
        StringBuilder escaped = new StringBuilder();
        for (char character : value.toCharArray()) {
            switch (character) {
                case '"' -> escaped.append("\\\"");
                case '\\' -> escaped.append("\\\\");
                case '\n' -> escaped.append("\\n");
                case '\r' -> escaped.append("\\r");
                case '\t' -> escaped.append("\\t");
                default -> {
                    if (character < 0x20) {
                        escaped.append(String.format("\\u%04x", (int) character));
                    } else {
                        escaped.append(character);
                    }
                }
            }
        }
        return escaped.toString();
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    public void setMessageNumber(int messageNumber) {
        this.messageNumber = messageNumber;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public void setMessageHash(String messageHash) {
        this.messageHash = messageHash;
    }
}