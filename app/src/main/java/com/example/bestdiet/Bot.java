package com.example.bestdiet;

import java.util.List;
import java.util.Map;

public class Bot {
    private String bot_id;
    private int status;
    private String operator;
    private int type;
    private String last_activity_at;
    private String referral_source;
    private List<Object> quick_replies;
    private Map<String, Object> variables;
    private List<Object> tags;
    private boolean is_chat_opened;
    private String subscribe_url;
    private boolean is_banned;
    private String created_at;
    private String automation_paused_until;
    private String id;

    private ChannelData channel_data;
    private long telegram_id;

    public Map<String, Object> getVariables() {
        return variables;
    }

    public String getbot_id() {
        return bot_id;
    }

    public String getstatus() {
        Object dateObj = this.variables.get("Status");
        if (dateObj != null) {
            return dateObj.toString(); // Приведение Object к String, если объект не null
        }
        return null; // или выбросить исключение, или вернуть значение по умолчанию
    }

    public String getheight() {
        Object dateObj = this.variables.get("height");
        if (dateObj != null) {
            return dateObj.toString(); // Приведение Object к String, если объект не null
        }
        return null; // или выбросить исключение, или вернуть значение по умолчанию
    }

    public String getneedweight() {
        Object dateObj = this.variables.get("need_weight_result");
        if (dateObj != null) {
            return dateObj.toString(); // Приведение Object к String, если объект не null
        }
        return null; // или выбросить исключение, или вернуть значение по умолчанию
    }

    public String getactivity_days() {
        Object dateObj = this.variables.get("activity_of_week");
        if (dateObj != null) {
            return dateObj.toString(); // Приведение Object к String, если объект не null
        }
        return null; // или выбросить исключение, или вернуть значение по умолчанию
    }
    public String getlimitation() {
        Object dateObj = this.variables.get("limitation");
        if (dateObj != null) {
            return dateObj.toString(); // Приведение Object к String, если объект не null
        }
        return null; // или выбросить исключение, или вернуть значение по умолчанию
    }

    public String getcountofmeal() {
        Object dateObj = this.variables.get("count_of_meal");
        if (dateObj != null) {
            return dateObj.toString(); // Приведение Object к String, если объект не null
        }
        return null; // или выбросить исключение, или вернуть значение по умолчанию
    }

    public String getweight() {
        Object dateObj = this.variables.get("weight");
        if (dateObj != null) {
            return dateObj.toString(); // Приведение Object к String, если объект не null
        }
        return null; // или выбросить исключение, или вернуть значение по умолчанию
    }

    public String getPib() {
        Object dateObj = this.variables.get("PIB");
        if (dateObj != null) {
            return dateObj.toString(); // Приведение Object к String, если объект не null
        }
        return null; // или выбросить исключение, или вернуть значение по умолчанию
    }
    public String getDate() {
        Object dateObj = this.variables.get("Date");
        if (dateObj != null) {
            return dateObj.toString(); // Приведение Object к String, если объект не null
        }
        return null; // или выбросить исключение, или вернуть значение по умолчанию
    }

    public String getGender() {
        Object dateObj = this.variables.get("Gender");
        if (dateObj != null) {
            return dateObj.toString(); // Приведение Object к String, если объект не null
        }
        return null; // или выбросить исключение, или вернуть значение по умолчанию
    }

    public String getclient_id() {
        Object dateObj = this.id;
        if (dateObj != null) {
            return dateObj.toString(); // Приведение Object к String, если объект не null
        }
        return null; // или выбросить исключение, или вернуть значение по умолчанию
    }

    public long getchat_id_tg() {
        return telegram_id; // или выбросить исключение, или вернуть значение по умолчанию
    }

    public String getphoto_url() {
        String Photo_url = this.channel_data.getPhoto_url();
        if (Photo_url != null) {
            return Photo_url; // Приведение Object к String, если объект не null
        }
        return null; // или выбросить исключение, или вернуть значение по умолчанию
    }
    // Getters and setters

    public class ChannelData {
        private String username;
        private String first_name;
        private String last_name;
        private String language_code;
        private String phone_number;
        private String vcard;
        private String photo;
        private String name;

        public String getPhoto_url()
        {
            return this.photo;
        }

        // getters and setters
    }
}
