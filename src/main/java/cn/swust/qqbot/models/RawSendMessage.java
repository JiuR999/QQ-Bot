package cn.swust.qqbot.models;

public class RawSendMessage {

    /**
     * user_id : 2041357138
     * message : {"type":"record","data":{"file":"https://api.pearktrue.cn/api/yujie/voice/c1320192d0d9ef35725a5b849405371f.mp3"}}
     */

    private long user_id;
    private int group_id;
    private MessageBean message;

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public int getGroup_id() {
        return group_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public MessageBean getMessage() {
        return message;
    }

    public void setMessage(MessageBean message) {
        this.message = message;
    }

    public static class MessageBean {
        /**
         * type : record
         * data : {"file":"https://api.pearktrue.cn/api/yujie/voice/c1320192d0d9ef35725a5b849405371f.mp3"}
         */

        private String type;
        private DataBean data;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public static class DataBean { }

        public static class FileDataBean extends DataBean{
            /**
             * file : https://api.pearktrue.cn/api/yujie/voice/c1320192d0d9ef35725a5b849405371f.mp3
             */

            private String file;

            public FileDataBean(String file) {
                this.file = file;
            }
            public String getFile() {
                return file;
            }

            public void setFile(String file) {
                this.file = file;
            }
        }

        public static class TextDataBean extends DataBean{

            private String text;

            public TextDataBean(String text) {
                this.text = text;
            }
            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }
        }
    }
}
