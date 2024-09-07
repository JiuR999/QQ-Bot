package cn.swust.qqbot.models;

import java.util.List;

public class QQMessage {

    /**
     * self_id : 235934114
     * user_id : 2041357138
     * time : 1723216012
     * message_id : -2147483638
     * real_id : -2147483638
     * message_seq : -2147483638
     * message_type : private
     * sender : {"user_id":2041357138,"nickname":"","card":""}
     * raw_message : h
     * font : 14
     * sub_type : friend
     * message : [{"data":{"text":"h"},"type":"text"}]
     * message_format : array
     * post_type : message
     */

    private int self_id;
    private long user_id;
    private int time;
    private int message_id;
    private int real_id;
    private int message_seq;
    private int group_id ;
    private String message_type;
    private SenderBean sender;
    private String raw_message;
    private int font;
    private String sub_type;
    private String message_format;
    private String post_type;
    private List<MessageBean> message;

    public int getGroup_id() {
        return group_id;
    }

    public int getSelf_id() {
        return self_id;
    }

    public void setSelf_id(int self_id) {
        this.self_id = self_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getMessage_id() {
        return message_id;
    }

    public void setMessage_id(int message_id) {
        this.message_id = message_id;
    }

    public int getReal_id() {
        return real_id;
    }

    public void setReal_id(int real_id) {
        this.real_id = real_id;
    }

    public int getMessage_seq() {
        return message_seq;
    }

    public void setMessage_seq(int message_seq) {
        this.message_seq = message_seq;
    }

    public String getMessage_type() {
        return message_type;
    }

    public void setMessage_type(String message_type) {
        this.message_type = message_type;
    }

    public SenderBean getSender() {
        return sender;
    }

    public void setSender(SenderBean sender) {
        this.sender = sender;
    }

    public String getRaw_message() {
        return raw_message;
    }

    public void setRaw_message(String raw_message) {
        this.raw_message = raw_message;
    }

    public int getFont() {
        return font;
    }

    public void setFont(int font) {
        this.font = font;
    }

    public String getSub_type() {
        return sub_type;
    }

    public void setSub_type(String sub_type) {
        this.sub_type = sub_type;
    }

    public String getMessage_format() {
        return message_format;
    }

    public void setMessage_format(String message_format) {
        this.message_format = message_format;
    }

    public String getPost_type() {
        return post_type;
    }

    public void setPost_type(String post_type) {
        this.post_type = post_type;
    }

    public List<MessageBean> getMessage() {
        return message;
    }

    public void setMessage(List<MessageBean> message) {
        this.message = message;
    }

    public static class SenderBean {
        /**
         * user_id : 2041357138
         * nickname :
         * card :
         */

        private long user_id;
        private String nickname;
        private String card;

        public long getUser_id() {
            return user_id;
        }

        public void setUser_id(long user_id) {
            this.user_id = user_id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getCard() {
            return card;
        }

        public void setCard(String card) {
            this.card = card;
        }

        @Override
        public String toString() {
            return "SenderBean{" +
                    "user_id=" + user_id +
                    ", nickname='" + nickname + '\'' +
                    ", card='" + card + '\'' +
                    '}';
        }
    }

    public static class MessageBean {
        /**
         * data : {"text":"h"}
         * type : text
         */

        private DataBean data;
        private String type;

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public static class DataBean {
            /**
             * text : h
             */

            private String text;

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            @Override
            public String toString() {
                return "DataBean{" +
                        "text='" + text + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "MessageBean{" +
                    "data=" + data +
                    ", type='" + type + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "QQMessage{" +
                "self_id=" + self_id +
                ", user_id=" + user_id +
                ", time=" + time +
                ", message_id=" + message_id +
                ", real_id=" + real_id +
                ", message_seq=" + message_seq +
                ", group_id=" + group_id +
                ", message_type='" + message_type + '\'' +
                ", sender=" + sender +
                ", raw_message='" + raw_message + '\'' +
                ", font=" + font +
                ", sub_type='" + sub_type + '\'' +
                ", message_format='" + message_format + '\'' +
                ", post_type='" + post_type + '\'' +
                ", message=" + message +
                '}';
    }
}
