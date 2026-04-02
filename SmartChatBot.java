import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class SmartChatBot {

    static Map<String, String> knowledgeBase = new HashMap<>();
    static String userName = "";

    public static void main(String[] args) {

        // FAQ TRAINING DATA
        knowledgeBase.put("hello", "Hello! How can I help you?");
        knowledgeBase.put("hi", "Hi there!");
        knowledgeBase.put("java", "Java is an object-oriented programming language.");
        knowledgeBase.put("dsa", "DSA helps solve problems efficiently.");
        knowledgeBase.put("weather", "Today's weather is sunny 🌞");
        knowledgeBase.put("sad", "Stay strong 💪 things will get better.");
        knowledgeBase.put("happy", "That's great to hear 😄");

        JFrame frame = new JFrame("Smart ChatBot 🤖");
        frame.setSize(450, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel chatPanel = new JPanel();
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
        chatPanel.setBackground(new Color(30,30,30));

        JScrollPane scroll = new JScrollPane(chatPanel);

        JTextField input = new JTextField();
        input.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JButton send = new JButton("Send");

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(input, BorderLayout.CENTER);
        bottom.add(send, BorderLayout.EAST);

        frame.add(scroll, BorderLayout.CENTER);
        frame.add(bottom, BorderLayout.SOUTH);

        addMessage(chatPanel, "Bot: Hello! I am your smart assistant 🤖", false);

        ActionListener action = e -> {
            String text = input.getText().toLowerCase().trim();

            if(text.isEmpty()) return;

            input.setText("");

            addMessage(chatPanel, "You: " + text, true);

            String response = getResponse(text);
            addMessage(chatPanel, "Bot: " + response, false);

            frame.revalidate();
        };

        send.addActionListener(action);
        input.addActionListener(action);

        frame.setVisible(true);
    }

    // NLP + FAQ + Learning
    static String getResponse(String input){

        // NAME MEMORY
        if(input.contains("my name is")){
            userName = input.replace("my name is", "").trim();
            return "Nice to meet you " + userName;
        }

        // FAQ MATCHING
        for(String key : knowledgeBase.keySet()){
            if(input.contains(key)){
                return knowledgeBase.get(key);
            }
        }

        // TRAIN BOT
        if(input.startsWith("teach ")){
            try{
                String data = input.replace("teach ", "");
                String[] parts = data.split("=");

                knowledgeBase.put(parts[0].trim(), parts[1].trim());
                return "Learned successfully!";
            }catch(Exception e){
                return "Invalid format! Use: teach question=answer";
            }
        }

        return "I don't understand yet. You can teach me!";
    }

    // UI MESSAGE
    static void addMessage(JPanel panel, String text, boolean isUser){

        JLabel label = new JLabel("<html><div style='width:200px'>" + text + "</div></html>");
        label.setOpaque(true);
        label.setForeground(Color.WHITE);
        label.setBackground(isUser ? new Color(0,150,136) : new Color(60,60,60));
        label.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JPanel wrapper = new JPanel(new FlowLayout(isUser ? FlowLayout.RIGHT : FlowLayout.LEFT));
        wrapper.setBackground(new Color(30,30,30));
        wrapper.add(label);

        panel.add(wrapper);
    }
}
